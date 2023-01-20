import java.io.*;
import java.nio.charset.StandardCharsets;

public class CSVHelper {
    private static final String CSV_SEPARATOR = ",";
    private static final String file_path = "./Resources/Database/";
    private static final String bank_file = "Bank.csv";
    private static final String Account_file = "Accounts.csv";

    //Declare Headers Here TODO
    private static final String[] User_Headers = {"UID", "Username", "Password", "Salt", "Email", "Phone"};
    //private static final String[] bank_Headers = {};
    //private static final String[] account_Headers = {};

    // Could be used to append to a file TODO
    public static void writeToCSV(String filename, String[] data) throws IOException {
        File file = new File(file_path + filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
        FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8, true);
        if (file.length() == 0) {
            // Create Header
            for (int i = 0; i< User_Headers.length; i++) {
                fw.append(User_Headers[i]);
                if (i != User_Headers.length - 1) {
                    fw.append(CSV_SEPARATOR);
                }
            }
                fw.append("\n");
            }
            //FileWriter fw = new FileWriter(file, true); //Append to EOF
            for (int i = 0; i < data.length; i++) {
                fw.append(data[i]);                             //Append data
                if (i != data.length - 1) {
                    fw.append(CSV_SEPARATOR);                   //Append separator (,) if not last element
                }
            }
            fw.append("\n");                                    //Append new line
            fw.flush();                                         //Flush to file
            fw.close();                                         //Close file
        } catch (IOException e) {
            // Change to logger TODO
            e.printStackTrace();
        }
    }

    // TODO
    public static void ReadCSV(String filename) throws IOException {
        File file = new File(file_path + filename);
        // Won't need this unless we need this for error handling?
        // if (!file.exists()) {
        //     try {
        //         file.createNewFile();
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        // }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                for (int i = 0; i < data.length; i++) {
                    // Change to creating an object TODO
                    System.out.println(data[i]);
                }
            }
        } catch (IOException e) {
            // Change to logger TODO
            e.printStackTrace();
        }
    }
    // Sample usage TODO
    public static void main(String[] args) throws IOException {
        User test = new User("test", "test", "test", "test", new Bank("test"));
        writeToCSV("test.csv", test.UserToArray());
    }
}

