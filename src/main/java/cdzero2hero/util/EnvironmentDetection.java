package cdzero2hero.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class EnvironmentDetection {
    private EnvironmentDetection() {}

    public static String detectEnvironment() {
        try {
            if (classExists("com.gargoylesoftware.htmlunit.WebClient")) {
                return "largetest";
            }

            String hostname = InetAddress.getLocalHost().getHostName();
            if (hostname.startsWith("cdzero2hero-app")) {
                return "prod";
            }

            return "local";
        } catch (UnknownHostException e) {
            throw new IllegalStateException("Unable to detect environment", e);
        }
    }

    private static boolean classExists(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
