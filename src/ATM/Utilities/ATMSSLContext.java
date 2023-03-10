package ATM.Utilities;

import ATM.Constants.Constants;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.logging.Level;

public abstract class ATMSSLContext {
    protected static SSLContext SSLCONTEXT = null;

    protected ATMSSLContext(String keyStorePath, String keyStorePass) {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream stream = new FileInputStream(keyStorePath);
            char[] trustStorePassword = keyStorePass.toCharArray();
            keyStore.load(stream, trustStorePassword);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, trustStorePassword);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLCONTEXT = javax.net.ssl.SSLContext.getInstance(Constants.SSL.PROTOCOL);
            SSLCONTEXT.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "KeyStore file not found.", e);
        } catch (CertificateException e) {
            LogHelper.log(Level.SEVERE, "Could not load certificate.", e);
        } catch (NoSuchAlgorithmException e) {
            LogHelper.log(Level.SEVERE, "No Provider supports a KeyManagerFactorySpi implementation for the specified algorithm", e);
        } catch (KeyStoreException e) {
            LogHelper.log(Level.SEVERE, "Failed.", e);
        } catch (UnrecoverableKeyException e) {
            LogHelper.log(Level.SEVERE, "Password might be incorrect.", e);
        } catch (KeyManagementException e) {
            LogHelper.log(Level.SEVERE, "Key expired or failed authorization.", e);
        }
    }

    protected ATMSSLContext() {
        //empty constructor for child class
    }
}
