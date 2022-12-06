package me.rochblondiaux.parrot4j.commands.composed;

import com.google.common.collect.Lists;
import me.rochblondiaux.parrot4j.commands.Command;
import me.rochblondiaux.parrot4j.commands.simple.ControlDataATCommand;
import me.rochblondiaux.parrot4j.data.enums.ControlDataMode;

import java.util.Collection;

public class GetConfigurationDataCommand extends UnconditionalComposedCommandAbstract {
    @Override
    public Collection<Command> getCommands() {
        Command resetAckFlagCommand = new ControlDataATCommand(ControlDataMode.RESET_ACK_FLAG);
        Command getConfigurationDataCommand = new ControlDataATCommand(ControlDataMode.GET_CONFIGURATION_DATA);

        return Lists.newArrayList(resetAckFlagCommand, getConfigurationDataCommand);
    }
}