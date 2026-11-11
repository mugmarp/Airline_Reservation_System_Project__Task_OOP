package Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Centralized configuration loader.
 * Reads config.properties from project root (working directory).
 */
public final class Config {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = new FileInputStream("config.properties")) {
            PROPS.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Cannot load config.properties: " + e);
        }
    }

    private Config() {}

    public static String get(String key) {
        return PROPS.getProperty(key);
    }

    public static int getInt(String key) {
        String value = PROPS.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing or empty config property: " + key);
        }
        return Integer.parseInt(value.trim());
    }
}