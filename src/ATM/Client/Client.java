package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.ATMSocket;

public class Client {
    private final ATMSocket socket;

    public Client() {
        socket = new ATMSocket(); //start new instance of client socket
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
