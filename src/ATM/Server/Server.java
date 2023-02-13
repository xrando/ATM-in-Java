package ATM.Server;

import ATM.Utilities.ATMServerSocket;
import ATM.Utilities.ATMSocket;
import ATM.Utilities.LogHelper;

import java.io.*;
import java.util.logging.Level;

public class Server {
    public void listen() throws IOException {
        //start new instance of serversocket. This need not be recreated as it uses multi-threading to handle different client sockets
        ATMServerSocket ss = new ATMServerSocket(10000);

        while (true){ //server should run non-stop
            ATMSocket socket = ss.accept(); //accept new client sockets and
            new Thread(() -> { //start new thread to handle each client sockets
                try(socket) { //autocloseable
                    while (true) {
                        /*once server receives a client request, it MUST respond to the client to continue*/
                        String clientInput = socket.read(); //receive client request as String
                        //Sample below:
                        if(clientInput.equals("Exit")){
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
