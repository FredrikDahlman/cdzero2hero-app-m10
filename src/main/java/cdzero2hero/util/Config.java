package cdzero2hero.util;

import java.io.IOException;
import java.util.Properties;

public enum Config {
    INSTANCE;

    private final Properties properties = new Properties();

    private Config() {
        String environment = EnvironmentDetection.detectEnvironment();
        String resource = "config/" + environment + ".properties";
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(resource));
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load configuration: " + resource);
        }
    }

    public int getHttpPort() {
        return Integer.parseInt(properties.getProperty("http.port"));
    }

    public int getShutdownPort() {
        return Integer.parseInt(properties.getProperty("shutdown.port"));
    }

    public String getBackgroundColor() {
        return properties.getProperty("backgroundcolor");
    }

    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public boolean isDbTestData() {
        return Boolean.parseBoolean(properties.getProperty("db.testdata"));
    }
}
