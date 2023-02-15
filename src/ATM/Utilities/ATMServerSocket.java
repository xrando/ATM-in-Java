package ATM.Utilities;

import ATM.Constants.Constants;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

//remove this when deploying client
public class ATMServerSocket implements AutoCloseable {
    SSLServerSocket sslServerSocket;

    public ATMServerSocket() throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        sslServerSocket = (SSLServerSocket) Security.sslContext(Constants.SSL.KEYSTORE, Constants.SSL.KEYSTOREPASS).getServerSocketFactory().createServerSocket(Constants.Socket.PORT);
    }

    public ATMSocket accept() throws IOException {
        SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
        return new ATMSocket(sslSocket);
    }

    public void close() throws Exception {
        this.sslServerSocket.close();
    }
}
