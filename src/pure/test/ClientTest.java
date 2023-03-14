package pure.test;

import pure.constants.Constants;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import pure.client.Client;

public class ClientTest {

    ///IMPORTANT: run server before executing the tests
    static Client c = new Client(null, Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
            null, null, Constants.SSL.PROTOCOL) {
        @Override
        public <T> String listen(T... input) {
            return null;
        }
    };

    @BeforeClass
    public static void login() {
        c.listen(Constants.User.Login, Constants.User.Username, "test", Constants.User.Password, "123123");
    }

    @AfterClass
    public static void listen() {
        c.close();
    }
}