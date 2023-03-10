package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.ATMSockets;
import ATM.Utilities.LogHelper;
import ATM.Utilities.Security;

import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.logging.Level;

public class ATMClientSocket implements ATMSockets {
    private SSLSocket sslSocket;

    public ATMClientSocket() {
        try {
            sslSocket = (SSLSocket) Security.sslContext(Constants.SSL.TRUSTSTORE, Constants.SSL.TRUSTSTOREPASS).getSocketFactory().createSocket(Constants.Socket.HOST, Constants.Socket.PORT);
            sslSocket.setSoTimeout(Constants.Socket.TIMEOUT);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Server is not ready, or wrong host IP / port number.", e);
        } catch (IllegalArgumentException e) {
            LogHelper.log(Level.SEVERE, "Timeout might be set to negative by mistake, or the port number is out of range (0 and 65535).", e);
        } catch (NullPointerException e) {
            LogHelper.log(Level.SEVERE, "Failed to generate ssl socket.", e);
            throw e;
        }
    }

    public ATMClientSocket(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
    }

    //read the input stream until hits the EOF constant
    public String read() {
        byte[] buf = new byte[1024];
        int b;
        StringBuilder a = new StringBuilder();
        try {
            while ((b = this.sslSocket.getInputStream().read(buf)) != -1) {
                String temp = new String(buf, 0, b);
                if (temp.endsWith(Constants.Stream.EOF)) {
                    a.append(temp, 0, temp.length() - Constants.Stream.EOF.length());
                    break;
                } else
                    a.append(temp);
            }
        } catch (SSLHandshakeException e) {
            LogHelper.log(Level.SEVERE, "Secure connection cannot be established with host " + this.sslSocket.getRemoteSocketAddress().toString(), e);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "There are no bytes buffered on the socket, or all buffered bytes have been consumed by read.", e);
        }
        return a.toString();
    }

    //push the String to outputstream and add EOF
    public void write(String s) {
        try {
            this.sslSocket.getOutputStream().write(s.getBytes());
            this.sslSocket.getOutputStream().write(Constants.Stream.EOF.getBytes());
            this.sslSocket.getOutputStream().flush();
        } catch (SSLHandshakeException e) {
            LogHelper.log(Level.SEVERE, "Secure connection cannot be established with host " + this.sslSocket.getRemoteSocketAddress().toString(), e);
            System.exit(-1);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "I/O error occurs when creating the output stream or the socket is not connected.", e);
        }
    }

    public void close() {
        try {
            this.sslSocket.close();
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "I/O error occurs when closing this socket.", e);
        }
    }
}