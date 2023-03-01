package ATM.Utilities;

import ATM.Constants.Constants;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.logging.Level;

//remove this when deploying client
public class ATMServerSocket {
    private final SSLServerSocket sslServerSocket = (SSLServerSocket) Security.sslContext(Constants.SSL.KEYSTORE, Constants.SSL.KEYSTOREPASS).getServerSocketFactory().createServerSocket(Constants.Socket.PORT);

    public ATMServerSocket() throws IOException {
        sslServerSocket.setSoTimeout(0);
    }

    public boolean getSslServerSocketStatus() {
        return sslServerSocket != null;
    }

    public ATMSocket accept() {
        ATMSocket atmSocket = null;
        try {
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
            atmSocket = new ATMSocket(sslSocket);
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
