package me.rochblondiaux.parrot4j;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.parrot4j.components.AddressComponent;
import me.rochblondiaux.parrot4j.components.ErrorListenerComponent;
import me.rochblondiaux.parrot4j.components.ReadyStateListenerComponent;
import me.rochblondiaux.parrot4j.components.ThreadComponent;
import me.rochblondiaux.parrot4j.listeners.ReadyStateChangeListener;
import me.rochblondiaux.parrot4j.listeners.VideoDataListener;

import java.net.InetAddress;
import java.util.Set;

@Log4j2
public abstract class VideoRetrieverAbstract implements Runnable {
    private final ThreadComponent threadComponent;

    private final AddressComponent addressComponent;

    private final ReadyStateListenerComponent readyStateListenerComponent;

    private final ErrorListenerComponent errorListenerComponent;

    private final Set<VideoDataListener> videoDataListeners;

    private InetAddress droneAddress;

    private int videoDataPort;

    @Inject
    public VideoRetrieverAbstract(ThreadComponent threadComponent, AddressComponent addressComponent,
                                  ReadyStateListenerComponent readyStateListenerComponent, ErrorListenerComponent errorListenerComponent) {
        super();

        this.threadComponent = threadComponent;
        this.addressComponent = addressComponent;
        this.readyStateListenerComponent = readyStateListenerComponent;
        this.errorListenerComponent = errorListenerComponent;

        videoDataListeners = Sets.newLinkedHashSet();
    }

    public void start(String droneIpAddress, int videoDataPort) {
        droneAddress = addressComponent.getInetAddress(droneIpAddress);
        this.videoDataPort = videoDataPort;

        log.info("Starting video thread");
        threadComponent.start(this);
    }

    public void stop() {
        log.info("Stopping video thread");
        threadComponent.stopAndWait();
    }

    public void addReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateListenerComponent.addReadyStateChangeListener(readyStateChangeListener);
    }

    public void removeReadyStateChangeListener(ReadyStateChangeListener readyStateChangeListener) {
        readyStateListenerComponent.addReadyStateChangeListener(readyStateChangeListener);
    }

    public void addVideoDataListener(VideoDataListener videoDataListener) {
        videoDataListeners.add(videoDataListener);
    }

    public void removeVideoDataListener(VideoDataListener videoDataListener) {
        videoDataListeners.remove(videoDataListener);
    }

    protected Set<VideoDataListener> getVideoDataListeners() {
        return videoDataListeners;
    }

    protected InetAddress getDroneAddress() {
        return droneAddress;
    }

    protected boolean isStopped() {
        return threadComponent.isStopped();
    }

    protected void setReady() {
        readyStateListenerComponent.emitReadyStateChange(ReadyStateChangeListener.ReadyState.READY);
    }

    public int getVideoDataPort() {
        return videoDataPort;
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Throwable e) {
            errorListenerComponent.emitError(e);
        }
    }

    protected abstract void doRun();
}