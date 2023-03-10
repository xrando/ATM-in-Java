package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.LogHelper;

import java.io.IOException;
import java.util.logging.Level;

public class Client {
    private ATMClientSocket socket;

    public Client() {
        try {
            socket = new ATMClientSocket(); //start new instance of client socket
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Server is not ready, or wrong host IP / port number.", e);
        }
    }

    public String listen(String input) {
        this.socket.write(input);
        return socket.read();
    }

    public void close() {
        this.socket.write(Constants.Stream.EOS);
        this.socket.close();
    }
}
