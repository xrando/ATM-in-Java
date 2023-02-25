package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.ATMSocket;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Client {
    private final ATMSocket socket;

    public Client() throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //get configurations from external config file
        socket = new ATMSocket(); //start new instance of client socket
    }

    public String listen(String input) throws IOException {
        this.socket.write(input);
        return socket.read();
    }

    public void close() throws Exception {
        this.socket.write(Constants.Stream.EOS);
        this.socket.close();
    }
}