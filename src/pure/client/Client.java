package pure.client;

import org.json.JSONObject;
import pure.constants.Constants;
import pure.util.Listenable;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * An abstract class containing {@link ClientSocket} with {@link Listenable} undefined.
 * */
public abstract class Client implements Listenable<JSONObject>, AutoCloseable {
    private final ClientSocket socket;

    /**
     * It creates the object of {@link ClientSocket} and handles the exceptions.
     * <br><br>
     * The action to be taken ({@link Listenable}) is defined here.
     *
     * @param host represents the IP address to connect.
     * @param port represents the port number to connect.
     * @param keyStoreType represents the keystore type. null for default type.
     * @param keyStorePath represents the storage location of the keystore.
     * @param keyStorePass represents the password of the keystore.
     * @param keyManagerAlgorithm set null to use default.
     * @param trustManagerAlgorithm set null to use default.
     * @param protocol set null to use default.
     * @param timeout represents the time in milliseconds the client will wait for the server's response before throwing timeout exception.
     * */
    public Client(String host, int port, String keyStoreType, String keyStorePath, String keyStorePass,
                  String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol, int timeout)
            throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        this.socket = new ClientSocket(host, port, keyStoreType, keyStorePath, keyStorePass,
                keyManagerAlgorithm, trustManagerAlgorithm, protocol, timeout);
    }

    /**
     * Returns the {@link ClientSocket} object.
     * */
    public final ClientSocket getSocket() {
        return socket;
    }

    /**
     * Sends the EOS constant to Server to signal the closure of the socket, then close the socket.
     * */
    public final void close() throws IOException {
        this.socket.writeJSON(Constants.Stream.EOS);
        this.socket.close();
    }
}
