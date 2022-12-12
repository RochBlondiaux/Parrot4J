package me.rochblondiaux.parrot4j.ar.configuration;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class DroneConfiguration {

    private Map<ConfigurationKeys, String> data;

    public DroneConfiguration() {
        this.data = new HashMap<>();
    }

    public void update(Map<ConfigurationKeys, String> data) {
        this.data = data;
    }

    public @Nullable String get(ConfigurationKeys key) {
        return data.get(key);
    }

    public void set(ConfigurationKeys key, String value) {
        data.put(key, value);
    }

    public Map<ConfigurationKeys, String> data() {
        return data;
    }
}
