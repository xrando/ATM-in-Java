import org.json.JSONObject;
import pure.client.Client;
import pure.client.ClientSocket;
import pure.constants.Constants;
import pure.util.Listenable;
import pure.util.LogHelper;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;

public class GUI extends Client {

    /**
     * It creates the object of {@link ClientSocket} and handles the exceptions.
     * <br><br>
     * The action to be taken ({@link Listenable}) is defined here.
     *
     * @param host represents the IP address to connect.
     * @param port represents the port number to connect.
     * @param keyStoreType represents the keystore type. null for default type.
     * @param keyStorePath represents the storage location of the keystore.
     * @param keyStorePass represents the password of the keystore.
     * @param keyManagerAlgorithm set null to use default.
     * @param trustManagerAlgorithm set null to use default.
     * @param protocol set null to use default.
     * @param timeout represents the time in milliseconds the client will wait for the server's response before throwing timeout exception.
     * @throws KeyManagementException    if this operation failed.
     * @throws KeyStoreException         if no Provider supports a KeyStoreSpi implementation for the specified type, or KeyManageFactory/TrustManageFactory failed to init.
     * @throws IOException               if there is an I/O or format problem with the keystore data, if a password is required but not given, or if the given password was incorrect.
     * @throws CertificateException      if any of the certificates in the keystore could not be loaded.
     * @throws NoSuchAlgorithmException  if no Provider supports a SSLContextSpi implementation for the specified protocol. Check Config if "Protocol=TLSV1.3" exists.
     * @throws UnrecoverableKeyException if the key cannot be recovered (e.g. the given password is wrong).
     * @see Client
     * @see ClientSocket
     */
    public GUI(String host, int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol, int timeout)
            throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        super(host, port, keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol, timeout);
    }

    @SafeVarargs
    @Override
    public final <T> JSONObject listen(T... input) {
        try {
            this.getSocket().writeJSON(input);
            return getSocket().readJSON();
        } catch (IOException e) {
            LogHelper.log(Level.WARNING, "There are no bytes buffered on the socket, or all buffered bytes have been consumed by read.", e);
        }
        return null;
    }

    public static void main(String[] args) {
        GUI ui = null;
        try {
            ui = new GUI(Constants.Socket.HOST, Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
                    null, null, Constants.SSL.PROTOCOL, Constants.Socket.TIMEOUT);
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Server is not ready, or wrong host IP or port number: " + Constants.Socket.HOST + ":" + Constants.Socket.PORT + ".", e);
        } catch (CertificateException e) {
            LogHelper.log(Level.SEVERE, "Could not load certificate.", e);
        } catch (NoSuchAlgorithmException e) {
            LogHelper.log(Level.SEVERE, "No Provider supports a KeyManagerFactorySpi implementation for the specified algorithm", e);
        } catch (KeyStoreException e) {
            LogHelper.log(Level.SEVERE, "Failed to load keystore.", e);
        } catch (UnrecoverableKeyException e) {
            LogHelper.log(Level.SEVERE, "Password might be incorrect.", e);
        } catch (KeyManagementException e) {
            LogHelper.log(Level.SEVERE, "Key expired or failed authorization.", e);
        }
        if (ui == null)
            System.exit(-1);

        pure.ui.GUI UI = new pure.ui.GUI(ui);
        JFrame frame = new JFrame("Pure ATM");
        frame.setContentPane(UI.getBase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        GUI finalUi = ui;
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //sent request to server to logout
                finalUi.listen(Constants.User.LOGOUT);
                super.windowClosing(e);
                try {
                    finalUi.close();
                } catch (IOException ex) {
                    LogHelper.log(Level.WARNING, "I/O error occurs when closing this socket.", ex);
                }
            }
        });
    }
}
