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
import java.util.logging.Level;

//https://github.com/square/okhttp/blob/master/samples/static-server/src/main/java/okhttp3/sample/SampleServer.java#L118
public abstract class Security {
    public static SSLContext sslContext(String keyStorePath, String keyStorePass) {
        SSLContext sslContext = null;
        try{
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream stream = new FileInputStream(keyStorePath);
            char[] trustStorePassword = keyStorePass.toCharArray();
            keyStore.load(stream, trustStorePassword);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, trustStorePassword);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext = SSLContext.getInstance(SSL.PROTOCOL);
            sslContext.init(
                    keyManagerFactory.getKeyManagers(),
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom());
        } catch (IOException e){
            LogHelper.log(Level.SEVERE, "KeyStore file not found.", e);
        } catch (CertificateException e) {
            LogHelper.log(Level.SEVERE, "Could not load certificate.", e);
        } catch (NoSuchAlgorithmException e) {
            LogHelper.log(Level.SEVERE, "No Provider supports a KeyManagerFactorySpi implementation for the specified algorithm", e);
        } catch (KeyStoreException e){
            LogHelper.log(Level.SEVERE, "Failed.", e);
        } catch (UnrecoverableKeyException e) {
            LogHelper.log(Level.SEVERE, "Password might be incorrect.", e);
        } catch (KeyManagementException e) {
            LogHelper.log(Level.SEVERE, "Key expired or failed authorization.", e);
        }
        return sslContext;
    }
}
