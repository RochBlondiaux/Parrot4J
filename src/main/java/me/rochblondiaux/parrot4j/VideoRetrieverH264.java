package me.rochblondiaux.parrot4j;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.components.*;
import me.rochblondiaux.parrot4j.listeners.ImageListener;
import me.rochblondiaux.parrot4j.video.H264VideoDecoder;

import java.awt.image.BufferedImage;

import static me.rochblondiaux.parrot4j.helpers.ThreadHelper.sleep;

@Log4j2
public class VideoRetrieverH264 extends VideoRetrieverAbstract implements ImageListener {

    private final TcpComponent tcpComponent;

    private final H264VideoDecoder videoDecoder;

    @Inject
    public VideoRetrieverH264(ThreadComponent threadComponent, AddressComponent addressComponent, TcpComponent tcpComponent,
                              ReadyStateListenerComponent readyStateListenerComponent, ErrorListenerComponent errorListenerComponent,
                              H264VideoDecoder videoDecoder) {
        super(threadComponent, addressComponent, readyStateListenerComponent, errorListenerComponent);
        this.tcpComponent = tcpComponent;
        this.videoDecoder = videoDecoder;
    }

    @Override
    public void stop() {
        super.stop();
        videoDecoder.stopDecoding();
    }

    @Override
    protected void doRun() {
        connectToVideoDataPort();
        initializeCommunication();
        setReady();

        while (!isStopped()) {
            tryDecoding();
            if (!isStopped()) {
                reconnectVideoPort();
            }
        }

        disconnectFromVideoDataPort();
    }

    private void tryDecoding() {
        try {
            videoDecoder.startDecoding(tcpComponent, this);
        } catch (Exception e) {
            log.warn("Exception while decoding video stream: {}", e.getMessage());
        }
    }

    private void reconnectVideoPort() {
        log.warn("Reconnecting video data port");
        tcpComponent.disconnect();
        sleep(4000);
        tcpComponent.connect(getDroneAddress(), getVideoDataPort());
    }

    private void connectToVideoDataPort() {
        log.info("Connecting to video data port {}", getVideoDataPort());
        tcpComponent.connect(getDroneAddress(), getVideoDataPort());
    }

    private void initializeCommunication() {
        tcpComponent.sendKeepAlivePacket();
    }

    private void disconnectFromVideoDataPort() {
        log.info("Disconnecting from video data port {}", getVideoDataPort());
        tcpComponent.disconnect();
    }

    @Override
    public void onImage(BufferedImage image) {
        getVideoDataListeners().forEach(listener -> listener.onVideoData(image));
    }
}