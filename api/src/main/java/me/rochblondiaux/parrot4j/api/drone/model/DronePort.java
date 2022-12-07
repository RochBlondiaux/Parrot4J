package me.rochblondiaux.parrot4j.api.drone.model;

/**
 * Parrot4J
 * 07/12/2022
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
