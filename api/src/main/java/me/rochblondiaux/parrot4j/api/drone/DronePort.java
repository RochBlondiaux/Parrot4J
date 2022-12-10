package me.rochblondiaux.parrot4j.api.drone;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public interface DronePort {

    int ftp();

    int authentication();

    int commands();

    int video();

    int videoRecorder();

    int logs();

    int control();

    int navigation();

    int media();
}
