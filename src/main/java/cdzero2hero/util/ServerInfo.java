package cdzero2hero.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerInfo {
    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "ERROR: " + e;
        }
    }

    public String getVersion() {
        return new Scanner(getClass().getClassLoader().getResourceAsStream("version.txt")).useDelimiter("\\Z").next();
    }
}
