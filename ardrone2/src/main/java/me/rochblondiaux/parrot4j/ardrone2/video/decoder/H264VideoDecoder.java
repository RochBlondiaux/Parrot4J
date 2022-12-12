package me.rochblondiaux.parrot4j.ardrone2.video.decoder;

import com.xuggle.xuggler.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.event.EventManager;
import me.rochblondiaux.parrot4j.api.network.TCPConnection;
import me.rochblondiaux.parrot4j.ardrone2.events.ImageReceivedEvent;

import java.net.SocketTimeoutException;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
@RequiredArgsConstructor
public class H264VideoDecoder {


    private final TCPConnection connection;
    private boolean running;

    public void start() {
        this.running = true;
        decode();
    }

    public void stop() {
        this.running = false;
    }

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    private void decode() {
        // Let's make sure that we can actually convert video pixel formats.
        if (!IVideoResampler.isSupported(IVideoResampler.Feature.FEATURE_COLORSPACECONVERSION))
            throw new RuntimeException("you must install the GPL version of Xuggler (with IVideoResampler support) for this demo to work");

        // Create a Xuggler container object
        IContainer container = IContainer.make();

        // Open up the container
        if (container.open(connection.inputStream(), null) < 0)
            throw new IllegalArgumentException("could not open inpustream");

        // query how many streams the call to open found
        int numStreams = container.getNumStreams();

        // and iterate through the streams to find the first video stream
        int videoStreamId = -1;
        IStreamCoder videoCoder = null;
        for (int i = 0; i < numStreams; i++) {
            // Find the stream object
            IStream stream = container.getStream(i);
            // Get the pre-configured decoder that can decode this stream;
            IStreamCoder coder = stream.getStreamCoder();

            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                videoStreamId = i;
                videoCoder = coder;
                break;
            }
        }
        if (videoStreamId == -1)
            throw new RuntimeException("could not find video stream");

        /*
         * Now we have found the video stream in this file. Let's open up our
         * decoder so it can do work.
         */
        if (videoCoder.open() < 0)
            throw new RuntimeException("could not open video decoder for container");

        IVideoResampler resampler = null;
        if (videoCoder.getPixelType() != IPixelFormat.Type.BGR24) {
            // if this stream is not in BGR24, we're going to need to
            // convert it. The VideoResampler does that for us.
            resampler = IVideoResampler
                    .make(videoCoder.getWidth(), videoCoder.getHeight(), IPixelFormat.Type.BGR24, videoCoder.getWidth(), videoCoder.getHeight(),
                            videoCoder.getPixelType());
            if (resampler == null)
                throw new RuntimeException("could not create color space resampler.");
        }

        /*
         * Now, we start walking through the container looking at each packet.
         */
        IPacket packet = IPacket.make();
        long firstTimestampInStream = Global.NO_PTS;
        long systemClockStartTime = 0;
        while (container.readNextPacket(packet) >= 0 && running) {
            try {
                /*
                 * Now we have a packet, let's see if it belongs to our video stream
                 */
                if (packet.getStreamIndex() != videoStreamId)
                    return;
                /*
                 * We allocate a new picture to get the data out of Xuggler
                 */
                IVideoPicture picture = IVideoPicture.make(videoCoder.getPixelType(), videoCoder.getWidth(), videoCoder.getHeight());

                int offset = 0;
                while (offset < packet.getSize()) {
                    /*
                     * Now, we decode the video, checking for any errors.
                     */

                    int bytesDecoded = videoCoder.decodeVideo(picture, packet, offset);
                    if (bytesDecoded < 0)
                        throw new RuntimeException("got error decoding video");
                    offset += bytesDecoded;

                    /*
                     * Some decoders will consume data in a packet, but will not
                     * be able to construct a full video picture yet. Therefore
                     * you should always check if you got a complete picture
                     * from the decoder
                     */
                    if (picture.isComplete()) {
                        IVideoPicture newPic = picture;

                        /*
                         * If the resampler is not null, that means we didn't
                         * get the video in BGR24 format and need to convert it
                         * into BGR24 format.
                         */

                        if (resampler != null) {
                            // we must resample
                            newPic = IVideoPicture.make(resampler.getOutputPixelFormat(), picture.getWidth(), picture.getHeight());
                            if (resampler.resample(newPic, picture) < 0)
                                throw new RuntimeException("could not resample video");
                        }
                        if (newPic.getPixelType() != IPixelFormat.Type.BGR24)
                            throw new RuntimeException("could not decode video as BGR 24 bit data");

                        if (firstTimestampInStream == Global.NO_PTS) {
                            // This is our first time through
                            firstTimestampInStream = picture.getTimeStamp();
                            // get the starting clock time so we can hold up frames until the right time.
                            systemClockStartTime = System.currentTimeMillis();
                        } else {
                            long systemClockCurrentTime = System.currentTimeMillis();
                            long millisecondsClockTimeSinceStartofVideo = systemClockCurrentTime - systemClockStartTime;

                            // compute how long for this frame since the first frame in the stream.
                            // remember that IVideoPicture and IAudioSamples timestamps are always in MICROSECONDS,
                            // so we divide by 1000 to get milliseconds.
                            long millisecondsStreamTimeSinceStartOfVideo = (picture.getTimeStamp() - firstTimestampInStream) / 1000;
                            final long millisecondsTolerance = 50; // and we give ourselfs 50 ms of tolerance
                            final long millisecondsToSleep =
                                    (millisecondsStreamTimeSinceStartOfVideo - (millisecondsClockTimeSinceStartofVideo + millisecondsTolerance));
                            if (millisecondsToSleep > 0) {
                                try {
                                    Thread.sleep(millisecondsToSleep);
                                } catch (InterruptedException e) {
                                    // we might get this when the user closes the dialog box, so just return from the method.
                                    return;
                                }
                            }
                        }
                        EventManager.call(new ImageReceivedEvent(Utils.videoPictureToImage(newPic)));
                    }
                } // end of while
            } catch (Exception e) {
                handleException(e);
            }
        }

        if (videoCoder != null)
            videoCoder.close();
        if (container != null)
            container.close();
    }

    private void handleException(Exception e) {
        if (e instanceof SocketTimeoutException) {
            log.warn("Socket timeout for video channel");
            connection.reconnect();
        } else
            log.error(e);
    }
}
