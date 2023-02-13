package ATM.Server;

import java.io.IOException;

public class ATMServer {
    public static void main(String[] args) throws IOException {
        new Server().listen(); //probably the only line needed
    }
}
