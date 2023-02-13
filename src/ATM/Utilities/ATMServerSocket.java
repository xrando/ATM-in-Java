package ATM.Utilities;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketImpl;

public class ATMServerSocket extends ServerSocket {
    public ATMServerSocket(int port) throws IOException {
        super(port);
    }

    /*added new method to return custom ATMSocket for accept() method*/
    public ATMSocket accept() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isBound())
            throw new SocketException("Socket is not bound yet");
        ATMSocket s = new ATMSocket((SocketImpl) null);
        implAccept(s);
        return s;
    }
}

