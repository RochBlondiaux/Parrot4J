package me.rochblondiaux.parrot4j;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.components.*;
import me.rochblondiaux.parrot4j.listeners.VideoDataListener;
import me.rochblondiaux.parrot4j.video.P264ImageDecoder;

import java.awt.image.BufferedImage;
import java.net.DatagramPacket;

import static me.rochblondiaux.parrot4j.helpers.ThreadHelper.sleep;

@Log4j2
public class VideoRetrieverP264 extends VideoRetrieverAbstract {

    public static final int RECEIVING_BUFFER_SIZE = 1024000;

    private final UdpComponent udpComponent;

    private final P264ImageDecoder imageDecoder;

    private byte[] receivingBuffer;

    private DatagramPacket incomingDataPacket;

    @Inject
    public VideoRetrieverP264(ThreadComponent threadComponent, AddressComponent addressComponent, UdpComponent udpComponent,
                              ReadyStateListenerComponent readyStateListenerComponent, ErrorListenerComponent errorListenerComponent,
                              P264ImageDecoder imageDecoder) {
        super(threadComponent, addressComponent, readyStateListenerComponent, errorListenerComponent);
        this.udpComponent = udpComponent;
        this.imageDecoder = imageDecoder;

        determineDatagramPackets();
    }

    private void determineDatagramPackets() {
        receivingBuffer = new byte[RECEIVING_BUFFER_SIZE];
        incomingDataPacket = new DatagramPacket(receivingBuffer, receivingBuffer.length, getDroneAddress(), getVideoDataPort());
    }

    @Override
    protected void doRun() {
        connectToVideoDataPort();
        initializeCommunication();
        setReady();

        while (!isStopped()) {
            try {
                udpComponent.receive(incomingDataPacket);
                processData();

                udpComponent.sendKeepAlivePacket();
            } catch (RuntimeException e) {
                // This happens sometimes, but does not hinder the video data from being displayed
            }
        }

        disconnectFromVideoDataPort();
    }

    private void connectToVideoDataPort() {
        log.info("Connecting to video data port {}", getVideoDataPort());
        udpComponent.connect(getDroneAddress(), getVideoDataPort());
    }

    private void initializeCommunication() {
        udpComponent.sendKeepAlivePacket();
        sleep(1000);
    }

    private void processData() {
        BufferedImage image = getImage();

        log.trace("Received video data - width: {}, height: {}", image.getWidth(), image.getHeight());

        for (VideoDataListener listener : getVideoDataListeners()) {
            listener.onVideoData(image);
        }
    }

    public BufferedImage getImage() {
        imageDecoder.determineImageFromStream(receivingBuffer, incomingDataPacket.getLength());
        int width = imageDecoder.getWidth();
        int height = imageDecoder.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, imageDecoder.getJavaPixelData(), 0, width);

        return image;
    }

    private void disconnectFromVideoDataPort() {
        log.info("Disconnecting from video data port {}", getVideoDataPort());
        udpComponent.disconnect();
    }
}