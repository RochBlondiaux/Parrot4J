package me.rochblondiaux.parrot4j.ardrone2.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@RequiredArgsConstructor
@Getter
public enum NavigationData {
    NAV_HEADER_OFFSET(0),
    NAV_STATE_OFFSET(4),
    NAV_CTRL_STATE_OFFSET(20),
    NAV_BATTERY_OFFSET(24),
    NAV_PITCH_OFFSET(28),
    NAV_ROLL_OFFSET(32),
    NAV_YAW_OFFSET(36),
    NAV_ALTITUDE_OFFSET(40),
    NAV_VX_OFFSET(44),
    NAV_VY_OFFSET(48),
    NAV_VZ_OFFSET(52),
    // REST OF NAVDATA_DEMO IS UNUSED
    NAV_GPS_LATITUDE_OFFSET(44), // @TODO
    NAV_GPS_LONGITUDE_OFFSET(48), // @TODO
    NAV_GPS_ELEVATION_OFFSET(48), // @TODO
    NAV_GPS_DATA_AVAILABLE_OFFSET(48); // @TODO

    private final int offset;

}
