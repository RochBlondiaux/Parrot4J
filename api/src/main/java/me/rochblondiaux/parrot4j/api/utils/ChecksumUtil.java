package me.rochblondiaux.parrot4j.api.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@UtilityClass
public class ChecksumUtil {

    public static final int RANDOM_STRING_LENGTH = 100;
    public static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String createRandomCrc32Hex() {
        return createCrc32Hex(createRandomString());
    }

    public static String createCrc32Hex(String value) {
        byte[] bytes = value.getBytes();
        Checksum checksumCreator = new CRC32();
        checksumCreator.update(bytes, 0, bytes.length);
        long checkSumValue = checksumCreator.getValue();
        return fixLength(Long.toHexString(checkSumValue));
    }

    private static String fixLength(String hexString) {
        StringBuilder hexStringBuilder = new StringBuilder(hexString);
        while (hexStringBuilder.length() != 8)
            hexStringBuilder.insert(0, "0");
        return hexStringBuilder.toString();
    }

    public static String createRandomString() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(RANDOM_STRING_LENGTH);
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++)
            stringBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        return stringBuilder.toString();
    }
}
