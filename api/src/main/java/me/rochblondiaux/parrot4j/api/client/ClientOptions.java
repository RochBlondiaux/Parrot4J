package me.rochblondiaux.parrot4j.api.client;

import lombok.Builder;
import lombok.Data;

import java.net.InetAddress;

/**
 * Parrot4J
 * 10/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Data
@Builder
public class ClientOptions {

    private final InetAddress address;
    private final boolean hull;
    private final boolean outdoor;
}
