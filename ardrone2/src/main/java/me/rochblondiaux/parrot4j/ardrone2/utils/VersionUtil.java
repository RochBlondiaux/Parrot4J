package me.rochblondiaux.parrot4j.ardrone2.utils;

import lombok.experimental.UtilityClass;
import me.rochblondiaux.parrot4j.api.drone.enums.DroneModels;
import me.rochblondiaux.parrot4j.api.drone.model.DroneModel;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@UtilityClass
public class VersionUtil {

    public static final String VERSION_SEPARATOR = "\\.";

    public static DroneModel fromVersionNumber(String versionNumber) {
        return getMajorVersion(versionNumber) == 2 ? DroneModels.AR_DRONE_2 : DroneModels.AR_DRONE_1;
    }

    public static int getMajorVersion(String versionNumber) {
        final String[] versionDetails = versionNumber.split(VERSION_SEPARATOR);
        int majorVersion;
        try {
            majorVersion = Integer.parseInt(versionDetails[0]);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("The version file did not contain the drone version");
        }
        if (majorVersion != 1 && majorVersion != 2)
            throw new IllegalStateException("Major version must either be 1 or 2");
        return majorVersion;
    }
}
