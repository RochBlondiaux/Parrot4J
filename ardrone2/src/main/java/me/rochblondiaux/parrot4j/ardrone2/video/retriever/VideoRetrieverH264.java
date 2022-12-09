package me.rochblondiaux.parrot4j.ardrone2.video.retriever;

import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.api.network.TCPConnection;
import me.rochblondiaux.parrot4j.api.video.VideoRetriever;
import me.rochblondiaux.parrot4j.ardrone2.controller.Ar2Controller;
import me.rochblondiaux.parrot4j.ardrone2.video.decoder.H264VideoDecoder;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Log4j2
public class VideoRetrieverH264 extends VideoRetriever {

    private final TCPConnection connection;
    private final H264VideoDecoder decoder;

    public VideoRetrieverH264(@NotNull Ar2Controller controller) {
        super("h264");
        this.connection = new TCPConnection(new InetSocketAddress(controller.getOptions().address(), controller.getOptions().ports().video()));
        this.decoder = new H264VideoDecoder(connection);
    }

    @Override
    public void run() {
        log.info("Connecting to video stream channel...");
        connect().join();

        while (!isInterrupted()) {
            this.decoder.start();

            if (!connection.isConnected())
                reconnect().join();
        }
        disconnect();
    }

    @Override
    public void interrupt() {
        super.interrupt();
        this.decoder.stop();
    }

    private void init() {
        this.connection.sendKeepAlivePacket();
    }

    private void disconnect() {
        log.info("Disconnecting from video stream channel...");
        this.connection.disconnect();
        log.info("Video stream channel disconnected.");
    }

    private CompletableFuture<Void> reconnect() {
        log.info("Reconnecting to video stream...");
        return this.connection.reconnect()
                .exceptionally(throwable -> {
                    log.error("Failed to reconnect to video stream.", throwable);
                    return null;
                }).thenAccept(unused -> {
                    log.info("Reconnected to video stream.");
                    init();
                });
    }

    private CompletableFuture<Void> connect() {
        log.info("Connecting to video data port...");
        return this.connection.connect()
                .exceptionally(throwable -> {
                    log.error("Failed to connect to video data port.", throwable);
                    return null;
                }).thenAccept(unused -> {
                    log.info("Connected to video data port.");
                    init();
                });
    }

}
