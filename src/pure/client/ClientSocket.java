package pure.client;

import org.json.JSONObject;
import pure.constants.Constants;
import pure.util.JSON;
import pure.util.LogHelper;
import pure.util.SSLContext;

import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

/**
 * A wrapper class for the {@link SSLSocket}.
 * <br><br>
 * Initialization of this class defines the parent class {@link SSLContext} and thus creates the SSL socket object.
 * <br><br>
 * It has basic read/write features implemented.
 * <br>
 * Getter provided for any further implementations if needed.
 * @see java.net.Socket
 * @see SSLSocket
 * @see SSLContext
 * */
public class ClientSocket extends SSLContext implements AutoCloseable {
    private final SSLSocket sslSocket;

    protected ClientSocket(String host, int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol, int timeout) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        super(keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol);
        sslSocket = (SSLSocket) getSSLContext().getSocketFactory().createSocket(host, port);
        sslSocket.setSoTimeout(timeout);
    }

    public SSLSocket getSslSocket() {
        return sslSocket;
    }

    public ClientSocket(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
    }

    /**
     * read the input stream until hits the EOF constant, return as String.
     * */
    public String readRaw() {
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

    public JSONObject read() {
        return JSON.tryParse(readRaw());
    }

    /**
     * push the unformatted String to output stream and add EOF.
     * */
    public void writeRaw(String s) {
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

    /**
     * receive generic array as input and format to Json, then parse to String and push to output stream.
     * */
    @SafeVarargs
    public final <T> void write(T... str) {
        if (str.length < 1)
            return;

        JSON j = new JSON(str[0]);
        for (int i = 1; i < str.length; i += 2) {
            j.add(str[i].toString(), i + 1 < str.length ? str[i + 1] : "");
        }
        writeRaw(j.toString());
    }

    public void close() {
        try {
            this.sslSocket.close();
        } catch (IOException e) {
            LogHelper.log(Level.WARNING, "I/O error occurs when closing this socket.", e);
        }
    }

    /**
     * returns the IP address of the socket.
     * */
    public String getIP() {
        return sslSocket.getRemoteSocketAddress().toString();
    }
}