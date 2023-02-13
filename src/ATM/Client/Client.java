package ATM.Client;
import ATM.Utilities.ATMSocket;
import ATM.Utilities.ConfigurationManager;

import java.io.IOException;

public class Client {
    private final ATMSocket socket;
    public Client() throws IOException {
        //get configurations from external config file
        String HOST = ConfigurationManager.GetConfig("Server");
        int PORT = Integer.parseInt(ConfigurationManager.GetConfig("Port"));
        socket = new ATMSocket(HOST, PORT); //start new instance of client socket
    }

    public String listen(String input) throws IOException {
        this.socket.write(input);
        return socket.read();
    }

    public void close() throws IOException {
        this.socket.write("Exit");
        this.socket.close();
    }
}