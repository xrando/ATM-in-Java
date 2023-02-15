package ATM.Utilities;

import ATM.Constants.Constants.SSL;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

//https://github.com/square/okhttp/blob/master/samples/static-server/src/main/java/okhttp3/sample/SampleServer.java#L118
public abstract class Security {
    public static SSLContext sslContext(String keyStorePath, String keyStorePass) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream stream = new FileInputStream(keyStorePath);
        char[] trustStorePassword = keyStorePass.toCharArray();
        keyStore.load(stream, trustStorePassword);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, trustStorePassword);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance(SSL.PROTOCOL);
        sslContext.init(
                keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(),
                new SecureRandom());
        return sslContext;
    }

}
