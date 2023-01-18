import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Client {
    public static void main(String[] args) {
        Socket socket = new Socket();
        try
        {
            socket.setSoTimeout(3000);
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 2000));
            System.out.println("Connected");
            System.out.println("Client message: " + socket.getLocalAddress() + " P: " + socket.getLocalPort());
            System.out.println("Server message: " + socket.getInetAddress() + "P: " + socket.getPort());
            todo(socket);
            socket.close();
        }catch (Exception e)
        {
            LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private static void todo(Socket client)
    {
        try
        {
            //get client input stream
            InputStream clientInputStream = System.in;
            BufferedReader clientBufferReader = new BufferedReader(new InputStreamReader(clientInputStream));

            //get client output stream to print stream
            OutputStream outputStream = client.getOutputStream();
            PrintStream socketPrintStream = new PrintStream(outputStream);

            //get server input stream
            InputStream serverInputStream = client.getInputStream();
            BufferedReader serverBufferReader = new BufferedReader(new InputStreamReader(serverInputStream));

            boolean flag = true;
            do {
                //read one line of string from system.in
                String input = clientBufferReader.readLine();
                //send input to server
                socketPrintStream.println(input);

                //get string from server
                String echo = serverBufferReader.readLine();
                if("0".equalsIgnoreCase(echo))
                {
                    flag = false;
                }
                else {
                    System.out.println(echo);
                }
            }while (flag);

            socketPrintStream.close();
            serverBufferReader.close();
        }
        catch (Exception e)
        {
            LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
