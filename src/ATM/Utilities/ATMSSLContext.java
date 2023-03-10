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

/**
 * This abstract class provides the SSL context with default settings for the ATM sever and clients,
 * to generate the SSL server socket and SSL sockets.
 * @see SSLContext
 * */
public abstract class ATMSSLContext {
    protected static SSLContext SSLCONTEXT;
    /**
     * Creates the SSL context for client/server.
     * KeyManagementFactory and TrustManagementFactory using default algorithm.
     * SSL context using TLSV1.3 as specified in config.properties.
     * <P></P>
     * @param keyStorePath the relative path stored in config
     * @param keyStorePass the password in plaintext stored in config
     * @throws KeyManagementException if this operation failed
     * @throws KeyStoreException if no Provider supports a KeyStoreSpi implementation for the specified type, or KeyManageFactory/TrustManageFactory failed to init
     * @throws IOException if there is an I/O or format problem with the keystore data, if a password is required but not given, or if the given password was incorrect
     * @throws CertificateException if any of the certificates in the keystore could not be loaded
     * @throws NoSuchAlgorithmException if no Provider supports a SSLContextSpi implementation for the specified protocol. Check Config if "Protocol=TLSV1.3" exists
     * @throws UnrecoverableKeyException if the key cannot be recovered (e.g. the given password is wrong).
     * */
     protected ATMSSLContext(String keyStorePath, String keyStorePass) throws KeyManagementException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
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
    }

    /**
     * Empty constructor for child classes
     * */
    protected ATMSSLContext() {
    }
}
