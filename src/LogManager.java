
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LogManager {
    public static Logger LOGGER;
    static {
        LOGGER = Logger.getLogger("ATM");
        try {
            FileHandler fileHandler = new FileHandler(ConfigurationManager.GetConfig("LogLocation") + LocalDate.now() + ".log", true);
            LOGGER.addHandler(fileHandler);
        }catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
}
