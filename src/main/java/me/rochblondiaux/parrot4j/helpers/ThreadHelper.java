package me.rochblondiaux.parrot4j.helpers;

public class ThreadHelper {
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
