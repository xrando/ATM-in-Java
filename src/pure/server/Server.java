package pure.server;

import pure.util.Listenable;
import pure.util.LogHelper;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

/**
 * An abstract class containing {@link ServerSocket} with {@link Listenable} undefined.
 * */
public abstract class Server implements Listenable<String> {
    private final pure.server.ServerSocket serverSocket;

    /**
     * It creates the object of {@link ServerSocket} and handles its exceptions.
     * <br><br>
     * The action to be taken ({@link Listenable}) is defined here.
     * */
    public Server(int port, String keyStoreType, String keyStorePath, String keyStorePass,
                  String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol) {
        pure.server.ServerSocket ss = null;
        try {
            ss = new ServerSocket(port, keyStoreType, keyStorePath, keyStorePass,
                    keyManagerAlgorithm, trustManagerAlgorithm, protocol);
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
        } finally {
            this.serverSocket = ss;
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
