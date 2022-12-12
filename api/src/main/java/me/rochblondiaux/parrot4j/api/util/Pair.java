package me.rochblondiaux.parrot4j.api.util;

import lombok.Data;

/**
 * Parrot4J
 * 11/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Data(staticConstructor = "of")
public class Pair<K, V> {

    private final K key;
    private final V value;


}
