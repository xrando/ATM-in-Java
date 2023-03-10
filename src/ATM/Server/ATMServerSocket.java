package ATM.Server;

import ATM.Client.ATMClientSocket;
import ATM.Constants.Constants;
import ATM.Utilities.ATMSSLContext;
import ATM.Utilities.LogHelper;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.logging.Level;

public class ATMServerSocket extends ATMSSLContext implements AutoCloseable {
/*    private final SSLServerSocket sslServerSocket = (SSLServerSocket) Security.sslContext(Constants.SSL.KEYSTORE, Constants.SSL.KEYSTOREPASS).getServerSocketFactory().createServerSocket(Constants.Socket.PORT);

    public ATMServerSocket() throws IOException {
        sslServerSocket.setSoTimeout(0);
        sslServerSocket.setNeedClientAuth(true);
    }*/

    private final SSLServerSocket sslServerSocket;

    protected ATMServerSocket() throws IOException {
        super(Constants.SSL.SERVER_KEYSTORE, Constants.SSL.SERVER_KEYSTORE_PASS);
        sslServerSocket = (SSLServerSocket) SSLCONTEXT.getServerSocketFactory().createServerSocket(Constants.Socket.PORT);
        sslServerSocket.setSoTimeout(0);
        sslServerSocket.setNeedClientAuth(true);
    }

    protected boolean getSslServerSocketStatus() {
        return sslServerSocket != null;
    }

    protected ATMClientSocket accept() {
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
