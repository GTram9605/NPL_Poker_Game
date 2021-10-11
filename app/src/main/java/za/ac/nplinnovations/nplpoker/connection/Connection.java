package za.ac.nplinnovations.nplpoker.connection;

public class Connection {
    private static String ADDRESS = "api.pokerapi.dev/v1/winner/";
    private final static String PROTOCOL = "https://";

    public static String getUrl() {
        return PROTOCOL + ADDRESS;
    }

    public static String getAddress() {
        return ADDRESS;
    }

    public static String getProtocol() {
        return PROTOCOL;
    }
}
