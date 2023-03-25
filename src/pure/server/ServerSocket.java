package pure.server;

import pure.client.ClientSocket;
import pure.util.Listenable;
import pure.util.SSLContext;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

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

    /**
     * Creates the ssl server socket.
     * <br>
     * @param port represents the port number to connect.
     * @param keyStoreType represents the keystore type. null for default type.
     * @param keyStorePath represents the storage location of the keystore.
     * @param keyStorePass represents the password of the keystore.
     * @param keyManagerAlgorithm set null to use default.
     * @param trustManagerAlgorithm set null to use default.
     * @param protocol set null to use default.
     * */
    public ServerSocket(int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        javax.net.ssl.SSLContext sslContext = new SSLContext(keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol).getSSLContext();
        sslServerSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(port, 0 , InetAddress.getLocalHost());
        System.out.println("Server on: " + InetAddress.getLocalHost()+"//"+port);
        sslServerSocket.setSoTimeout(0);
        sslServerSocket.setNeedClientAuth(true);
    }

    /**
     * Indicates if the server socket is created.
     * */
    public boolean getSslServerSocketStatus() {
        return sslServerSocket != null;
    }

    public SSLServerSocket getSslServerSocket() {
        return sslServerSocket;
    }

    /**
     * Wrap the {@link SSLSocket} accepted by {@link SSLServerSocket#accept()} to {@link ClientSocket}.
     * */
    public final ClientSocket accept() throws IOException {
        ClientSocket socket;
        try {
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
            socket = new ClientSocket(sslSocket);
        } catch (IOException e) {
            throw new IOException(e);
        }
        return socket;
    }
}
