package pure.test;

import org.json.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;
import pure.constants.Constants;
import pure.client.Client;

public class ClientTest {

    ///IMPORTANT: run server before executing the tests
    static Client c = new Client(Constants.Socket.HOST, Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
            null, null, Constants.SSL.PROTOCOL, Constants.Socket.TIMEOUT) {
        @Override
        public <T> JSONObject listen(T... input) {
            this.getSocket().write(input);
            return getSocket().read();
        }
    };

    @AfterClass
    public static void listen() {
        c.close();
    }

    @Test
    public void testCommunication() {
        assertEquals("Hello", c.listen("Hello").getString(Constants.JSON.TYPE));
    }
}