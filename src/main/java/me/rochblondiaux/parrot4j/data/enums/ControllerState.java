package me.rochblondiaux.parrot4j.data.enums;

public enum ControllerState {
    STARTED,
    COMMAND_ONE_RETRIEVER_READY,
    COMMAND_TWO_RETRIEVERS_READY,
    WORKERS_READY,
    READY,
    STOPPED;

    public ControllerState getNextState() {
        return switch (this) {
            case STARTED -> COMMAND_ONE_RETRIEVER_READY;
            case COMMAND_ONE_RETRIEVER_READY -> COMMAND_TWO_RETRIEVERS_READY;
            case COMMAND_TWO_RETRIEVERS_READY -> WORKERS_READY;
            case WORKERS_READY -> READY;
            default -> this;
        };
    }
}
