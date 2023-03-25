package pure.test;

import org.json.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;
import pure.constants.Constants;
import pure.client.Client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class ClientTest {

    ///IMPORTANT: run server before executing the tests
    static Client c;

    static {
        try {
            c = new Client(Constants.Socket.HOST, Constants.Socket.PORT, null, Constants.SSL.CLIENT_KEYSTORE, Constants.SSL.CLIENT_KEYSTORE_PASS,
                    null, null, Constants.SSL.PROTOCOL, Constants.Socket.TIMEOUT) {
                @Override
                public <T> JSONObject listen(T... input) {

                    try {
                        this.getSocket().writeJSON(input);
                        return getSocket().readJSON();
                    } catch (IOException e) {
                        System.out.println("error");
                    }
                    return null;
                }
            };
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void listen() {
        try {
            c.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCommunication() {
        assertEquals("Hello", c.listen("Hello").getString(Constants.JSON.TYPE));
    }
}