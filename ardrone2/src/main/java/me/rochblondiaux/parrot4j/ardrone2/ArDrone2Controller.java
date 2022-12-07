package me.rochblondiaux.parrot4j.ardrone2;

import lombok.RequiredArgsConstructor;
import me.rochblondiaux.parrot4j.api.controller.DroneController;
import me.rochblondiaux.parrot4j.api.drone.movements.Location;
import me.rochblondiaux.parrot4j.api.drone.movements.Rotation;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public class ArDrone2Controller implements DroneController {

    private final InetAddress address;
    private final ArDrone2 drone;

    @Override
    public CompletableFuture<Void> initialize() {
        return null;
    }

    @Override
    public CompletableFuture<Void> takeOff() {
        return null;
    }

    @Override
    public CompletableFuture<Void> land() {
        return null;
    }

    @Override
    public CompletableFuture<Void> emergency() {
        return null;
    }

    @Override
    public CompletableFuture<Void> move3d(double vx, double vy, double vz, double vr) {
        return null;
    }

    @Override
    public CompletableFuture<Void> move(double vx, double vy, double vr) {
        return null;
    }

    @Override
    public CompletableFuture<Void> move(@NotNull Location location) {
        return null;
    }

    @Override
    public CompletableFuture<Void> rotate(@NotNull Rotation rotation) {
        return null;
    }

    @Override
    public CompletableFuture<Void> cancelMovement() {
        return null;
    }

    @Override
    public CompletableFuture<Void> calibrate(boolean outdoor, boolean hull) {
        return null;
    }

    @Override
    public CompletableFuture<Void> flatTrim() {
        return null;
    }

    @Override
    public void disconnect() {

    }
}
