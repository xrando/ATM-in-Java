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

            // Client Variables
            String username = "";
            String password = "";
            String email = "";
            String phone = "";
            boolean login = false;

            try
            {
                //get server return stream
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                //get client input stream
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Check for login
                while (!login) {
                    // Client Login
                    socketOutput.println("Welcome to the Bank! Enter 0 to exit, 1 to login, 2 to register");

                    String firstInput = socketInput.readLine();

                    switch (firstInput) {
                        case "0":
                            socketOutput.println("bye");
                            socket.close();
                            break;
                        case "1": // Modify to login TODO
                            // client login
                            socketOutput.println("Enter your username: ");
                            username = socketInput.readLine();
                            socketOutput.println("Enter your password: ");
                            password = socketInput.readLine();

                            // User Login Check without database
                            User user = new User(username, password, "0", "0", new Bank("test"));
                            login = user.Login(username, password); // Change to state

                            //print output to user if login is successful
                            if (login) {
                                socketOutput.println("Login Successful, Welcome " + username + "!");
                                LogHelper.LOGGER.log(Level.INFO, "user login successful: " + username);
                            } else {
                                socketOutput.println("Login Failed");
                                LogHelper.LOGGER.log(Level.INFO, "user login failed: " + username);
                            }
                            break;
                        case "2": // Modify to register TODO
                            // client register
                            socketOutput.println("Enter your username: ");
                            username = socketInput.readLine();
                            socketOutput.println("Enter your password: ");
                            password = socketInput.readLine();

                            // User Login Check without database
                            User reguser = new User(username, password, "0", "0", new Bank("test"));
                            login = reguser.Login(username, password); // Change to state

                            //print output to user if login is successful
                            if (login) {
                                socketOutput.println("Login Successful, Welcome " + username + "!");
                                LogHelper.LOGGER.log(Level.INFO, "User registered:" + username);
                            } else {
                                socketOutput.println("Login Failed");
                                LogHelper.LOGGER.log(Level.WARNING, "Failed to register user: " + username);
                            }
                            break;
                        default:
                            socketOutput.println("Invalid Input");
                            break;
                    }
                }

                do {
                    //get client string
                    String str = socketInput.readLine();

                    // Possible to use Switch case statements here

                    if("0".equalsIgnoreCase(str))
                    {
                        flag = false;
                        socketOutput.println("bye");
                    } else if ("8".equalsIgnoreCase(str)) { //Log out Condition
                        flag = false;
                        socketOutput.println("Goodbye " + username + "!");

                    } else
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
