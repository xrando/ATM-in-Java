package pure.test;

import org.json.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;
import pure.client.ClientSocket;
import pure.constants.Constants;
import pure.server.ServerSocket;
import pure.util.LogHelper;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

public class ServerTest {
    static ServerSocket s;

    static {
        try {
            s = new ServerSocket(Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
                    null, null, Constants.SSL.PROTOCOL) {
                @Override
                public <T> String listen(T... input) {
                    while (this.getSslServerSocketStatus()) {
                        ClientSocket socket = this.accept();
                        new Thread(() -> {
                            JSONObject clientInput = socket.read();
                            System.out.println(clientInput);
                            socket.write(clientInput.getString(Constants.JSON.TYPE));
                        }).start();
                    }
                    return "Server Down";
                }
            };
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Check port number.", e);
        } catch (NullPointerException e) {
            LogHelper.log(Level.SEVERE, "Failed to generate ssl server socket.", e);
        } catch (CertificateException e) {
            LogHelper.log(Level.SEVERE, "Could not load certificate.", e);
        } catch (NoSuchAlgorithmException e) {
            LogHelper.log(Level.SEVERE, "No Provider supports a KeyManagerFactorySpi implementation for the specified algorithm", e);
        } catch (KeyStoreException e) {
            LogHelper.log(Level.SEVERE, "Failed to load keystore.", e);
        } catch (UnrecoverableKeyException e) {
            LogHelper.log(Level.SEVERE, "Password might be incorrect.", e);
        } catch (KeyManagementException e) {
            LogHelper.log(Level.SEVERE, "Key expired or failed authorization.", e);
        }
    }

    @Test
    public void startServer(){
        s.listen();
    }
}
