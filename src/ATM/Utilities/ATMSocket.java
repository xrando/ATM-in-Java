package ATM.Utilities;

import ATM.Constants.Constants;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class ATMSocket implements AutoCloseable {
    private final SSLSocket sslSocket;

    public ATMSocket() throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        sslSocket = (SSLSocket) Security.sslContext(Constants.SSL.TRUSTSTORE, Constants.SSL.TRUSTSTOREPASS).getSocketFactory().createSocket(Constants.Socket.HOST, Constants.Socket.PORT);
    }

    public ATMSocket(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
    }

    //read the input stream until hits the EOF constant
    public String read() throws IOException {
        byte[] buf = new byte[1024];
        int b;
        StringBuilder a = new StringBuilder();
        while ((b = this.sslSocket.getInputStream().read(buf)) != -1) {
            String temp = new String(buf, 0, b);
            if (temp.endsWith(Constants.Stream.EOF)) {
                a.append(temp, 0, temp.length() - Constants.Stream.EOF.length());
                break;
            } else
                a.append(temp);
        }
        return a.toString();
    }

    //push the String to outputstream and add EOF
    public void write(String s) throws IOException {
        this.sslSocket.getOutputStream().write(s.getBytes());
        this.sslSocket.getOutputStream().write(Constants.Stream.EOF.getBytes());
        this.sslSocket.getOutputStream().flush();
    }

    public void close() throws Exception {
        this.sslSocket.close();
    }
}