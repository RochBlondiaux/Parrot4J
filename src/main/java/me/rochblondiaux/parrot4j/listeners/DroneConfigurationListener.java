package me.rochblondiaux.parrot4j.listeners;

import me.rochblondiaux.parrot4j.data.DroneConfiguration;

public interface DroneConfigurationListener {
    void onDroneConfiguration(DroneConfiguration config);
}