import org.json.JSONObject;
import pure.client.Client;
import pure.constants.Constants;
import pure.util.JSON;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
    public static void main(String[] args) {
        Client client = new Client(null, Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
                null, null, Constants.SSL.PROTOCOL, Constants.Socket.TIMEOUT) {
            @SafeVarargs
            @Override
            public final <T> String listen(T... input) {
                this.getSocket().write(input);
                return getSocket().read();
            }
        };
        pure.ui.GUI UI = new pure.ui.GUI(client);
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
                JSONObject logout = JSON.tryParse(client.listen(Constants.User.Logout));
                super.windowClosing(e);
                client.close();
            }
        });
    }
}
