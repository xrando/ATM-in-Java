package ATM.Server;

import ATM.Client.ATMClientSocket;
import ATM.Constants.Constants;
import ATM.Utilities.LogHelper;
import ATM.Utilities.Security;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.logging.Level;

//remove this when deploying client
public class ATMServerSocket implements AutoCloseable {
    private final SSLServerSocket sslServerSocket = (SSLServerSocket) Security.sslContext(Constants.SSL.KEYSTORE, Constants.SSL.KEYSTOREPASS).getServerSocketFactory().createServerSocket(Constants.Socket.PORT);

    public ATMServerSocket() throws IOException {
        sslServerSocket.setSoTimeout(0);
        sslServerSocket.setNeedClientAuth(true);
    }

    public boolean getSslServerSocketStatus() {
        return sslServerSocket != null;
    }

    public ATMClientSocket accept() {
        ATMClientSocket atmSocket = null;
        try {
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
            atmSocket = new ATMClientSocket(sslSocket);
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
