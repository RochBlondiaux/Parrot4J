package me.rochblondiaux.parrot4j.data;

import lombok.Getter;
import me.rochblondiaux.parrot4j.helpers.ChecksumHelper;

@Getter
public class LoginData {
    private final String sessionChecksum;

    private final String profileChecksum;

    private final String applicationChecksum;

    public LoginData(String applicationName, String profileName) {
        sessionChecksum = ChecksumHelper.createRandomCrc32Hex();
        applicationChecksum = ChecksumHelper.createCrc32Hex(applicationName);
        profileChecksum = ChecksumHelper.createCrc32Hex(profileName);
    }
}
