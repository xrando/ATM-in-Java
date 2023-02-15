package Backup;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

//to be removed
public class ATMServerSocket extends ServerSocket {
    public ATMServerSocket(int port) throws IOException {
        super(port);
    }


    //added new method to return custom ATMSocket for accept() method
    public ATMSocket accept() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isBound())
            throw new SocketException("Socket is not bound yet");
        ATMSocket s = new ATMSocket(null);
        implAccept(s);
        return s;
    }
}

