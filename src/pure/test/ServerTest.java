package pure.test;

import org.json.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;
import pure.client.ClientSocket;
import pure.constants.Constants;
import pure.server.Server;

public class ServerTest {
    static Server s = new Server(Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
            null, null, Constants.SSL.PROTOCOL) {
        @Override
        public <T> String listen(T... input) {
            while (this.getServerSocket().getSslServerSocketStatus()) {
                ClientSocket socket = this.getServerSocket().accept();
                new Thread(() -> {
                    JSONObject clientInput = socket.read();
                    System.out.println(clientInput);
                    socket.write(clientInput.getString(Constants.JSON.TYPE));
                }).start();
            }
            return "Server Down";
        }
    };

    @Test
    public void startServer(){
        s.listen();
    }
}
