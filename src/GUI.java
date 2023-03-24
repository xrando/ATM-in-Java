import org.json.JSONObject;
import pure.client.Client;
import pure.client.ClientSocket;
import pure.constants.Constants;
import pure.util.Listenable;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
     */
    public GUI(String host, int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol, int timeout) {
        super(host, port, keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol, timeout);
    }

    @SafeVarargs
    @Override
    public final <T> JSONObject listen(T... input) {
        this.getSocket().write(input);
        return getSocket().read();
    }

    public static void main(String[] args) {
        GUI ui = new GUI(Constants.Socket.HOST, Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
                null, null, Constants.SSL.PROTOCOL, Constants.Socket.TIMEOUT);
        pure.ui.GUI UI = new pure.ui.GUI(ui);
        JFrame frame = new JFrame("Pure ATM");
        frame.setContentPane(UI.getBase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //sent request to server to logout
                ui.listen(Constants.User.LOGOUT);
                super.windowClosing(e);
                ui.close();
            }
        });
    }
}
