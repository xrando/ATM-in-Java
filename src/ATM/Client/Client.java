package ATM.Client;
import ATM.Utilities.ATMSocket;
import ATM.Utilities.ConfigurationManager;

import java.io.IOException;

public class Client {
    public void listen(String input) throws IOException {
        //get configurations from external config file
        final String HOST = ConfigurationManager.GetConfig("Server");
        final int PORT = Integer.parseInt(ConfigurationManager.GetConfig("port"));

        ATMSocket socket = new ATMSocket(HOST, PORT); //start new instance of client socket

        while (true){
            /*The client sends a request to the server, and will wait for the server response.
            * TODO: add timeout*/
            socket.write(input); //send input to server
            String msg = socket.read(); //get server response
            System.out.println(msg); //for debugging
            if(msg.equals("Exit")) //exit needed to break loop
                break;
        }
        socket.close(); //closing socket will close the streams within automatically
    }
}