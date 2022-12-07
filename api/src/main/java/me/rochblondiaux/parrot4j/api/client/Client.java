package me.rochblondiaux.parrot4j.api.client;

import lombok.Builder;
import me.rochblondiaux.parrot4j.api.driver.DroneDriver;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Builder
public record Client(DroneDriver<?> driver, ClientOptions options) {
}
