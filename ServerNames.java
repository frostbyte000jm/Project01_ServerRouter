
/*
*   To get the information below run a Termanail (which ever you choose)
*   run ipconfig /all
*   Collect your *Host Name* and your *IPv4 address*.
*   Enter them in the valid fields below.
*
* */

public class ServerNames {
    private static String routerName = "DESKTOP-DEOBPOO";
    private static String serverAddress = "192.168.1.120";
    private static String clientAddress = "192.168.1.120";
    private static String textFile = "C:\\Temp\\file.txt";
    private static String vidFile = "C:\\Temp\\drop.avi";


    public static String getRouterName() {
        return routerName;
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static String getClientAddress() {
        return clientAddress;
    }

    public static String getTextFile() {
        return textFile;
    }

    public static String getVidFile() {
        return vidFile;
    }
}
