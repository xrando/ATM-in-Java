package Backup;

import ATM.Constants.Constants.Socket;
import ATM.Constants.Constants.Stream;
import Backup.ATMServerSocket;
import Backup.ATMSocket;
import ATM.Utilities.LogHelper;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

//to be removed
public class Server {
    public void listen() throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //start new instance of serversocket. This need not be recreated as it uses multi-threading to handle different client sockets


        ATMServerSocket ss = new ATMServerSocket(Socket.PORT);
        //SSLServerSocket ss = (SSLServerSocket) Security.sslContext(SSL.KEYSTORE, SSL.KEYSTOREPASS).getServerSocketFactory().createServerSocket(Socket.PORT);

        while (true) { //server should run non-stop
            ATMSocket socket = ss.accept(); //accept new client sockets and
            new Thread(() -> { //start new thread to handle each client sockets
                try (socket) { //autocloseable
                    while (true) {
                        /*once server receives a client request, it MUST respond to the client to continue*/
                        String clientInput = socket.read(); //receive client request as String
                        System.out.println("Received: " + clientInput);
                        //Sample below:
                        if (clientInput.equals(Stream.EOS)) { //need an exit code to safely end the connection
                            socket.write(clientInput); //response to client
                            break;
                        } else
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
