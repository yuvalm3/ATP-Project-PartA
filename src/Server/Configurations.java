package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton to load application settings from resources/config.properties.
 */
public class Configurations {
    private static Configurations instance;
    private final Properties props = new Properties();

    private Configurations() {
        try (InputStream in = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static synchronized Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    /** Returns the raw String value or throws if missing. */
    public String getProperty(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing property: " + key);
        }
        return value;
    }

    /** Returns an int parsed from the property. */
    public int getInt(String key) {
        return Integer.parseInt(getProperty(key));
    }
}
