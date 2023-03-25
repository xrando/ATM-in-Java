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
 * @see java.net.ServerSocket
 * @see SSLServerSocket
 * @see SSLContext
 * @see ClientSocket
 * */

public abstract class ServerSocket implements Listenable<String> {
    private final SSLServerSocket sslServerSocket;

    /**
     * Creates the ssl server socket. Enforce client authentication.
     * <br>
     * @param port represents the port number to connect.
     * @param keyStoreType represents the keystore type. null for default type.
     * @param keyStorePath represents the storage location of the keystore.
     * @param keyStorePass represents the password of the keystore.
     * @param keyManagerAlgorithm set null to use default.
     * @param trustManagerAlgorithm set null to use default.
     * @param protocol set null to use default.
     * @throws KeyManagementException    if this operation failed.
     * @throws KeyStoreException         if no Provider supports a KeyStoreSpi implementation for the specified type, or KeyManageFactory/TrustManageFactory failed to init.
     * @throws IOException               if there is an I/O or format problem with the keystore data, if a password is required but not given, or if the given password was incorrect.
     * @throws CertificateException      if any of the certificates in the keystore could not be loaded.
     * @throws NoSuchAlgorithmException  if no Provider supports a SSLContextSpi implementation for the specified protocol. Check Config if "Protocol=TLSV1.3" exists.
     * @throws UnrecoverableKeyException if the key cannot be recovered (e.g. the given password is wrong).
     * */
    public ServerSocket(int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol)
            throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        javax.net.ssl.SSLContext sslContext = new SSLContext(keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol).getSSLContext();
        sslServerSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(port, 0 , InetAddress.getLocalHost());
        System.out.println("Server on: " + InetAddress.getLocalHost()+":"+port);
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
