package me.rochblondiaux.parrot4j.api.network;

import lombok.RequiredArgsConstructor;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public abstract class AbstractConnection implements NetworkConnection {

    protected static final byte[] KEEP_ALIVE_BYTES = new byte[]{0x01, 0x00, 0x00, 0x00};

    private final InetSocketAddress address;

    @Override
    public CompletableFuture<Void> reconnect() {
        return disconnect()
                .thenAccept(unused -> connect());
    }

    @Override
    public InetSocketAddress address() {
        return address;
    }

    public abstract boolean isConnected();
}
