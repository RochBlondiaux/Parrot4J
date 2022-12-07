package me.rochblondiaux.parrot4j.api.utils;

import lombok.experimental.UtilityClass;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@UtilityClass
public class VersionUtil {

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
