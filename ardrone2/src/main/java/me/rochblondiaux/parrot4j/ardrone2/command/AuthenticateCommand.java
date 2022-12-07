package me.rochblondiaux.parrot4j.ardrone2.command;

/**
 * Parrot4J
 * 07/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class AuthenticateCommand extends ATCommand {

    // The config IDs
    private final String session;
    private final String user;
    private final String appIDS;

    // Default session IDs
    private static final String ARDRONE_SESSION_ID = "d2e081a3";  // SessionID
    private static final String ARDRONE_PROFILE_ID = "be27e2e4";  // Profile ID
    private static final String ARDRONE_APPLICATION_ID = "d87f7e0c";  // Application ID

    /**
     * This command will send the default identification
     *
     * @param seq The sequence number of the command
     */
    public AuthenticateCommand(int seq) {
        this(seq, ARDRONE_SESSION_ID, ARDRONE_PROFILE_ID, ARDRONE_APPLICATION_ID);
    }

    /**
     * @param seq     The sequence number of the command
     * @param session The session ID
     * @param user    The user ID
     * @param appIDS  The application ID
     */
    public AuthenticateCommand(int seq, String session, String user, String appIDS) {
        super(seq, "CONFIG_IDS");

        this.session = session;
        this.user = user;
        this.appIDS = appIDS;
    }

    /**
     * @return The parameters returned as a string. They are separated by a ",".
     */
    @Override
    protected String parametersToString() {
        return String.format("%d,\"%s\",\"%s\",\"%s\"", seq, session, user, appIDS);
    }
}