package ATM.Client;

import ATM.Constants.Constants;

public class Client {
    private final ATMClientSocket socket;

    public Client() {
        socket = new ATMClientSocket(); //start new instance of client socket
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
