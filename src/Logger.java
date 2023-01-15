import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
public class Logger {
    public static void WriteLog(String Log) throws IOException {
        InputStream inputStream;
        Properties prop = new Properties();
        String propFileName = "config.properties";
        inputStream = Logger.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            prop.load(inputStream);
        } else
        {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
        String LogLocation = prop.getProperty("LogLocation");
        File file = new File(LogLocation + LocalDate.now() + ".txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file, true);
        writer.write("\n" + LocalDateTime.now() + ": " + Log);
        writer.close();
    }
}
