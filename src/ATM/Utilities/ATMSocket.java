package ATM.Utilities;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;

public class ATMSocket extends Socket {
    private final static String EOF = "over"; //to indicate the end of stream.
    public ATMSocket(String host, int port) throws IOException {
        super(host, port);
    }

    public ATMSocket(SocketImpl socket) throws SocketException {
        super(socket);
    }

    /*read the input stream until hits the EOF constant
    * TODO: add decryption*/
    public String read() throws IOException {
        byte[] buf = new byte[1024];
        int b;
        StringBuilder a = new StringBuilder();
        while ((b = this.getInputStream().read(buf)) != -1){
            String temp = new String(buf, 0, b);
            if(temp.equals(EOF))
                break;
            else
                a.append(temp);
        }
        return a.toString();
    }

    /*push the String to outputstream and add EOF
    * TODO: add encryption*/
    public void write(String s) throws IOException {
        super.getOutputStream().write(s.getBytes());
        super.getOutputStream().write(EOF.getBytes());
    }

}

