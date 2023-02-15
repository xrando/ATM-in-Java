package ATM.Server;

import ATM.Constants.Constants;
import ATM.Utilities.ATMServerSocket;
import ATM.Utilities.ATMSocket;
import ATM.Utilities.LogHelper;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

public class Server {
    public void listen() throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        ATMServerSocket ss = new ATMServerSocket();
        while (true) {
            ATMSocket socket = ss.accept();
            new Thread(() -> {
                try (socket) { //autocloseable
                    while (true) {
                        /*once server receives a client request, it MUST respond to the client to continue*/
                        String clientInput = socket.read(); //receive client request as String
                        System.out.println("Received: " + clientInput);
                        //Sample below:
                        if (clientInput.equals(Constants.Stream.EOS)) { //need an exit code to safely end the connection
                            socket.write(clientInput); //response to client
                            break;
                        } else
                            socket.write("You sent: " + clientInput);
                        //End of sample
                        //TODO: (replace the sample codes) switch statement to handle different requests
                    }
                } catch (IOException e) {
                    LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
