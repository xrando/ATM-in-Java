package ATM.Server;

import ATM.Bank.User;
import ATM.Bank.Account;
import ATM.Bank.Transaction;
import ATM.Constants.Constants;
import ATM.Utilities.ATMServerSocket;
import ATM.Utilities.ATMSocket;
import ATM.Utilities.JSON;
import ATM.Utilities.LogHelper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;

public class Server {
    public void listen() {
        ATMServerSocket ss = null;
        try {
            ss = new ATMServerSocket();
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Check port number.", e);
            System.exit(-1);
        } catch (NullPointerException e) {
            LogHelper.log(Level.SEVERE, "Failed to generate ssl server socket.", e);
            System.exit(-1);
        }

        while (ss.getSslServerSocketStatus()) {
            ATMSocket socket = ss.accept();
            new Thread(() -> {
                User user = null;
                Account account = null;
                Transaction transaction = null;
                    while (true) {
                        /*once server receives a client request, it MUST respond to the client to continue*/
                        String clientInput = socket.read(); //receive client request as String

                        //for debugging:
                        System.out.println("Received: " + clientInput);

                        //exit code to safely end the connection
                        if (clientInput.equals(Constants.Stream.EOS) | clientInput.isEmpty())
                            break;

                        //request[0] is the request type, the rest are the parameters if applicable (can have no parameter)
                        //String[] request = clientInput.split(Constants.RequestBuilder.Separator);
                        JSONObject request = JSON.tryParse(clientInput);

                        switch (request.getString(Constants.JSON.Type)){
                            case Constants.User.Login -> {
                                user = new User(request.getString(Constants.User.Username), request.getString(Constants.User.Password));
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.User.LoginStatus, user.Login()).toString()); //now socket.write() can receive boolean. String "true" or "false" will be sent
                                //socket.write(user.Login()); //now socket.write() can receive boolean. String "true" or "false" will be sent
                                user.getUserFromDatabase();
                            }
                            case Constants.User.Logout -> socket.write(new JSON(Constants.Stream.RES).add(Constants.User.LoginStatus, user.logout()).toString());
                            //TODO: complete the cases
                            case Constants.User.ChangePin -> {
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.User.ChangePin, user.changePin(request.getString(Constants.User.oldPin), request.getString(Constants.User.newPin))).toString());
                                System.out.println(request.getString(Constants.User.oldPin));
                                System.out.println(request.getString(Constants.User.newPin));
                            }
                            case Constants.User.CreateUser -> {
                                User tmpuser = new User();
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.User.CreateUser, tmpuser.CreateUser(request.getString(Constants.User.Username), request.getString(Constants.User.Password))).toString());
                            }
                            case Constants.Account.SelectAccount -> {
                                int selectAccount = request.getInt(Constants.Account.SelectedAccount);
                                account = new Account(user.getAccounts().get(selectAccount));
                                System.out.println("Selected AccountID:"+account.getAccountID() + ", Account type:" +account.getAccountType());
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Account.SelectedAccount, account.getAccountID()).toString());
                                account.retrieveAccountTransactions();

                            }
                            case Constants.Account.TransactionHistory -> {
                                for (int i=0;i<account.getAccountTransactions().size();i++){
                                    System.out.println(account.getAccountTransactions().get(i).getAmount());
                                }
                                System.out.println("Account Balance: "+account.GetAccountBalance());
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Account.TransactionHistory,"Transaction Retrieved").toString());
                            }
                            case Constants.Transaction.Withdraw -> {
                                //Negative to make value a withdrawal
                                transaction = new Transaction(-(request.getDouble(Constants.Transaction.Amount)), request.getString(Constants.Transaction.TransactionNote),account.getAccountID());
                                transaction.AddTransactionToSQL(account,transaction);
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Transaction.Withdraw,"Withdrawal Complete").add(Constants.Account.GetAccountBalance,account.GetAccountBalance()).toString());
                            }
                            case Constants.Transaction.Deposit -> {
                                transaction = new Transaction(request.getDouble(Constants.Transaction.Amount), request.getString(Constants.Transaction.TransactionNote),account.getAccountID());
                                transaction.AddTransactionToSQL(account,transaction);
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Transaction.Deposit,"Deposit Complete").add(Constants.Account.GetAccountBalance,account.GetAccountBalance()).toString());
                            }

                            //TODO: send back all accountids of current user
                            //case Constants.Account.AllAccounts

                            //TODO: send back all account summary of current user
                            //case Constants.Account.AllAccountSummary

                            //TODO: send back account balance of accountId received of current user
                            //case Constants.Account.GetAccountBalance
                        }
                    }
                    socket.close();
            }).start();
        }
        System.out.println("Server down.");
    }
}
