package me.rochblondiaux.parrot4j.ardrone2;

import me.rochblondiaux.parrot4j.api.drone.enums.DroneModels;
import me.rochblondiaux.parrot4j.api.drone.model.DroneVersion;
import me.rochblondiaux.parrot4j.api.drone.model.impl.AbstractDrone;
import me.rochblondiaux.parrot4j.ardrone2.configuration.DroneConfiguration;
import me.rochblondiaux.parrot4j.ardrone2.data.DroneData;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class Ar2Drone extends AbstractDrone {


    private final DroneConfiguration configuration;
    private DroneData data;

    public Ar2Drone(@NotNull InetAddress address) {
        super(address, DroneModels.AR_DRONE_2);
        this.configuration = new DroneConfiguration();
    }

    public DroneConfiguration configuration() {
        return configuration;
    }

    public DroneData data() {
        return data;
    }

    public void data(DroneData data) {
        this.data = data;
    }

    public void updateInformation(@NotNull String name, @NotNull String serialNumber, @NotNull String firmwareVersion, @NotNull String hardwareVersion) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.version = DroneVersion.of(firmwareVersion, hardwareVersion);
    }
}
