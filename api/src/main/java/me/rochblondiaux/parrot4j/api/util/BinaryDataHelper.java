package me.rochblondiaux.parrot4j.api.util;

import lombok.experimental.UtilityClass;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@UtilityClass
public class BinaryDataHelper {

    public static int getUnsignedByteValue(byte by) {
        return (int) (by & 0xffL);
    }

    public static int getIntValue(byte[] data, int offset, int length) {
        int tempValue;
        int integerValue = 0;

        for (int i = length - 1; i >= 0; i--) {
            integerValue <<= 8;
            tempValue = data[offset + i] & 0xFF;
            integerValue |= tempValue;
        }

        return integerValue;
    }

    public static float getFloatValue(byte[] data, int offset, int length) {
        return Float.intBitsToFloat(getIntValue(data, offset, length));
    }

    public static int getNormalizedIntValue(Float value) {
        return Float.floatToIntBits(Math.max(-1.0f, Math.min(1.0f, value)));
    }


    public static boolean flagSet(int flagsValue, int index) {
        return (flagsValue & (1 << index)) != 0;
    }
}
