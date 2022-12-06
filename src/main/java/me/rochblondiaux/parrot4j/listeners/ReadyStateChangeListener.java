package me.rochblondiaux.parrot4j.listeners;

public interface ReadyStateChangeListener {
    void onReadyStateChange(ReadyState readyState);

    enum ReadyState {
        READY, NOT_READY
    }
}
