package me.rochblondiaux.parrot4j.api.util;

import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 12/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class Preconditions {

    public static void checkState(boolean expression, @Nullable String errorMessage) {
        if (!expression)
            throw new IllegalStateException(errorMessage);
    }

    public static void checkNotNull(@Nullable Object reference, @Nullable String errorMessage) {
        if (reference == null)
            throw new NullPointerException(errorMessage);
    }
}
