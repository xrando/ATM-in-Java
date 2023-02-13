package ATM.Server;

import ATM.Utilities.ATMServerSocket;
import ATM.Utilities.ATMSocket;
import ATM.Utilities.ConfigurationManager;
import ATM.Utilities.LogHelper;

import java.io.*;
import java.util.logging.Level;

public class Server {
    public void listen() throws IOException {
        //start new instance of serversocket. This need not be recreated as it uses multi-threading to handle different client sockets
        final int PORT = Integer.parseInt(ConfigurationManager.GetConfig("Port"));
        ATMServerSocket ss = new ATMServerSocket(PORT);

        while (true){ //server should run non-stop
            ATMSocket socket = ss.accept(); //accept new client sockets and
            new Thread(() -> { //start new thread to handle each client sockets
                try(socket) { //autocloseable
                    while (true) {
                        /*once server receives a client request, it MUST respond to the client to continue*/
                        String clientInput = socket.read(); //receive client request as String
                        System.out.println("Received: " + clientInput);
                        //Sample below:
                        if(clientInput.equals("Exit")){ //need an exit code to safely end the connection
                            socket.write(clientInput); //response to client
                            break;
                        }
                        else
                            socket.write("You sent: " + clientInput);
                        //End of sample
                        //TODO: (replace the sample codes) switch statement to handle different requests
                    }
                } catch (IOException e) {
                    LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }).start();
        }
    }
}
