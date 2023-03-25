package pure.util;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * This abstract class provides the SSL context with default settings for the ATM sever and clients,
 * to generate the SSL server socket and SSL sockets.
 *
 * @see javax.net.ssl.SSLContext
 */
public class SSLContext {
    private final javax.net.ssl.SSLContext SSLCONTEXT;

    /**
     * Creates the SSL context for client/server.
     * KeyManagementFactory and TrustManagementFactory using default algorithm.
     * SSL context using TLSV1.3 as specified in config.properties.
     * <P></P>
     *
     * @param keyStoreType          represents the keystore type. null for default type.
     * @param keyStorePath          represents the storage location of the keystore.
     * @param keyStorePass          represents the password of the keystore.
     * @param keyManagerAlgorithm   set null to use default.
     * @param trustManagerAlgorithm set null to use default.
     * @param protocol              set null to use default.
     * @throws KeyManagementException    if this operation failed.
     * @throws KeyStoreException         if no Provider supports a KeyStoreSpi implementation for the specified type, or KeyManageFactory/TrustManageFactory failed to init.
     * @throws IOException               if there is an I/O or format problem with the keystore data, if a password is required but not given, or if the given password was incorrect.
     * @throws CertificateException      if any of the certificates in the keystore could not be loaded.
     * @throws NoSuchAlgorithmException  if no Provider supports a SSLContextSpi implementation for the specified protocol. Check Config if "Protocol=TLSV1.3" exists.
     * @throws UnrecoverableKeyException if the key cannot be recovered (e.g. the given password is wrong).
     */
    public SSLContext(String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol)
            throws KeyManagementException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyStore keyStore;
        InputStream stream;
        char[] trustStorePassword;
        KeyManagerFactory keyManagerFactory;
        TrustManagerFactory trustManagerFactory;

        keyStore = KeyStore.getInstance(keyStoreType == null ? KeyStore.getDefaultType() : keyStoreType);

        stream = new FileInputStream(keyStorePath);
        trustStorePassword = keyStorePass.toCharArray();
        keyStore.load(stream, trustStorePassword);

        keyManagerFactory = KeyManagerFactory.getInstance(keyManagerAlgorithm == null ? KeyManagerFactory.getDefaultAlgorithm() : keyManagerAlgorithm);
        keyManagerFactory.init(keyStore, trustStorePassword);

        trustManagerFactory = TrustManagerFactory.getInstance(trustManagerAlgorithm == null ? TrustManagerFactory.getDefaultAlgorithm() : trustManagerAlgorithm);
        trustManagerFactory.init(keyStore);

        SSLCONTEXT = javax.net.ssl.SSLContext.getInstance(protocol == null ? javax.net.ssl.SSLContext.getDefault().getProtocol() : protocol);
        SSLCONTEXT.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
    }

    public javax.net.ssl.SSLContext getSSLContext() {
        return SSLCONTEXT;
    }
}
