package ATM.Server;

import ATM.Bank.User;
import ATM.Constants.Constants;
import ATM.Utilities.ATMServerSocket;
import ATM.Utilities.ATMSocket;
import ATM.Utilities.LogHelper;
import org.json.JSONArray;
import org.json.JSONObject;

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
                User user = null;
                try (socket) { //autocloseable
                    while (true) {
                        /*once server receives a client request, it MUST respond to the client to continue*/
                        String clientInput = socket.read(); //receive client request as String

                        //for debugging:
                        System.out.println("Received: " + clientInput);

                        //exit code to safely end the connection
                        if (clientInput.equals(Constants.Stream.EOS))
                            break;

                        //request[0] is the request type, the rest are the parameters if applicable (can have no parameter)
                        //String[] request = clientInput.split(Constants.RequestBuilder.Separator);
                        JSONObject request = new JSONObject(clientInput);

                        switch (request.getString(Constants.JSON.Type)){
                            case Constants.User.Login -> {
                                user = new User(request.getString(Constants.User.Username), request.getString(Constants.User.Password));
                                socket.write(user.Login()); //now socket.write() can receive boolean. String "true" or "false" will be sent
                            }
                            case Constants.User.Logout -> socket.write(user.logout());
                            //TODO: complete the cases
                        }
                        //Sample below:
/*                        if (clientInput.equals(Constants.Stream.EOS)) { //need an exit code to safely end the connection
                            socket.write(clientInput); //response to client
                            break;
                        } else
                            socket.write("You sent: " + clientInput);*/
                        //End of sample
                    }
                } catch (IOException e) {
                    LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
                } catch (NullPointerException e) {
                    LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
                } catch (Exception e) {
                    LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }).start();
        }
    }
}
