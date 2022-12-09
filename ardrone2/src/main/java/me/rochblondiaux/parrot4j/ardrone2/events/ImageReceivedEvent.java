package me.rochblondiaux.parrot4j.ardrone2.events;

import me.rochblondiaux.parrot4j.api.event.Event;

import java.awt.image.BufferedImage;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public record ImageReceivedEvent(BufferedImage image) implements Event {
}
