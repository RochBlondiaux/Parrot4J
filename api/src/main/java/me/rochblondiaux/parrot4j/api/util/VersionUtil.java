package me.rochblondiaux.parrot4j.api.util;

import lombok.experimental.UtilityClass;
import me.rochblondiaux.parrot4j.api.drone.implementation.DroneModels;

import java.util.Arrays;
import java.util.Optional;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@UtilityClass
public class VersionUtil {

    public static final String VERSION_SEPARATOR = "\\.";

    public static Optional<DroneModels> fromVersionNumber(String model, String versionNumber) {
        return Arrays.stream(DroneModels.values())
                .filter(m -> m.model().equalsIgnoreCase(model) && m.majorVersion() == getMajorVersion(versionNumber))
                .findFirst();
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

    public static int compareVersions(String version1Text, String version2Text) {
        final String[] versionValues1 = version1Text.split("\\.");
        final String[] versionValues2 = version2Text.split("\\.");

        int i = 0;
        while (i < versionValues1.length
                && i < versionValues2.length
                && versionValues1[i].equals(versionValues2[i]))
            i++;

        if (i < versionValues1.length
                && i < versionValues2.length) {
            int diff = Integer.valueOf(versionValues1[i]).compareTo(Integer.valueOf(versionValues2[i]));
            return Integer.compare(diff, 0);
        }
        return Integer.compare(versionValues1.length, versionValues2.length);
    }
}
