import ATM.Client.Client;
import ATM.Constants.Constants;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
    public static void main(String[] args) {
        Client client = new Client(null, Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
                null, null, Constants.SSL.PROTOCOL) {
            @Override
            public <T> String listen(T... input) {
                this.getSocket().write(input);
                return getSocket().read();
            }
        };
        ATM.Client.GUI UI = new ATM.Client.GUI(client);
        JFrame frame = new JFrame("Pure ATM");
        frame.setContentPane(UI.getBase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                client.close();
            }
        });
    }
}
