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
            //set time out: if server no response
            socket.setSoTimeout(3000);
            socket.connect(new InetSocketAddress(InetAddress.getByName(ConfigurationManager.GetConfig("Server")), 2000));

            LogHelper.LOGGER.info("Local machine: " + socket.getLocalAddress() + " Port: " + socket.getLocalPort());
            LogHelper.LOGGER.info("Connected to Server: " + socket.getInetAddress() + " Port: " + socket.getPort());

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
            //initialize table obj
            TableHelper tb = new TableHelper(true, true);

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
                    //print table
                    tb.setHeaders("Input", "Output");
                    tb.addRow(input, echo);
                    tb.print(false);
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
