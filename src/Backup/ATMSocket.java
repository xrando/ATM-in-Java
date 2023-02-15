package Backup;

import ATM.Constants.Constants.Stream;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;

//to be removed
public class ATMSocket extends Socket {
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
        while ((b = this.getInputStream().read(buf)) != -1) {
            String temp = new String(buf, 0, b);
            if (temp.endsWith(Stream.EOF)) {
                a.append(temp, 0, temp.length() - Stream.EOF.length());
                break;
            } else
                a.append(temp);
        }
        return a.toString();
    }

    /*push the String to outputstream and add EOF
     * TODO: add encryption*/
    public void write(String s) throws IOException {
        super.getOutputStream().write(s.getBytes());
        super.getOutputStream().write(Stream.EOF.getBytes());
        super.getOutputStream().flush();
    }

}

