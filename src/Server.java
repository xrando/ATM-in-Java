import pure.bank.Account;
import pure.bank.Transaction;
import pure.bank.User;
import pure.client.ClientSocket;
import pure.constants.Constants;
import pure.util.JSON;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        new pure.server.Server(Constants.Socket.PORT, null, Constants.SSL.SERVER_KEYSTORE, Constants.SSL.SERVER_KEYSTORE_PASS,
                null, null, Constants.SSL.PROTOCOL) {
            @Override
            public <T> String listen(T... input) {
                while (this.getServerSocket().getSslServerSocketStatus()) {
                    ClientSocket socket = this.getServerSocket().accept();
                    new Thread(() -> {
                        boolean flag = true;
                        User user = null;
                        Account account = null;
                        Transaction transaction;
                        while (flag) {
                            String clientInput = socket.read(); //receive client request as String

                            //for debugging:
                            System.out.println("Received: " + clientInput);

                            //request[0] is the request type, the rest are the parameters if applicable (can have no parameter)
                            //String[] request = clientInput.split(Constants.RequestBuilder.Separator);
                            JSONObject request = JSON.tryParse(clientInput);

                            switch (request.getString(Constants.JSON.Type)) {
                                case Constants.User.Login -> {
                                    user = new User();
                                    socket.write(Constants.Stream.RES, Constants.User.LoginStatus, user.Login(request.getString(Constants.User.Username), request.getString(Constants.User.Password)));
                                    //socket.write(user.Login()); //now socket.write() can receive boolean. String "true" or "false" will be sent
                                    user.getUserFromDatabase();
                                }
                                case Constants.User.Logout ->
                                        socket.write(Constants.Stream.RES, Constants.User.LoginStatus, user.logout());

                                case Constants.User.ChangePin -> {
                                    socket.write(Constants.Stream.RES, Constants.User.ChangePin, user.changePin(request.getString(Constants.User.oldPin), request.getString(Constants.User.newPin)));
                                    System.out.println(request.getString(Constants.User.oldPin));
                                    System.out.println(request.getString(Constants.User.newPin));
                                }
                                case Constants.User.CreateUser -> {
                                    User tmpuser = new User();
                                    socket.write(Constants.Stream.RES, Constants.User.CreateUser, tmpuser.CreateUser(request.getString(Constants.User.Username), request.getString(Constants.User.Password), request.getString(Constants.User.Email), request.getInt(Constants.Account.CreateAccount)));
                                }
                                case Constants.User.ForgetPin -> {
                                    User tmpuser = new User();
                                    socket.write(Constants.Stream.RES, Constants.User.ForgetPin, tmpuser.forgetPin(request.getString(Constants.User.Username)));
                                }
                                case Constants.Account.SelectAccount -> {
                                    int selectAccount = request.getInt(Constants.Account.SelectedAccount);
                                    if (user.getAccounts().size() > 0) {
                                        account = new Account(user.getAccounts().get(selectAccount));
                                        System.out.println("Selected AccountID:" + account.getAccountID() + ", Account type:" + account.getAccountType());
                                        socket.write(Constants.Stream.RES, Constants.Account.SelectedAccount, account.getAccountID());
                                        account.retrieveAccountTransactions();
                                    } else {
                                        socket.write(Constants.Stream.RES, Constants.Account.SelectedAccount, "");
                                    }

                                }
                                case Constants.Account.TransactionHistory -> {
                                    ArrayList<Transaction> transactions = account.getAccountTransactions();
//                                for (int i=0;i<transactions.size();i++){
//                                    System.out.println(transactions.get(i).getTransactionDate() + " " + transactions.get(i).getTransactionTime());
//                                }
//                                System.out.println("Account Balance: "+account.GetAccountBalance());
                                    socket.write(Constants.Stream.RES, Constants.Account.TransactionHistory, JSON.parseTransactionsToString(transactions));
                                }

                                case Constants.Account.AllAccounts -> {
                                    if (account == null) {
                                        socket.write(Constants.Stream.RES, Constants.Account.AllAccounts, "");
                                        break;
                                    }

                                    List<Account> accounts = account.getTransactionAccount(user.getUID());
                                    for (int i = 0; i < accounts.size(); i++) {
                                        System.out.println(accounts.get(i).getAccountID() + " " + accounts.get(i).getAccountType() + " " + accounts.get(i).getUID());
                                    }
                                    socket.write(Constants.Stream.RES, Constants.Account.AllAccounts, JSON.parseAccountsToString(accounts));
                                }

                                case Constants.Transaction.Withdraw -> {
                                    //Negative to make value a withdrawal
                                    transaction = new Transaction(-(request.getDouble(Constants.Transaction.Amount)), request.getString(Constants.Transaction.TransactionNote), account.getAccountID());
                                    transaction.AddTransactionToSQL(transaction);
                                    socket.write(Constants.Stream.RES, Constants.Transaction.Withdraw, "Withdrawal Complete", Constants.Account.GetAccountBalance, account.GetAccountBalance());
                                }
                                case Constants.Transaction.Deposit -> {
                                    transaction = new Transaction(request.getDouble(Constants.Transaction.Amount), request.getString(Constants.Transaction.TransactionNote), account.getAccountID());
                                    transaction.AddTransactionToSQL(transaction);
                                    socket.write(Constants.Stream.RES, Constants.Transaction.Deposit, "Deposit Complete", Constants.Account.GetAccountBalance, account.GetAccountBalance());
                                }

                                case Constants.Account.GetAccountBalance -> {
                                    socket.write(Constants.Stream.RES, Constants.Account.GetAccountBalance, account.GetAccountBalance());
                                }

                                case Constants.Account.CreateAccount -> {
                                    int selection = request.getInt(Constants.Account.CreateAccount);
                                    socket.write(Constants.Stream.RES, Constants.Account.CreateAccount, account.createAccount(selection, user.getUID()));
                                }

                                case Constants.Transaction.Transfer -> {
                                    //Negative to deduct value
                                    transaction = new Transaction(-(request.getDouble(Constants.Transaction.Amount)), request.getString(Constants.Transaction.TransactionNote), request.getString(Constants.Transaction.Payee), account.getAccountID());
                                    transaction.AddTransactionToSQL(transaction);
                                    //transaction.transactionEmail(transaction, user.getUsername(), user.getEmail());
                                    socket.write(Constants.Stream.RES, Constants.Transaction.Transfer, "Transfer Complete", Constants.Account.GetAccountBalance, account.GetAccountBalance());
                                }

                                // Get current user particulars (to allow users to see and update particulars in UI,
                                // send over all current user particulars such as email, phone number etc)
                                // Done, Name, phone and email will now be accessible
                                case Constants.User.GetUserInformation ->
                                        socket.write(Constants.Stream.RES, Constants.User.Username, user.getUsername(), Constants.User.Email, user.getEmail(), Constants.User.Phone, user.getPhone());

                                // Update user particulars (to allow users to update particulars in UI)
                                case Constants.User.UpdateUser -> {
                                    user.setUsername(request.getString(Constants.User.Username));
                                    user.setEmail(request.getString(Constants.User.Email));
                                    user.setPhone(request.getString(Constants.User.Phone));

                                    // Logout to update user information
                                    //socket.write(new JSON(Constants.Stream.RES, Constants.User.UpdateUser, "User information updated").toString());
                                }
                                case Constants.Stream.EOS -> flag = false;
                            }
                        }
                        System.out.println("Closing Client " + socket.getIP());
                        socket.close();
                        System.out.println("Closed");
                    }).start();
                }
                return "Server Down";
            }
        }.listen();
    }
}
