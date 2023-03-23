package pure.server;

import pure.client.ClientSocket;
import pure.util.Listenable;
import pure.util.LogHelper;
import pure.util.SSLContext;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

/**
 * A wrapper class for {@link SSLServerSocket}.
 * <br><br>
 * Initialization of this class defines the parent class {@link SSLContext} and thus creates the SSL socket object.
 * @see ServerSocket
 * @see SSLServerSocket
 * @see SSLContext
 * @see ClientSocket
 * */

public abstract class ServerSocket implements Listenable<String> {
    private final SSLServerSocket sslServerSocket;

    public ServerSocket(int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        javax.net.ssl.SSLContext sslContext = new SSLContext(keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol).getSSLContext();
        sslServerSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(port);
        sslServerSocket.setSoTimeout(0);
        sslServerSocket.setNeedClientAuth(true);
    }

    public boolean getSslServerSocketStatus() {
        return sslServerSocket != null;
    }

    public SSLServerSocket getSslServerSocket() {
        return sslServerSocket;
    }

    /**
    * Wrap the {@link SSLSocket} accepted by {@link SSLServerSocket#accept()} to {@link ClientSocket}.
     * */
    public final ClientSocket accept() {
        ClientSocket socket = null;
        try {
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
            socket = new ClientSocket(sslSocket);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "I/O error occurred while waiting for connection.", e);
        }
        return socket;
    }

    public final void close() {
        try {
            this.sslServerSocket.close();
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Unable to close the server socket or the socket already closed.", e);
        }
    }
}
