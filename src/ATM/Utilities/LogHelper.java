package ATM.Utilities;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogHelper {
    public static Logger LOGGER;
    static {
        LOGGER = Logger.getLogger("ATM");
        try {
            Files.createDirectories(Paths.get(ConfigurationManager.GetConfig("LogLocation")));
            FileHandler fileHandler = new FileHandler(ConfigurationManager.GetConfig("LogLocation") + LocalDate.now() + ".log", true);
//            System.setProperty("java.util.logging.SimpleFormatter.format","[%1$tF %1$tT] [%4$-7s] %5$s %n");
            SimpleFormatter SF = new SimpleFormatter();
            fileHandler.setFormatter(SF);
            LOGGER.addHandler(fileHandler);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
