import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

public class Server {
    public static void main(String[] args) {
        try
        {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(ConfigurationManager.GetConfig("Port")));

            LogHelper.LOGGER.info("Server Connected");
            LogHelper.LOGGER.info("Server IP: " + serverSocket.getInetAddress() + " Port: " + serverSocket.getLocalPort());

            //wait for client
            for (;;)
            {
                //get client
                Socket client = serverSocket.accept();

                //multi thread client
                ClientHandler clientHandler = new ClientHandler(client);
                clientHandler.start();
            }
        }catch (Exception e)
        {
            LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;
        private boolean flag = true;
        ClientHandler(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            super.run();
            LogHelper.LOGGER.info("Server Connected: " + socket.getInetAddress() + " Port: " + socket.getPort());

            try
            {
                //get server return stream
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                //get client input stream
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                do {
                    //get client string
                    String str = socketInput.readLine();
                    if("0".equalsIgnoreCase(str))
                    {
                        flag = false;
                        socketOutput.println("bye");
                    }
                    else
                    {
                        System.out.println(str);
                        socketOutput.println(str.length());
                    }
                }while (flag);
                socketOutput.close();
                socketInput.close();
            }
            catch (Exception e)
            {
                LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
            finally {
                try
                {
                    socket.close();
                }
                catch (Exception e)
                {
                    LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }

            }
            LogHelper.LOGGER.info("Client Closed: " + socket.getInetAddress() + " Port: " + socket.getPort());
        }
    }
}
