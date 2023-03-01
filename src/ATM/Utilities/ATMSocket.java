package ATM.Utilities;

import ATM.Constants.Constants;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.logging.Level;

public class ATMSocket {
    private SSLSocket sslSocket;

    public ATMSocket() {
        try {
            sslSocket = (SSLSocket) Security.sslContext(Constants.SSL.TRUSTSTORE, Constants.SSL.TRUSTSTOREPASS).getSocketFactory().createSocket(Constants.Socket.HOST, Constants.Socket.PORT);
            sslSocket.setSoTimeout(Constants.Socket.TIMEOUT);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Server is not ready, or wrong host IP / port number.", e);
        } catch (IllegalArgumentException e) {
            LogHelper.log(Level.SEVERE, "Timeout might be set to negative by mistake, or the port number is out of range (0 and 65535).", e);
        }
    }

    public ATMSocket(SSLSocket sslSocket) {
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
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "If there are no bytes buffered on the socket, or all buffered bytes have been consumed by read.", e);
        }
        return a.toString();
    }

    //push the String to outputstream and add EOF
    public void write(String s) {
        try {
            this.sslSocket.getOutputStream().write(s.getBytes());
            this.sslSocket.getOutputStream().write(Constants.Stream.EOF.getBytes());
            this.sslSocket.getOutputStream().flush();
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