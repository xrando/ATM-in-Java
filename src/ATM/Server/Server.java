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
import java.util.ArrayList;
import java.util.List;
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
                Transaction transaction;
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
                                user = new User();
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.User.LoginStatus, user.Login(request.getString(Constants.User.Username), request.getString(Constants.User.Password))).toString());
                                //socket.write(user.Login()); //now socket.write() can receive boolean. String "true" or "false" will be sent
                                user.getUserFromDatabase();
                            }
                            case Constants.User.Logout -> socket.write(new JSON(Constants.Stream.RES).add(Constants.User.LoginStatus, user.logout()).toString());

                            case Constants.User.ChangePin -> {
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.User.ChangePin, user.changePin(request.getString(Constants.User.oldPin), request.getString(Constants.User.newPin))).toString());
                                System.out.println(request.getString(Constants.User.oldPin));
                                System.out.println(request.getString(Constants.User.newPin));
                            }
                            case Constants.User.CreateUser -> {
                                User tmpuser = new User();
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.User.CreateUser, tmpuser.CreateUser(request.getString(Constants.User.Username), request.getString(Constants.User.Password),request.getString(Constants.User.Email), request.getInt(Constants.Account.CreateAccount))).toString());
                            }
                            case Constants.User.ForgetPin -> {
                                User tmpuser = new User();
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.User.ForgetPin, tmpuser.forgetPin(request.getString(Constants.User.Username))).toString());
                            }
                            case Constants.Account.SelectAccount -> {
                                int selectAccount = request.getInt(Constants.Account.SelectedAccount);
                                account = new Account(user.getAccounts().get(selectAccount));
                                System.out.println("Selected AccountID:"+account.getAccountID() + ", Account type:" +account.getAccountType());
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Account.SelectedAccount, account.getAccountID()).toString());
                                account.retrieveAccountTransactions();
                            }
                            case Constants.Account.TransactionHistory -> {
                                ArrayList<Transaction> transactions = account.getAccountTransactions();
//                                for (int i=0;i<transactions.size();i++){
//                                    System.out.println(transactions.get(i).getTransactionDate() + " " + transactions.get(i).getTransactionTime());
//                                }
//                                System.out.println("Account Balance: "+account.GetAccountBalance());
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Account.TransactionHistory,JSON.parseTransactionsToString(transactions)).toString());
                            }

                            case Constants.Account.AllAccounts -> {
                                List<Account> accounts = account.getTransactionAccount(user.getUID());
                                for (int i=0;i<accounts.size();i++){
                                    System.out.println(accounts.get(i).getAccountID() + " " + accounts.get(i).getAccountType() + " " + accounts.get(i).getUID());
                                }
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Account.AllAccounts,JSON.parseAccountsToString(accounts)).toString());
                            }

                            case Constants.Transaction.Withdraw -> {
                                //Negative to make value a withdrawal
                                transaction = new Transaction(-(request.getDouble(Constants.Transaction.Amount)), request.getString(Constants.Transaction.TransactionNote),account.getAccountID());
                                transaction.AddTransactionToSQL(transaction);
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Transaction.Withdraw,"Withdrawal Complete").add(Constants.Account.GetAccountBalance,account.GetAccountBalance()).toString());
                            }
                            case Constants.Transaction.Deposit -> {
                                transaction = new Transaction(request.getDouble(Constants.Transaction.Amount), request.getString(Constants.Transaction.TransactionNote),account.getAccountID());
                                transaction.AddTransactionToSQL(transaction);
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Transaction.Deposit,"Deposit Complete").add(Constants.Account.GetAccountBalance,account.GetAccountBalance()).toString());
                            }

                            case Constants.Account.GetAccountBalance -> {
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Account.GetAccountBalance,account.GetAccountBalance()).toString());
                            }

                            case Constants.Account.CreateAccount -> {
                                int selection = request.getInt(Constants.Account.CreateAccount);
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Account.CreateAccount, account.createAccount(selection,user.getUID())).toString());
                            }

                            case Constants.Transaction.Transfer -> {
                                //Negative to deduct value
                                transaction = new Transaction(-(request.getDouble(Constants.Transaction.Amount)), request.getString(Constants.Transaction.TransactionNote),request.getString(Constants.Transaction.Payee),account.getAccountID());
                                transaction.AddTransactionToSQL(transaction);
                                //transaction.transactionEmail(transaction, user.getUsername(), user.getEmail());
                                socket.write(new JSON(Constants.Stream.RES).add(Constants.Transaction.Transfer,"Transfer Complete").add(Constants.Account.GetAccountBalance,account.GetAccountBalance()).toString());
                            }

                            // Get current user particulars (to allow users to see and update particulars in UI,
                            // send over all current user particulars such as email, phone number etc)
                            // Done, Name, phone and email will now be accessible
                            case Constants.User.GetUserInformation ->  socket.write(new JSON(Constants.Stream.RES).add(Constants.User.Username, user.getUsername()).add(Constants.User.Email, user.getEmail()).add(Constants.User.Phone, user.getPhone()).toString());

                            // Update user particulars (to allow users to update particulars in UI)
                            case Constants.User.UpdateUser -> {
                                user.setUsername(request.getString(Constants.User.Username));
                                user.setEmail(request.getString(Constants.User.Email));
                                user.setPhone(request.getString(Constants.User.Phone));

                                // Logout to update user information
                                //socket.write(new JSON(Constants.Stream.RES).add(Constants.User.UpdateUser, "User information updated").toString());
                            }
                        }
                    }
                    socket.close();
            }).start();
        }
        System.out.println("Server down.");
    }
}
