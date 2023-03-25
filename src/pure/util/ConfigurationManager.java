package pure.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Read the config.properties file and retrieve values based on the key input.
 * */
public abstract class ConfigurationManager {
    public static String GetConfig(String Key) {
        String returnVal;
        try (InputStream inputStream = new FileInputStream("Resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            returnVal = properties.getProperty(Key);
            return returnVal;
        } catch (NullPointerException e) {
            LogHelper.log(Level.SEVERE, "Could not find the config file.", e);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Failed to read the config file.", e);
        } catch (IllegalArgumentException e) {
            LogHelper.log(Level.SEVERE, "Input stream contains a malformed Unicode escape sequence.", e);
        }
        throw new RuntimeException("Error happened while attempting to read the config file.");
    }

    public static int GetConfigAsInt(String Key) {
        int returnVal;
        try {
            returnVal = Integer.parseInt(GetConfig(Key));
            return returnVal;
        } catch (NumberFormatException e) {
            LogHelper.log(Level.SEVERE, "Please check the config file if " + Key +  " is int format.", e);
        }
        throw new RuntimeException("Error happened while attempting to read the config file.");
    }
}
