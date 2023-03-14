package pure.util;

import pure.constants.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

public abstract class ConfigurationManager {
    public static String GetConfig(String Key) {
        String returnVal = null;
        try (InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_NAME)) {
            Properties properties = new Properties();
            if (inputStream != null)
                properties.load(inputStream);
            returnVal = properties.getProperty(Key);
        } catch (NullPointerException e) {
            LogHelper.log(Level.SEVERE, "Could not find the config file.", e);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Failed to read the config file.", e);
        } catch (IllegalArgumentException e) {
            LogHelper.log(Level.SEVERE, "Input stream contains a malformed Unicode escape sequence.", e);
        }
        return returnVal;
    }

    public static int GetConfigAsInt(String Key) {
        int returnVal = 0;
        try (InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_NAME)) {
            Properties properties = new Properties();
            if (inputStream != null)
                properties.load(inputStream);
            returnVal = Integer.parseInt(properties.getProperty(Key));
        } catch (NullPointerException e) {
            LogHelper.log(Level.SEVERE, "Could not find the config file.", e);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Failed to read the config file.", e);
        } catch (IllegalArgumentException e) {
            LogHelper.log(Level.SEVERE, "Config for " + Key + " should be integer format, or the input stream contains a malformed Unicode escape sequence.", e);
        }
        return returnVal;
    }
}
