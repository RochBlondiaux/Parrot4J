package me.rochblondiaux.parrot4j;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import me.rochblondiaux.parrot4j.components.UrlConnectionComponent;
import me.rochblondiaux.parrot4j.data.enums.DroneVersion;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class VersionReader {
    private static final String FTP_URL_SCHEMA = "ftp:";
    private static final String VERSION_FILE_NAME = "version.txt";
    private final UrlConnectionComponent urlConnectionComponent;

    @Inject
    public VersionReader(UrlConnectionComponent urlConnectionComponent) {
        this.urlConnectionComponent = urlConnectionComponent;
    }

    public DroneVersion getDroneVersion(String ipAddress, int port) {
        String ftpFilePath = String.format("%s//%s:%d/%s", FTP_URL_SCHEMA, ipAddress, port, VERSION_FILE_NAME);

        try {
            List<String> fileLines = getFileContent(ftpFilePath);
            String versionNumber = getVersionLine(fileLines);

            return DroneVersion.fromVersionNumber(versionNumber);
        } catch (Exception e) {
            throw new IllegalStateException("There was an error while determining the drone version", e);
        }
    }

    private List<String> getFileContent(String ftpFilePath) {
        urlConnectionComponent.connect(ftpFilePath);
        List<String> lines = Lists.newArrayList(urlConnectionComponent.readLines());
        urlConnectionComponent.disconnect();

        return lines;
    }

    private String getVersionLine(List<String> fileLines) {
        checkState(fileLines.size() == 1, "The version file must contain exactly one line");
        return fileLines.get(0);
    }
}