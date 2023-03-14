package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.Listenable;
import ATM.Utilities.LogHelper;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

public abstract class Client implements Listenable, AutoCloseable {
    private final ClientSocket socket;

    public Client(String host, int port, String keyStoreType, String keyStorePath, String keyStorePass,
                  String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol) {
        ClientSocket s = null;
        try {
            s = new ClientSocket(host, port, keyStoreType, keyStorePath, keyStorePass,
                    keyManagerAlgorithm, trustManagerAlgorithm, protocol); //start new instance of client socket
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Server is not ready, or wrong host IP / port number.", e);
        } catch (CertificateException e) {
            LogHelper.log(Level.SEVERE, "Could not load certificate.", e);
        } catch (NoSuchAlgorithmException e) {
            LogHelper.log(Level.SEVERE, "No Provider supports a KeyManagerFactorySpi implementation for the specified algorithm", e);
        } catch (KeyStoreException e) {
            LogHelper.log(Level.SEVERE, "Failed to laod keystore.", e);
        } catch (UnrecoverableKeyException e) {
            LogHelper.log(Level.SEVERE, "Password might be incorrect.", e);
        } catch (KeyManagementException e) {
            LogHelper.log(Level.SEVERE, "Key expired or failed authorization.", e);
        } finally {
            this.socket = s;
        }
    }

    public final ClientSocket getSocket() {
        return socket;
    }

    public final void close() {
        this.socket.write(Constants.Stream.EOS);
        this.socket.close();
    }
}
