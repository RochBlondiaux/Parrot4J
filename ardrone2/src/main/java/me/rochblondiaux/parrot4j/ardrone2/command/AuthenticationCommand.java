package me.rochblondiaux.parrot4j.ardrone2.command;

import me.rochblondiaux.parrot4j.ardrone2.model.AuthenticationData;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class AuthenticationCommand extends ATCommand {

    private final AuthenticationData data;

    public AuthenticationCommand(AuthenticationData data) {
        super("CONFIG_IDS");
        this.data = data;
    }

    @Override
    protected String build(int sequence) {
        return "%d,\"%s\",\"%s\",\"%s\"".formatted(sequence, data.getSessionChecksum(), data.getProfileChecksum(), data.getApplicationChecksum());
    }
}
