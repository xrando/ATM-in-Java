package pure.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A wrapper class for {@link Logger}.
 * <br><br>
 * Use {@link #log(Level, String, Exception)} to record an event to the location specified in config.properties.
 * */
public abstract class LogHelper {
    private final static Logger LOGGER;

    static {
        LOGGER = Logger.getLogger("pure");
        try {
            Files.createDirectories(Paths.get(ConfigurationManager.GetConfig("LogLocation")));
            FileHandler fileHandler = new FileHandler(ConfigurationManager.GetConfig("LogLocation") + LocalDate.now() + ".log", true);
            SimpleFormatter SF = new SimpleFormatter();
            fileHandler.setFormatter(SF);
            LOGGER.addHandler(fileHandler);
        } catch (Exception e) {
            System.out.println("Failed to log. Please check the config.properties has \"LogLocation\" configured correctly.");
        }
    }

    public static void log(Level level, String message, Exception exception) {
        LOGGER.log(level, message, exception);
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, message);
    }
}
