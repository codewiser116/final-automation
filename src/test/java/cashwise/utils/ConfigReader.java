package cashwise.utils;

import cashwise.base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    protected static Logger logger = LogManager.getLogger(ConfigReader.class);

    static {
        String path = "src/test/resources/config.properties";

        try {
            FileInputStream file = new FileInputStream(path);
            properties = new Properties();
            properties.load(file);
            file.close();
            logger.info("Configuration properties loaded");
        }catch (IOException e){
            logger.error("Failed to load configuration properties", e);
            throw new RuntimeException("Could not load configuration properties", e);
        }
    }


    public static String getProperty(String key){
        return properties.getProperty(key);
    }

    /**
     * Loads configuration properties from the config.properties file.
     */
    public static Properties loadConfigurations() {
        return properties;
    }
}
