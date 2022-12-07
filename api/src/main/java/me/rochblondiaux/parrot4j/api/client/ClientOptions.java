package me.rochblondiaux.parrot4j.api.client;

import lombok.Builder;
import me.rochblondiaux.parrot4j.api.drone.model.DronePort;

import java.net.InetAddress;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Builder
public record ClientOptions(InetAddress address, DronePort ports, boolean gps, boolean hull, boolean outdoor) {
}
