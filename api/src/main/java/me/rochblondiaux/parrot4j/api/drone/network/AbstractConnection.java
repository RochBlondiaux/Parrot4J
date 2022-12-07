package me.rochblondiaux.parrot4j.api.drone.network;

import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@ApiStatus.Internal
public abstract class AbstractConnection implements DroneConnection {

    protected static final byte[] KEEP_ALIVE_BYTES = new byte[]{0x01, 0x00, 0x00, 0x00};

    protected final InetAddress address;
    protected final int port;
    @Getter
    protected boolean connected;

    public AbstractConnection(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.connected = false;
    }

    @Override
    public CompletableFuture<Void> reconnect(int delay) {
        return CompletableFuture.supplyAsync(() -> {
            this.disconnect();
            try {
                Thread.sleep(delay * 1000L);
                this.connect().join();
                return null;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public @NotNull InetAddress address() {
        return this.address;
    }

    @Override
    public int port() {
        return this.port;
    }
}
