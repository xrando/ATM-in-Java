package pure.test;

import org.junit.Ignore;
import org.junit.Test;
import pure.constants.Constants;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import pure.client.Client;

public class ClientTest {

    ///IMPORTANT: run server before executing the tests
    static Client c = new Client(Constants.Socket.HOST, Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
            null, null, Constants.SSL.PROTOCOL) {
        @Override
        public <T> String listen(T... input) {
            this.getSocket().write(input);
            return getSocket().read();
        }
    };

    @Ignore
    //@BeforeClass
    public static void login() {
        c.listen(Constants.User.Login, Constants.User.Username, "test", Constants.User.Password, "123123");
    }

    @AfterClass
    public static void listen() {
        c.close();
    }

    @Test
    public void testCommunication() {
        System.out.println(c.listen("Hello"));
    }
}