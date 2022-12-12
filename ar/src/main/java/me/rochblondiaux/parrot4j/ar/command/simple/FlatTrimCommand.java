package me.rochblondiaux.parrot4j.ar.command.simple;

import me.rochblondiaux.parrot4j.api.util.Preconditions;
import me.rochblondiaux.parrot4j.ar.command.ATCommand;
import me.rochblondiaux.parrot4j.ar.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ar.data.NavigationData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class FlatTrimCommand extends ATCommand {

    public FlatTrimCommand() {
        super("FTRIM");
    }

    @Override
    protected String build(int sequence) {
        return sequence + "";
    }


    @Override
    public void isSuccessful(@NotNull NavigationData data, @Nullable DroneConfiguration configuration) {
        Preconditions.checkState(data.state().trimReceived(), "Trim order not received");
    }
}
