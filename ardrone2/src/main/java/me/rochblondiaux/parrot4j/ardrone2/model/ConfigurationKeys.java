package me.rochblondiaux.parrot4j.ardrone2.model;

import lombok.RequiredArgsConstructor;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
public enum ConfigurationKeys {
    // "general" configs
    GENERAL_NUM_VERSION_CONFIG("num_version_config", ConfigurationKeys.GENERAL),
    GENERAL_NUM_VERSION_MB("num_version_mb", ConfigurationKeys.GENERAL),
    GENERAL_NUM_VERSION_SOFT("num_version_soft", ConfigurationKeys.GENERAL),
    GENERAL_DRONE_SERIAL("drone_serial", ConfigurationKeys.GENERAL),
    GENERAL_SOFT_BUILD_DATE("soft_build_date", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR1_SOFT("motor1_soft", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR1_HARD("motor1_hard", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR1_SUPPLIER("motor1_supplier", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR2_SOFT("motor2_soft", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR2_HARD("motor2_hard", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR2_SUPPLIER("motor2_supplier", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR3_SOFT("motor3_soft", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR3_HARD("motor3_hard", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR3_SUPPLIER("motor3_supplier", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR4_SOFT("motor4_soft", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR4_HARD("motor4_hard", ConfigurationKeys.GENERAL),
    GENERAL_MOTOR4_SUPPLIER("motor4_supplier", ConfigurationKeys.GENERAL),
    GENERAL_AR_DRONE_NAME("ardrone_name", ConfigurationKeys.GENERAL),
    GENERAL_FLYING_TIME("flying_time", ConfigurationKeys.GENERAL),
    GENERAL_NAV_DATA_DEMO("navdata_demo", ConfigurationKeys.GENERAL),
    GENERAL_NAV_DATA_OPTIONS("navdata_options", ConfigurationKeys.GENERAL),
    GENERAL_COM_WATCHDOG("com_watchdog", ConfigurationKeys.GENERAL),
    GENERAL_VIDEO_ENABLE("video_enable", ConfigurationKeys.GENERAL),
    GENERAL_VISION_ENABLE("vision_enable", ConfigurationKeys.GENERAL),
    GENERAL_V_BAT_MIN("vbat_min", ConfigurationKeys.GENERAL),
    // control configs
    CONTROL_CONTROL_LEVEL("control_level", ConfigurationKeys.CONTROL),
    CONTROL_EULER_ANGLE_MAX("euler_angle_max", ConfigurationKeys.CONTROL),
    CONTROL_ALTITUDE_MAX("altitude_max", ConfigurationKeys.CONTROL),
    CONTROL_ALTITUDE_MIN("altitude_min", ConfigurationKeys.CONTROL),
    CONTROL_CONTROL_VZ_MAX("control_vz_max", ConfigurationKeys.CONTROL),
    CONTROL_CONTROL_YAW("control_yaw", ConfigurationKeys.CONTROL),
    CONTROL_OUTDOOR("outdoor", ConfigurationKeys.CONTROL),
    CONTROL_FLIGHT_WITHOUT_SHELL("flight_without_shell", ConfigurationKeys.CONTROL),
    CONTROL_FLYING_MODE("flying_mode", ConfigurationKeys.CONTROL),
    // custom configs
    CUSTOM_APPLICATION_ID("application_id", ConfigurationKeys.CUSTOM),
    CUSTOM_PROFILE_ID("profile_id", ConfigurationKeys.CUSTOM),
    CUSTOM_SESSION_ID("session_id", ConfigurationKeys.CUSTOM),
    // video configs
    VIDEO_BITRATE("bitrate", ConfigurationKeys.VIDEO),
    VIDEO_BITRATE_MAX("max_bitrate", ConfigurationKeys.VIDEO),
    VIDEO_CODEC("video_codec", ConfigurationKeys.VIDEO),
    VIDEO_CHANNEL("video_channel", ConfigurationKeys.VIDEO),
    VIDEO_ON_USB("video_on_usb", ConfigurationKeys.VIDEO),
    VIDEO_BITRATE_CTRL_MODE("bitrate_ctrl_mode", ConfigurationKeys.VIDEO);

    private static final String GENERAL = "general";
    private static final String CONTROL = "control";
    private static final String CUSTOM = "custom";
    private static final String VIDEO = "video";

    private final String key;
    private final String configClass;

    public String key() {
        return String.format("%s:%s", configClass, key);
    }
}