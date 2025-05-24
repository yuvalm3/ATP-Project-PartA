package Server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Singleton to load and store application settings from properties.config in resources.
 */
public class Configurations {
    private static Configurations instance;
    private final Properties properties;
    private final String filePath;

    private Configurations() {
        properties = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("properties.config")) {
            if (in == null) throw new FileNotFoundException("properties.config not found in resources");
            properties.load(in);
            URL url = getClass().getClassLoader().getResource("properties.config");
            filePath = url != null ? Paths.get(url.toURI()).toString() : null;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load properties.config", e);
        }
    }

    public static synchronized Configurations getInstance() {
        if (instance == null) instance = new Configurations();
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void save() {
        if (filePath == null) throw new IllegalStateException("Cannot save properties: file path unknown");
        try (OutputStream out = new FileOutputStream(filePath)) {
            properties.store(out, null);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save properties.config", e);
        }
    }
}
