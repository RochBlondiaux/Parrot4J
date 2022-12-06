package me.rochblondiaux.parrot4j.data;

import lombok.Data;
import me.rochblondiaux.parrot4j.data.enums.ARDrone1VideoCodec;
import me.rochblondiaux.parrot4j.data.enums.ARDrone2VideoCodec;

@Data
public final class Config {
    public static final int WAIT_TIMEOUT = 15;
    public static final int REACHABLE_TIMEOUT = 1000;
    public static final String MIN_FIRMWARE_VERSION = "1.6.4";
    private static final int NO_RETRIES = 0;
    private final LoginData loginData;

    private final int maxStartupRetries;

    private String droneIpAddress = "192.168.1.1";

    private int ftpPort = 5551;

    private int navDataPort = 5554;

    private int videoDataPort = 5555;

    private int commandPort = 5556;

    private int configDataPort = 5559;

    private ARDrone1VideoCodec arDrone1VideoCodec = ARDrone1VideoCodec.P264;

    private ARDrone2VideoCodec arDrone2VideoCodec = ARDrone2VideoCodec.H264_360P;

    public Config(String applicationName, String profileName) {
        this(applicationName, profileName, NO_RETRIES);
    }

    public Config(String applicationName, String profileName, int maxStartupRetries) {
        this.maxStartupRetries = maxStartupRetries;
        loginData = new LoginData(applicationName, profileName);
    }
}