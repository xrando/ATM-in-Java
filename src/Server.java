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
            ServerSocket serverSocket = new ServerSocket(2000);

            System.out.println("Connected");
            System.out.println("Server message: " + serverSocket.getInetAddress() + " P: " + serverSocket.getLocalPort());

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
            LogHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;
        ClientHandler(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            super.run();
            System.out.println("Client message: " + socket.getInetAddress() + " P: " + socket.getPort());

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
                        socketOutput.println("Return: " + str.length());
                    }
                }while (flag);
                socketOutput.close();
                socketInput.close();
            }
            catch (Exception e)
            {
                LogHelper.LOGGER.log(Level.SEVERE, e.getMessage());
            }
            finally {
                try
                {
                    socket.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    LogHelper.LOGGER.log(Level.SEVERE, e.getStackTrace().toString());
                }

            }
            System.out.println("Client Closed: " + socket.getInetAddress() + "P: " + socket.getPort());
        }
    }
}
