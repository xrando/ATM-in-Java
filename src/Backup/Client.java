package Backup;

import ATM.Constants.Constants.Socket;
import ATM.Constants.Constants.Stream;
import Backup.ATMSocket;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

//to be removed
public class Client {
    private final java.net.Socket socket;

    public Client() throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //get configurations from external config file
        socket = new ATMSocket(Socket.HOST, Socket.PORT); //start new instance of client socket
    }

    public String listen(String input) throws IOException {
        this.socket.write(input);
        return socket.read();
    }

    public void close() throws IOException {
        this.socket.write(Stream.EOS);
        this.socket.close();
    }
}