package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.model.ConfigurationKeys;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ConfigureCommand extends ATCommand {

    private final ConfigurationKeys key;
    private final String value;


    /**
     * @param seq   The sequence number of the command
     * @param key   The key of the config command
     * @param value The value of the config command
     */
    public ConfigureCommand(int seq, ConfigurationKeys key, String value) {
        super(seq, "CONFIG");
        this.key = key;
        this.value = value;
    }

    /**
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d,\"%s\",\"%s\"", seq, key.getKey(), value);
    }
}