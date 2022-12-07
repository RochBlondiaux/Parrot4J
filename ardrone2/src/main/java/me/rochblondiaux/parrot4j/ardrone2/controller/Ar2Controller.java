package me.rochblondiaux.parrot4j.ardrone2.controller;

import me.rochblondiaux.parrot4j.api.drone.controller.DroneController;
import me.rochblondiaux.parrot4j.api.model.Location;
import me.rochblondiaux.parrot4j.api.model.Rotation;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class Ar2Controller implements DroneController {
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
