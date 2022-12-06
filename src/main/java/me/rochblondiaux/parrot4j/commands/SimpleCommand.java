package me.rochblondiaux.parrot4j.commands;

import me.rochblondiaux.parrot4j.CommandSender;
import me.rochblondiaux.parrot4j.CommandSenderCoordinator;

public interface SimpleCommand extends Command {
    void execute(CommandSender commandSender, CommandSenderCoordinator commandSenderCoordinator);
}