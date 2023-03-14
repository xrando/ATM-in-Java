package ATM.Server;

import ATM.Client.ClientSocket;
import ATM.Utilities.SSLContext;
import ATM.Utilities.LogHelper;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

public class ServerSocket extends SSLContext implements AutoCloseable {
    private final SSLServerSocket sslServerSocket;

    protected ServerSocket(int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        super(keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol);
        sslServerSocket = (SSLServerSocket) SSLCONTEXT.getServerSocketFactory().createServerSocket(port);
        sslServerSocket.setSoTimeout(0);
        sslServerSocket.setNeedClientAuth(true);
    }

    public boolean getSslServerSocketStatus() {
        return sslServerSocket != null;
    }

    public ClientSocket accept() {
        ClientSocket atmSocket = null;
        try {
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
            atmSocket = new ClientSocket(sslSocket);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "I/O error occurred while waiting for connection.", e);
        }
        return atmSocket;
    }

    public void close() {
        try {
            this.sslServerSocket.close();
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Unable to close the server socket or the socket already closed.", e);
        }
    }
}
