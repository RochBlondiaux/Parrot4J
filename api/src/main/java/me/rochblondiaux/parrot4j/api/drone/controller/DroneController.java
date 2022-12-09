package me.rochblondiaux.parrot4j.api.drone.controller;

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
public interface DroneController {

    /**
     * Initiate the connection with the drone.
     *
     * @return A {@link CompletableFuture} that will be completed when the connection is established.
     */
    CompletableFuture<Void> initialize();

    /**
     * Take off the drone.
     *
     * @return A {@link CompletableFuture} that will be completed when the drone is flying.
     */
    CompletableFuture<Void> takeOff();

    /**
     * Land the drone.
     *
     * @return A {@link CompletableFuture} that will be completed when the drone is landed.
     */
    CompletableFuture<Void> land();

    /**
     * Emergency landing.
     *
     * @return A {@link CompletableFuture} that will be completed when the drone is landed.
     */
    CompletableFuture<Void> emergency();

    /***
     * Moves the drone in a 3D plane
     * @param vx Pitch left/right between [-1;1]
     * @param vy Pitch forward/backward between [-1;1]
     * @param vz Up/down between [-1;1]
     * @param vr Rotate left/right between [-1;1]
     * @return A {@link CompletableFuture} that will be completed when the drone has moved.
     */
    CompletableFuture<Void> move3d(double vx, double vy, double vz, double vr);

    /***
     * Moves the drone in a 2D plane
     * @param vx Pitch left/right between [-1;1]
     * @param vy Pitch forward/backward between [-1;1]
     * @param vr Rotate left/right between [-1;1]
     * @return A {@link CompletableFuture} that will be completed when the drone has moved.
     */
    CompletableFuture<Void> move(double vx, double vy, double vr);

    /**
     * Moves the drone to a specific location.
     *
     * @param location The location to move to.
     * @return A {@link CompletableFuture} that will be completed when the drone has moved.
     */
    CompletableFuture<Void> move(@NotNull Location location);

    /**
     * Rotates the drone to a specific rotation.
     *
     * @param rotation The rotation to move to.
     * @return A {@link CompletableFuture} that will be completed when the drone has rotated.
     */
    CompletableFuture<Void> rotate(@NotNull Rotation rotation, float gas);

    /**
     * Cancel the current movement.
     *
     * @return A {@link CompletableFuture} that will be completed when the drone has stopped.
     */
    CompletableFuture<Void> cancelMovement();

    /**
     * Calibrates the drone when it is flying.
     *
     * @return A {@link CompletableFuture} that will be completed when the drone has been calibrated.
     */
    CompletableFuture<Void> calibrate();

    /**
     * Calibrates the drone when on the ground with the current settings.
     *
     * @return A {@link CompletableFuture} that will be completed when the drone has been calibrated.
     */
    CompletableFuture<Void> flatTrim();

    /**
     * Disconnect the drone.
     */
    void disconnect();

}
