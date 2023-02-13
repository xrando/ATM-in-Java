package ATM.Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    public static String GetConfig(String Key) {
        try (InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            if (inputStream != null)
                properties.load(inputStream);
            return properties.getProperty(Key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String GetConfig(String Name, String Key) {
        try (InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(Name)) {
            Properties properties = new Properties();
            if (inputStream != null)
                properties.load(inputStream);
            return properties.getProperty(Key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
