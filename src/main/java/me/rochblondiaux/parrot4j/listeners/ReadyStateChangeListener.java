package me.rochblondiaux.parrot4j.listeners;

public interface ReadyStateChangeListener {
    public void onReadyStateChange(ReadyState readyState);

    public enum ReadyState {
        READY, NOT_READY
    }
}
