package me.rochblondiaux.parrot4j.api.video;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public abstract class VideoRetriever extends Thread {

    public VideoRetriever(String name) {
        super("VideoRetriever-%s".formatted(name));
    }

}
