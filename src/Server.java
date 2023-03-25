import org.json.JSONException;
import org.json.JSONObject;
import pure.bank.Account;
import pure.bank.Transaction;
import pure.bank.User;
import pure.client.ClientSocket;
import pure.constants.Constants;
import pure.server.ServerSocket;
import pure.util.JSON;
import pure.util.LogHelper;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Server extends ServerSocket {
    /**
     * Creates the ssl server socket.
     * <br>
     * @param port represents the port number to connect.
     * @param keyStoreType represents the keystore type. null for default type.
     * @param keyStorePath represents the storage location of the keystore.
     * @param keyStorePass represents the password of the keystore.
     * @param keyManagerAlgorithm set null to use default.
     * @param trustManagerAlgorithm set null to use default.
     * @param protocol set null to use default.
     * */
    private Server(int port, String keyStoreType, String keyStorePath, String keyStorePass, String keyManagerAlgorithm, String trustManagerAlgorithm, String protocol) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        super(port, keyStoreType, keyStorePath, keyStorePass, keyManagerAlgorithm, trustManagerAlgorithm, protocol);
    }

    @SafeVarargs
    @Override
    public final <T> String listen(T... input) {
        while (this.getSslServerSocketStatus()) {
            ClientSocket socket = this.accept();
            new Thread(() -> {
                boolean flag = true;
                User user = null;
                Account account = null;
                Transaction transaction;
                while (flag) {
                    //receive client request
                    JSONObject request;
                    try {
                        request = socket.readJSON();
                    } catch (IOException e) {
                        LogHelper.log(Level.WARNING, "There are no bytes buffered on the socket, or all buffered bytes have been consumed by read.", e);
                        continue;
                    }

                    //try to get the request type.
                    String type;
                    try {
                        type = request.getString(Constants.JSON.TYPE);
                    } catch (JSONException e) {
                        LogHelper.log(Level.SEVERE, "No such key in JSON Object: [" + Constants.JSON.TYPE + "].", e);
                        continue;
                    }

                    //switch cases based on the request type.
                    try {
                        switch (type) {
                            case Constants.User.LOGIN -> {
                                user = new User();
                                socket.writeJSON(Constants.Stream.RES, Constants.User.LOGIN_STATUS, user.Login(request.getString(Constants.User.USERNAME), request.getString(Constants.User.PASSWORD)));
                                user.getUserFromDatabase();
                            }
                            case Constants.User.LOGOUT ->
                                    socket.writeJSON(Constants.Stream.RES, Constants.User.LOGIN_STATUS, user != null && user.logout());

                            case Constants.User.CHANGE_PIN -> {
                                socket.writeJSON(Constants.Stream.RES, Constants.User.CHANGE_PIN, user != null ? user.changePin(request.getString(Constants.User.OLD_PIN), request.getString(Constants.User.NEW_PIN)) : null);
                                System.out.println(request.getString(Constants.User.OLD_PIN));
                                System.out.println(request.getString(Constants.User.NEW_PIN));
                            }
                            case Constants.User.CREATE_USER -> {
                                User tmpuser = new User();
                                socket.writeJSON(Constants.Stream.RES, Constants.User.CREATE_USER, tmpuser.CreateUser(request.getString(Constants.User.USERNAME), request.getString(Constants.User.PASSWORD), request.getString(Constants.User.EMAIL), request.getString(Constants.User.PHONE), request.getInt(Constants.Account.CREATE_ACCOUNT)));
                            }
                            case Constants.User.FORGET_PIN -> {
                                User tmpuser = new User();
                                socket.writeJSON(Constants.Stream.RES, Constants.User.FORGET_PIN, tmpuser.forgetPin(request.getString(Constants.User.USERNAME)));
                            }
                            case Constants.Account.SELECT_ACCOUNT -> {
                                int selectAccount = request.getInt(Constants.Account.SELECTED_ACCOUNT);
                                if (user != null && user.getAccounts().size() > 0) {
                                    account = new Account(user.getAccounts().get(selectAccount));
                                    System.out.println("Selected AccountID:" + account.getAccountID() + ", Account type:" + account.getAccountType());
                                    socket.writeJSON(Constants.Stream.RES, Constants.Account.SELECTED_ACCOUNT, account.getAccountID());
                                    account.retrieveAccountTransactions();
                                }

                            }
                            case Constants.Account.TRANSACTION_HISTORY -> {
                                ArrayList<Transaction> transactions = account != null ? account.getAccountTransactions() : null;
                                socket.writeJSON(Constants.Stream.RES, Constants.Account.TRANSACTION_HISTORY, JSON.parseTransactionsToString(transactions));
                            }

                            case Constants.Account.ALL_ACCOUNTS -> {
                                if (account == null) {
                                    socket.writeJSON(Constants.Stream.RES, Constants.Account.ALL_ACCOUNTS, "");
                                    break;
                                }

                                List<Account> accounts = account.getTransactionAccount(user.getUID());
                                for (Account value : accounts) {
                                    System.out.println(value.getAccountID() + " " + value.getAccountType() + " " + value.getUID());
                                }
                                socket.writeJSON(Constants.Stream.RES, Constants.Account.ALL_ACCOUNTS, JSON.parseAccountsToString(accounts));
                            }

                            case Constants.Transaction.WITHDRAW -> {
                                //Negative to make value a withdrawal
                                transaction = new Transaction(-(request.getDouble(Constants.Transaction.AMOUNT)), request.getString(Constants.Transaction.TRANSACTION_NOTE), account != null ? account.getAccountID() : null);
                                transaction.AddTransactionToSQL(transaction, account);
                                socket.writeJSON(Constants.Stream.RES, Constants.Transaction.WITHDRAW, "Withdrawal Complete", Constants.Account.GET_ACCOUNT_BALANCE, account != null ? account.GetAccountBalance() : 0);
                            }
                            case Constants.Transaction.DEPOSIT -> {
                                transaction = new Transaction(request.getDouble(Constants.Transaction.AMOUNT), request.getString(Constants.Transaction.TRANSACTION_NOTE), account != null ? account.getAccountID() : null);
                                transaction.AddTransactionToSQL(transaction, account);
                                socket.writeJSON(Constants.Stream.RES, Constants.Transaction.DEPOSIT, "Deposit Complete", Constants.Account.GET_ACCOUNT_BALANCE, account != null ? account.GetAccountBalance() : 0);
                            }

                            case Constants.Account.GET_ACCOUNT_BALANCE -> socket.writeJSON(Constants.Stream.RES, Constants.Account.GET_ACCOUNT_BALANCE, account != null ? account.GetAccountBalance() : 0);

                            case Constants.Account.GET_ACCOUNT_SUMMARY -> {
                                List<Account> accountsList = account != null ? account.getTransactionAccount(account.getUID()) : null;
                                socket.writeJSON(Constants.Stream.RES, Constants.Account.GET_ACCOUNT_SUMMARY, JSON.parseAccountsSummaryToString(accountsList));
                            }

                            case Constants.Account.CREATE_ACCOUNT -> {
                                int selection = request.getInt(Constants.Account.CREATE_ACCOUNT);
                                socket.writeJSON(Constants.Stream.RES, Constants.Account.CREATE_ACCOUNT, account != null && account.createAccount(selection, user.getUID()));
                                if (user != null) {
                                    user.getUserFromDatabase();
                                }
                            }

                            case Constants.Account.CHANGE_TRANSACTION_LIMIT -> {
                                int newLimit = request.getInt(Constants.Account.CHANGE_TRANSACTION_LIMIT);
                                int selectAccount = request.getInt(Constants.Account.SELECTED_ACCOUNT);
                                socket.writeJSON(Constants.Stream.RES, Constants.Account.CHANGE_TRANSACTION_LIMIT, account != null && account.changeTransactionLimit(newLimit,user.getAccounts().get(selectAccount).getAccountID()));
                            }

                            case Constants.Account.GET_TRANSACTION_LIMIT -> socket.writeJSON(Constants.Stream.RES, Constants.Account.GET_TRANSACTION_LIMIT, account != null ? account.getTransactionLimit() : null);

                            case Constants.Transaction.TRANSFER -> {
                                //Negative to deduct value
                                transaction = new Transaction(-(request.getDouble(Constants.Transaction.AMOUNT)), request.getString(Constants.Transaction.TRANSACTION_NOTE), account != null ? account.getAccountID() : null, request.getString(Constants.Transaction.PAYEE));
                                transaction.AddTransactionToSQL(transaction, account);
                                //transaction.transactionEmail(transaction, user.getUsername(), user.getEmail());
                                socket.writeJSON(Constants.Stream.RES, Constants.Transaction.TRANSFER, "Transfer Complete", Constants.Account.GET_ACCOUNT_BALANCE, account != null ? account.GetAccountBalance() : 0);
                            }


                            // Get current user particulars (to allow users to see and update particulars in UI,
                            // send over all current user particulars such as email, phone number etc)
                            // Done, Name, phone and email will now be accessible
                            case Constants.User.GET_USER_INFORMATION ->
                                    socket.writeJSON(Constants.Stream.RES, Constants.User.USERNAME, user != null ? user.getUsername() : null, Constants.User.EMAIL, user != null ? user.getEmail() : null, Constants.User.PHONE, user != null ? user.getPhone() : null);

                            // Update user particulars (to allow users to update particulars in UI)
                            case Constants.User.UPDATE_USER -> {
                                if(user != null){
                                    user.setUsername(request.getString(Constants.User.USERNAME));
                                    user.setEmail(request.getString(Constants.User.EMAIL));
                                    user.setPhone(request.getString(Constants.User.PHONE));
                                }
                                // Logout to update user information
                                socket.writeJSON(Constants.Stream.RES, Constants.User.UPDATE_USER, "User information updated");
                            }
                            // set flag to false when exit UI, breaks the loop and close the socket connection.
                            case Constants.Stream.EOS -> flag = false;
                            default -> throw new IllegalStateException("Unexpected value: " + type);
                        }
                    } catch (IOException e) {
                        LogHelper.log(Level.WARNING, "There are no bytes buffered on the socket, or all buffered bytes have been consumed by read.", e);
                    }
                }
                System.out.println("Closing Client " + socket.getIP());
                try {
                    socket.close();
                } catch (IOException e) {
                    LogHelper.log(Level.WARNING, "I/O error occurs when closing this socket.", e);
                }
                System.out.println("Closed");
            }).start();
        }
        return "Server Down";
    }

    public static void main(String[] args) {
        try {
            Server s = new Server(Constants.Socket.PORT, null, Constants.SSL.SERVER_KEYSTORE, Constants.SSL.SERVER_KEYSTORE_PASS,
                    null, null, Constants.SSL.PROTOCOL);
            s.listen();
        } catch (IOException e) {
            LogHelper.log(Level.SEVERE, "Check port number.", e);
        } catch (NullPointerException e) {
            LogHelper.log(Level.SEVERE, "Failed to generate ssl server socket.", e);
        } catch (CertificateException e) {
            LogHelper.log(Level.SEVERE, "Could not load certificate.", e);
        } catch (NoSuchAlgorithmException e) {
            LogHelper.log(Level.SEVERE, "No Provider supports a KeyManagerFactorySpi implementation for the specified algorithm", e);
        } catch (KeyStoreException e) {
            LogHelper.log(Level.SEVERE, "Failed to load keystore.", e);
        } catch (UnrecoverableKeyException e) {
            LogHelper.log(Level.SEVERE, "Password might be incorrect.", e);
        } catch (KeyManagementException e) {
            LogHelper.log(Level.SEVERE, "Key expired or failed authorization.", e);
        }
    }
}
