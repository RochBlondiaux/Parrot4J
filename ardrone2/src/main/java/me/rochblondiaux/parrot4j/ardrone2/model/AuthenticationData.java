package me.rochblondiaux.parrot4j.ardrone2.model;

import lombok.Getter;
import me.rochblondiaux.parrot4j.ardrone2.utils.ChecksumUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Parrot4J
 * 08/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
@Getter
public class AuthenticationData {

    private final String sessionChecksum;
    private final String profileChecksum;
    private final String applicationChecksum;

    public AuthenticationData(@NotNull String applicationName, @NotNull String profileName) {
        this.sessionChecksum = ChecksumUtil.createRandomCrc32Hex();
        this.applicationChecksum = ChecksumUtil.createCrc32Hex(applicationName);
        this.profileChecksum = ChecksumUtil.createCrc32Hex(profileName);
    }
}
