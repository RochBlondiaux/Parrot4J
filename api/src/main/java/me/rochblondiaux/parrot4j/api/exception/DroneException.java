package me.rochblondiaux.parrot4j.api.exception;

/**
 * Parrot4J
 * 06/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class DroneException extends RuntimeException {
    public DroneException(String cause) {
        super(cause);
    }
}