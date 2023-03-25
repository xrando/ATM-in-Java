package pure.client;

import org.json.JSONObject;
import pure.constants.Constants;
import pure.util.JSON;
import pure.util.LogHelper;
import pure.util.SSLContext;

import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
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
public class ClientSocket implements AutoCloseable {
    private final SSLSocket sslSocket;

    /**
     * @param host represents the IP address to connect.
     * @param port represents the port number to connect.
     * @param keyStoreType represents the keystore type. null for default type.
     * @param keyStorePath represents the storage location of the keystore.
     * @param keyStorePass represents the password of the keystore.
     * @param keyManagerAlgorithm set null to use default.
     * @param trustManagerAlgorithm set null to use default.
     * @param protocol set null to use default.
     * @param timeout represents the time in milliseconds the client will wait for the server's response before throwing timeout exception.*/
    protected ClientSocket(String host, int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol, int timeout) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        javax.net.ssl.SSLContext sslContext = new SSLContext(keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol).getSSLContext();
        sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(host, port);
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
    public String readString() throws IOException {
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
        } catch (SocketException e){
            throw new SocketException(e);
        }catch (IOException e) {
            throw new IOException(e);
        }
        return a.toString();
    }

    /**
     * return the message as JSONObject from the input stream.
     * @see #readString()
     * */
    public JSONObject readJSON() throws IOException {
        return JSON.tryParse(readString());
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return new ObjectInputStream(this.sslSocket.getInputStream()).readObject();
    }

    public void writeObject(Object o) throws IOException {
        new ObjectOutputStream(this.sslSocket.getOutputStream()).writeObject(o);
    }

    /**
     * push the unformatted String to output stream and add EOF.
     * */
    public void writeString(String s) throws IOException {
        try {
            this.sslSocket.getOutputStream().write(s.getBytes());
            this.sslSocket.getOutputStream().write(Constants.Stream.EOF.getBytes());
            this.sslSocket.getOutputStream().flush();
        } catch (SSLHandshakeException e) {
            LogHelper.log(Level.SEVERE, "Secure connection cannot be established with host " + this.sslSocket.getRemoteSocketAddress().toString(), e);
            System.exit(-1);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * receive generic array as input and format to Json, then parse to String and push to output stream.
     * @see #writeString(String)
     * */
    @SafeVarargs
    public final <T> void writeJSON(T... str) throws IOException {
        if (str.length < 1)
            return;

        JSON j = new JSON(str[0]);
        for (int i = 1; i < str.length; i += 2) {
            j.add(str[i].toString(), i + 1 < str.length ? str[i + 1] : "");
        }
        writeString(j.toString());
    }

    public void close() throws IOException {
        try {
            this.sslSocket.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * returns the IP address of the socket.
     * */
    public String getIP() {
        return sslSocket.getRemoteSocketAddress().toString();
    }
}