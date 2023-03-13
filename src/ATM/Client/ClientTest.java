package ATM.Client;

import ATM.Constants.Constants;
import ATM.Server.Server;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClientTest {

    ///IMPORTANT: run server before executing the tests
    static Client c = new Client();

    @BeforeClass
    public static void login() {
        c.listen(Constants.User.Login, Constants.User.Username, "test", Constants.User.Password, "123123");
    }

    @AfterClass
    public static void listen() {
        c.close();
    }
}