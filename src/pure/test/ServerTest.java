package pure.test;

import org.junit.Test;
import pure.client.ClientSocket;
import pure.constants.Constants;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import pure.server.Server;

public class ServerTest {
    static Server s = new Server(Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
            null, null, Constants.SSL.PROTOCOL) {
        @Override
        public <T> String listen(T... input) {
            while (this.getServerSocket().getSslServerSocketStatus()) {
                ClientSocket socket = this.getServerSocket().accept();
                new Thread(() -> {
                    String clientInput = socket.read();
                    System.out.println(clientInput);
                    socket.write("Received", "Message", clientInput);
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
