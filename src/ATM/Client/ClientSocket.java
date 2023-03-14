package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.SSLContext;
import ATM.Utilities.JSON;
import ATM.Utilities.LogHelper;

import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

public class ClientSocket extends SSLContext implements AutoCloseable {
    private final SSLSocket sslSocket;

    protected ClientSocket(String host, int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        super(keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol);
        sslSocket = (SSLSocket) SSLCONTEXT.getSocketFactory().createSocket(host, port);
        sslSocket.setSoTimeout(Constants.Socket.TIMEOUT);
    }

    public ClientSocket(SSLSocket sslSocket) {
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
            LogHelper.log(Level.WARNING, "There are no bytes buffered on the socket, or all buffered bytes have been consumed by read.", e);
        }
        return a.toString();
    }

    //push the String to outputstream and add EOF
    private void write(String s) {
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

    @SafeVarargs
    public final <T> void write(T... str){
        if(str.length < 1)
            return;

        JSON j = new JSON(str[0]);
        for(int i = 1; i < str.length; i+=2) {
            j.add(str[i].toString(), i + 1 < str.length ? str[i + 1] : "");
        }
        write(j.toString());
    }

    public void close() {
        try {
            this.sslSocket.close();
        } catch (IOException e) {
            LogHelper.log(Level.WARNING, "I/O error occurs when closing this socket.", e);
        }
    }

    public String getIP() {
        return sslSocket.getRemoteSocketAddress().toString();
    }
}