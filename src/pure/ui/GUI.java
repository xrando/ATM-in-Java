package pure.ui;

/**
 * Represents a class that acts as both the graphical user interface and client for this application.
 * <br><br>
 * For server/client implementation, event listeners represents the requests to be sent to server during the communication.
 * **/

import pure.constants.Constants;
import pure.client.Client;
import pure.util.InputSanitisation;
import pure.util.LogHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.logging.Level;

import static java.util.Map.entry;

public class GUI {
    private JPanel base;
    private JPanel login;
    private JPanel main;
    private JPanel button;
    private JPanel screen;
    private JButton btnBack;
    private JButton btnLogout;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JButton btnLogin;
    private JLabel lblUsernameValidator;
    private JLabel lblLoginValidator;
    private JPanel menu;
    private JButton btnViewTransactions;
    private JButton btnWithdrawal;
    private JButton btnDeposit;
    private JButton btnBankTransfer;
    private JButton btnSettings;
    private JPanel ViewTransactions;
    private JTextArea AllTransactions;
    private JPanel Withdrawal;
    private JPanel Deposit;
    private JPanel BankTransfer;
    private JPanel Settings;
    private JLabel lblUserName;
    private JTextField txtEmailAddress;
    private JTextField txtPhoneNumber;
    private JButton btnChangePassword;
    private JTextField txtOldPassword;
    private JTextField txtNewPassword;
    private JButton btnUpdatePassword;
    private JLabel lblConfirmPasswordValidator;
    private JPanel ChangePassword;
    private JButton btnSaveChanges;
    private JLabel lblChangeSuccessful;
    private JComboBox ddlWithdrawAccounts;
    private JLabel lblWithdrawAccountBalance;
    private JTextField txtWithdrawalAmount;
    private JLabel lblWithdrawalAmountValidator;
    private JButton btnConfirmWithdrawal;
    private JComboBox ddlDepositAccounts;
    private JTextField txtDepositAmount;
    private JLabel lblDepositAccountbalance;
    private JButton btnConfirmDeposit;
    private JLabel lblWelcomeMessage;
    private JComboBox ddlTransferAccounts;
    private JLabel lblTransferAccountBalance;
    private JTextField txtTransferToAccount;
    private JTextField txtTransferAmount;
    private JButton btnTransfer;
    private JTextField txtWithdrawalNote;
    private JTextField txtDepositNote;
    private JLabel lblOldPasswordValidator;
    private JTextField txtConfirmPassword;
    private JButton btnChangeLang;
    private JPanel changeLanguage;
    private JButton btnEnglish;
    private JButton btnChinese;
    private JLabel lblUser;
    private JLabel lblPass;
    private JLabel lblViewTransaction;
    private JLabel lblWithdraw;
    private JLabel lblWithdrawAccount;
    private JLabel lblWithdrawalNote;
    private JLabel lblWithdrawalBalance;
    private JLabel lblWithdrawalAmount;
    private JLabel lblDeposit;
    private JLabel lblDepositAccount;
    private JLabel lblDepositNote;
    private JLabel lblDepositBalance;
    private JLabel lblDepositAmount;
    private JLabel lblBankTransfer;
    private JLabel lblTransferAccount;
    private JLabel lblTransferBalance;
    private JLabel lblTransferToAccount;
    private JLabel lblTransferAmount;
    private JLabel lblsetting;
    private JLabel lblsUser;
    private JLabel lblEmail;
    private JLabel lblphoneNumber;
    private JLabel lbloldPassword;
    private JLabel lblnewPassword;
    private JLabel lblconfirmPassword;
    private JLabel lblchooseLang;
    private JButton btnTransactionLimit;
    private JPanel TransactionLimit;
    private JLabel lblTransactionLimit;
    private JButton btnNewUser;
    private JPanel NewUser;
    private JLabel lblCreateNewUser;
    private JTextField txtNewUsername;
    private JTextField txtNewUserPassword;
    private JLabel lblNewUsername;
    private JButton btnCreateNewUser;
    private JButton btnNewUserBack;
    private JLabel lblNewUserpw;
    private JLabel lblTransferAmountValidator;
    private JComboBox ddlTransactionAccounts;
    private JButton btnForgetPassword;
    private JLabel lblNewUserEmail;
    private JTextField txtNewUserEmail;
    private JLabel lblNewUserPhoneNumber;
    private JTextField txtNewUserPhoneNumber;
    private JLabel lblNewUserAccount;
    private JComboBox ddlNewUserAccount;
    private JLabel lblNewUserValidator;
    private JLabel lblDepositAmountValidator;
    private JScrollPane AllTransactionScoller;
    private JTextField txtNewTransactionLimitAmount;
    private JButton btnUpdateTransactionLimit;
    private JLabel lblCurrentTransactionLimit;
    private JLabel lblCurrentTransactionLimitAmount;
    private JLabel lblNewTransactionLimit;
    private JLabel lblTransactionLimitAmmountTransfer;
    private JLabel lblTransactionLimitTransfer;
    private JLabel lblTransactionLimitDeposit;
    private JLabel lblTransactionLimitAmountWithdrawal;
    private JLabel lblTransactionLimitWithdrawal;
    private JButton btnViewAccountSummary;
    private JPanel ViewAccountSummary;
    private JLabel lblViewAccountSummary;
    private JTextArea AccountSummary;
    private JButton btnOpenNewAccount;
    private JPanel OpenNewAccount;
    private JLabel lblOpenNewAccount;
    private JComboBox ddlOpenNewAccount;
    private JButton btnOpenAccount;
    private JLabel lblNewAccountCreationValidator;
    private JScrollPane AccountSummaryScroller;
    private JComboBox ddlTransactionLimitAccounts;
    private final Map<String, String> English = Map.ofEntries(
            entry("0", "Username:"),
            entry("1", "Password:"),
            entry("2", "Login"),
            entry("3", "Language"),
            entry("4", "Back"),
            entry("5", "Logout"),
            entry("6", "Welcome back "),
            entry("7", "View Transactions"),
            entry("8", "Withdrawal"),
            entry("9", "Deposit"),
            entry("10", "Bank Transfer"),
            entry("11", "Settings"),
            entry("12", "View Transactions"),
            entry("13", "Withdrawal"),
            entry("14", "Account:"),
            entry("15", "Transaction Note:"),
            entry("16", "Balance:"),
            entry("17", "Withdrawal amount:"),
            entry("18", "Withdraw"),
            entry("19", "Deposit"),
            entry("20", "Account:"),
            entry("21", "Transaction Note:"),
            entry("22", "Balance:"),
            entry("23", "Deposit amount:"),
            entry("24", "Deposit"),
            entry("25", "Bank Transfer"),
            entry("26", "Account:"),
            entry("27", "Balance:"),
            entry("28", "Transfer to account:"),
            entry("29", "Transfer amount:"),
            entry("30", "Transfer:"),
            entry("31", "Settings"),
            entry("32", "UserName"),
            entry("33", "Change Password"),
            entry("34", "Email Address"),
            entry("35", "Phone Number"),
            entry("36", "Save Changes"),
            entry("37", "Old Password"),
            entry("38", "New Password"),
            entry("39", "Confirm Password"),
            entry("40", "Update Password"),
            entry("41", "Choose Language"),
            entry("42", "English"),
            entry("43", "Chinese"),
            entry("44", "Create New User"),
            entry("45", "Set Transaction Limit"),
            entry("46", "Forget Password"),
            entry("47", "Current Transaction Limit:"),
            entry("48", "New Transaction Limit:"),
            entry("49", "Update Transaction Limit"),
            entry("50","View Account Summary"),
            entry("51","Open New Account"),
            entry("52","Open Account")
    );

    private final Map<String, String> Chinese = Map.ofEntries(
            entry("0", "用户名:"),
            entry("1", "密码:"),
            entry("2", "登录"),
            entry("3", "语言"),
            entry("4", "后退"),
            entry("5", "登出"),
            entry("6", "欢迎回来 "),
            entry("7", "查看交易"),
            entry("8", "退出"),
            entry("9", "订金"),
            entry("10", "银行转帐"),
            entry("11", "设置"),
            entry("12", "查看交易"),
            entry("13", "退出"),
            entry("14", "帐户:"),
            entry("15", "交易须知:"),
            entry("16", "平衡:"),
            entry("17", "提款金额:"),
            entry("18", "提取"),
            entry("19", "订金"),
            entry("20", "帐户:"),
            entry("21", "交易须知:"),
            entry("22", "平衡:"),
            entry("23", "存款金额:"),
            entry("24", "订金"),
            entry("25", "银行转帐"),
            entry("26", "帐户:"),
            entry("27", "平衡:"),
            entry("28", "转入账户:"),
            entry("29", "转账金额:"),
            entry("30", "转移:"),
            entry("31", "设置"),
            entry("32", "用户名"),
            entry("33", "更改密码"),
            entry("34", "电子邮件地址"),
            entry("35", "电话号码"),
            entry("36", "保存更改"),
            entry("37", "旧密码"),
            entry("38", "新密码"),
            entry("39", "确认密码"),
            entry("40", "更新密码"),
            entry("41", "选择语言"),
            entry("42", "英语"),
            entry("43", "中文"),
            entry("44", "创建新用户"),
            entry("45", "设定交易限额"),
            entry("46", "忘记密码"),
            entry("47", "当前交易限额:"),
            entry("48", "新交易限额:"),
            entry("49","更新交易限额"),
            entry("50","查看帐户摘要"),
            entry("51","开设新帐户"),
            entry("52","开户口")
    );

    public GUI(Client client) {
        final String[] language = new String[]{"eng"};

        //Create event listener for login button
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Input validation
                String pinValid = InputSanitisation.validPin(txtPassword.getText());
                String nameValid = InputSanitisation.validNameString(txtUsername.getText());
                //if input pass validation
                if (pinValid.equals("true") && nameValid.equals("true")) {
                    //test account username: test, pw:123123
                    //init json object to store replies from server
                    JSONObject jo = client.listen(Constants.User.LOGIN, Constants.User.PASSWORD, txtPassword.getText(), Constants.User.USERNAME, txtUsername.getText());

                    //Get user input from textbox and on successful login, show main menu
                    if (jo.get(Constants.User.LOGIN_STATUS).toString().equalsIgnoreCase("true")) {
                        lblLoginValidator.setText("");
                        if (language[0].equals("eng")) {
                            //set welcome msg
                            lblWelcomeMessage.setText("Welcome " + txtUsername.getText());
                        } else {
                            //set welcome msg
                            lblWelcomeMessage.setText("欢迎 " + txtUsername.getText());
                        }
                        //attach main menu screen
                        setScreen(base, main);
                        setScreen(screen, menu);
                        //Onlogin
                        //Select User Account
                        JSONObject SelectAccount = client.listen(Constants.Account.SELECT_ACCOUNT, Constants.Account.SELECTED_ACCOUNT, 0);

                        //populate dropdownlists with data
                        //Send request to server to get all accounts of current users
                        //Retrieve All User Accounts
                        JSONObject retrieveAccounts = client.listen(Constants.Account.ALL_ACCOUNTS);
                        JSONArray ja2 = new JSONArray(retrieveAccounts.get(Constants.Account.ALL_ACCOUNTS).toString());
                        ja2.forEach(record -> {
                            JSONObject joo2 = new JSONObject(record.toString());
                            //populate dropdownlists with user accounts
                            ddlWithdrawAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                            ddlDepositAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                            ddlTransferAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                            ddlTransactionAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                            ddlTransactionLimitAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                        });
                        //populate transaction limit labels
                        //Send request to server to get transaction limit for current user
                        JSONObject jo1 = client.listen(Constants.Account.GET_TRANSACTION_LIMIT);
                        //populate label with data
                        lblTransactionLimitAmmountTransfer.setText("$"+jo1.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                        lblTransactionLimitAmountWithdrawal.setText("$"+jo1.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                        //populate open new account ddl
                        ddlOpenNewAccount.removeAllItems();
                        ddlOpenNewAccount.addItem("savings");
                        ddlOpenNewAccount.addItem("current");

                    } else {
                        lblLoginValidator.setText("Wrong credentials");
                    }
                } else {
                    if (!pinValid.equals("true")) {
                        lblLoginValidator.setText(pinValid);
                    } else if (!nameValid.equals("true")) {
                        lblLoginValidator.setText(nameValid);
                    }
                }
            }
        });
        //Create event listener for logout button
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //sent request to server to logout
                JSONObject logout = client.listen(Constants.User.LOGOUT);
                //clear ddl
                ddlWithdrawAccounts.removeAllItems();
                ddlDepositAccounts.removeAllItems();
                ddlTransferAccounts.removeAllItems();
                ddlTransactionAccounts.removeAllItems();
                ddlTransactionLimitAccounts.removeAllItems();
                txtUsername.setText("");
                txtPassword.setText("");
                //re-attach login screen
                setScreen(base, login);
            }
        });
        //Create event listener for back button
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //re-attach main menu
                setScreen(screen, menu);
            }
        });
        //Create event listener for view transactions button
        btnViewTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = "";
                //populate records
                if(ddlTransactionAccounts.getItemCount() != 0)
                {
                    choice = ddlTransactionAccounts.getItemAt(ddlTransactionAccounts.getSelectedIndex()).toString();
                    //send select acc request to server
                    JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT, Constants.Account.SELECTED_ACCOUNT, ddlTransactionAccounts.getSelectedIndex()));
                    //Onclick, send request to server to get transaction history of accountId selected
                    JSONObject jo = client.listen(Constants.Account.TRANSACTION_HISTORY, Constants.Account.ACCOUNT_ID, choice);
                    //populate date to screen
                    JSONArray ja = new JSONArray(jo.get(Constants.Account.TRANSACTION_HISTORY).toString());
                    //clear text area
                    AllTransactions.setText("");
                    ja.forEach(record -> {
                        JSONObject joo = new JSONObject(record.toString());
                        //append new transactions to text area
                        AllTransactions.append("  Transaction Note: " + joo.get(Constants.Transaction.TRANSACTION_NOTE) + "\n");
                        AllTransactions.append("  Amount: $" + joo.get(Constants.Transaction.AMOUNT) + "\n");
                        AllTransactions.append("  Transaction Date: " + joo.get(Constants.Transaction.DATE) + "\n");
                        AllTransactions.append("  Transaction Time: " + joo.get(Constants.Transaction.TIME_STAMP) + "\n");
                        AllTransactions.append("\n\n");
                    });
                }

                //set scroller to focus to top
                AllTransactions.setCaretPosition(0);
                //attach view transaction screen
                setScreen(screen, ViewTransactions);
            }
        });
        //Create event listener for withdrawal button
        btnWithdrawal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear fields on load
                txtWithdrawalNote.setText("");
                txtWithdrawalAmount.setText("");
                lblWithdrawalAmountValidator.setText("");
                lblTransactionLimitAmountWithdrawal.setText("");
                //attach withdrawal screen
                setScreen(screen, Withdrawal);
                String choice = "";
                if(ddlWithdrawAccounts.getItemCount() != 0)
                {
                    choice = ddlWithdrawAccounts.getItemAt(ddlWithdrawAccounts.getSelectedIndex()).toString();
                    //send select account request to server
                    new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT, Constants.Account.SELECTED_ACCOUNT, String.valueOf(ddlWithdrawAccounts.getSelectedIndex())));
                    //Onclick, send request to server to get balance of accountId selected
                    JSONObject jo = client.listen(Constants.Account.GET_ACCOUNT_BALANCE, Constants.Account.ACCOUNT_ID, choice);
                    //update balance label to display updated balance amount
                    lblWithdrawAccountBalance.setText("$" + jo.get(Constants.Account.GET_ACCOUNT_BALANCE).toString());
                    //get limit for account
                    //send select account request to server
                    JSONObject jo2 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT
                            , Constants.Account.SELECTED_ACCOUNT,
                            ddlWithdrawAccounts.getSelectedIndex()));
                    JSONObject jo4 = client.listen(Constants.Account.GET_TRANSACTION_LIMIT);
                    //populate label with data
                    lblTransactionLimitAmountWithdrawal.setText("$"+jo4.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                }


            }
        });
        //Create event listener for deposit button
        btnDeposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear fields on load
                txtDepositNote.setText("");
                txtDepositAmount.setText("");
                lblDepositAmountValidator.setText("");

                //attach deposit screen
                setScreen(screen, Deposit);
                String choice = "";
                if(ddlDepositAccounts.getItemCount() != 0)
                {
                    choice = ddlDepositAccounts.getItemAt(ddlDepositAccounts.getSelectedIndex()).toString();
                    //send select account request to server
                    new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT, Constants.Account.SELECTED_ACCOUNT, ddlDepositAccounts.getSelectedIndex()));
                    //Onclick, send request to server to get balance of accountId selected
                    JSONObject jo = client.listen(Constants.Account.GET_ACCOUNT_BALANCE, Constants.Account.ACCOUNT_ID, choice);
                    //update balance label to display updated balance amount
                    lblDepositAccountbalance.setText("$" + jo.get(Constants.Account.GET_ACCOUNT_BALANCE).toString());
                }

            }
        });
        //Create event listener for bank transfer button
        btnBankTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear fields
                txtTransferToAccount.setText("");
                txtTransferAmount.setText("");
                lblTransferAmountValidator.setText("");
                lblTransactionLimitAmmountTransfer.setText("");
                //attach bank transfer screen
                setScreen(screen, BankTransfer);
                String choice = "";
                if(ddlTransactionAccounts.getItemCount() != 0)
                {
                    choice = ddlTransactionAccounts.getItemAt(ddlTransactionAccounts.getSelectedIndex()).toString();
                    //send select account request to server
                    new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT, Constants.Account.SELECTED_ACCOUNT, ddlTransactionAccounts.getSelectedIndex()));
                    //Onclick, send request to server to get balance of accountId selected
                    JSONObject jo = client.listen(Constants.Account.GET_ACCOUNT_BALANCE, Constants.Account.ACCOUNT_ID, choice);
                    //update balance label to display updated balance amount
                    lblTransferAccountBalance.setText("$" + jo.get(Constants.Account.GET_ACCOUNT_BALANCE).toString());
                    //get limit for account
                    //send select account request to server
                    JSONObject jo2 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT
                            , Constants.Account.SELECTED_ACCOUNT,
                            ddlTransferAccounts.getSelectedIndex()));
                    JSONObject jo4 = client.listen(Constants.Account.GET_TRANSACTION_LIMIT);
                    //populate label with data
                    lblTransactionLimitAmmountTransfer.setText("$"+jo4.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                }

            }
        });
        //Create event listener for settings button
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear validator
                lblChangeSuccessful.setText("");
                //send Get User Info request
                JSONObject GetUserInfo = client.listen(Constants.User.GET_USER_INFORMATION);
                //populate fields with current user data
                lblUserName.setText(GetUserInfo.get(Constants.User.USERNAME).toString());
                txtEmailAddress.setText(GetUserInfo.get(Constants.User.EMAIL).toString());
                txtPhoneNumber.setText(GetUserInfo.get(Constants.User.PHONE).toString());
                //attach settings screen
                setScreen(screen, Settings);
            }
        });
        //Create event listener for change password button
        btnChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear validator
                lblConfirmPasswordValidator.setText("");
                //attach change password screen
                setScreen(screen, ChangePassword);
            }
        });
        //Create event listener for update password button
        btnUpdatePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Input validation
                String result = InputSanitisation.validPin(txtOldPassword.getText());
                String result1 = InputSanitisation.validPin(txtNewPassword.getText());
                String result2 = InputSanitisation.validPin(txtConfirmPassword.getText());
                if (!InputSanitisation.validPin(txtOldPassword.getText()).equals("true")) {
                    lblConfirmPasswordValidator.setText("Old password is invalid. " + result);
                } else if (!InputSanitisation.validPin(txtNewPassword.getText()).equals("true")) {
                    lblConfirmPasswordValidator.setText("New password is invalid. " + result1);
                } else if (!InputSanitisation.validPin(txtConfirmPassword.getText()).equals("true")) {
                    lblConfirmPasswordValidator.setText("Confirm password is invalid. " + result2);
                } else if (txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
                    //send request to server for password change
                    JSONObject jo = client.listen(Constants.User.CHANGE_PIN
                            , Constants.User.OLD_PIN, txtOldPassword.getText()
                            , Constants.User.NEW_PIN, txtNewPassword.getText());
                    if (!jo.toString().equals("Pin changed successfully.")) {
                        lblConfirmPasswordValidator.setText(jo.get(Constants.User.CHANGE_PIN).toString());
                    } else {
                        lblConfirmPasswordValidator.setText(jo.get(Constants.User.CHANGE_PIN).toString());
                    }
                } else {
                    lblConfirmPasswordValidator.setText("Ensure that new password and confirm password entered are the same");
                }
                //clear textbox
                txtOldPassword.setText("");
                txtNewPassword.setText("");
                txtConfirmPassword.setText("");
            }
        });
        //Create event listener for save changes button
        btnSaveChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if phone number entered is not integer (incorrect format)
                try {
                    Integer.parseInt(txtPhoneNumber.getText());
                    //Input Validation
                    //validate phone
                    String phoneResult = InputSanitisation.validPhone(txtPhoneNumber.getText());
                    // validate email
                    String emailResult = InputSanitisation.validEmail(txtEmailAddress.getText());
                    if (phoneResult.equals("true") && emailResult.equals("true"))
                    {
                        //send request to update particulars
                        JSONObject jo = client.listen(Constants.User.UPDATE_USER, Constants.User.USERNAME, lblUserName.getText(), Constants.User.EMAIL, txtEmailAddress.getText(), Constants.User.PHONE, txtPhoneNumber.getText());
                        //display updated message if changes found
                        lblChangeSuccessful.setText("Particulars updated!");
                    }
                    else
                    {
                        if (!emailResult.equals("true")) {
                            lblChangeSuccessful.setText(emailResult);
                        } else if (!phoneResult.equals("true")) {
                            lblChangeSuccessful.setText(phoneResult);
                        }
                    }
                } catch (NumberFormatException ex) {
                    //log
                    LogHelper.log(Level.SEVERE, "Phone number must be an integer.", ex);
                    //validator label
                    if (language[0].equals("eng")) {
                        lblChangeSuccessful.setText("Phone number must be an integer.");
                    } else {
                        lblChangeSuccessful.setText("电话号码必须是整数.");
                    }
                }
            }
        });
        //Create event listener for confirm withdrawal button
        btnConfirmWithdrawal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if amount entered is not integer (incorrect format)
                try {
                    //input validation to check if input withdrawal amount is higher than current balance
                    if (Double.parseDouble(lblWithdrawAccountBalance.getText().substring(1)) < Double.parseDouble(txtWithdrawalAmount.getText())) {
                        //display error message
                        lblWithdrawalAmountValidator.setText("Amount entered must not exceed current account balance");
                    }
                    else if (Double.parseDouble(txtWithdrawalAmount.getText()) > Double.parseDouble(lblTransactionLimitAmountWithdrawal.getText().substring(1)))
                    {
                        //display error message
                        lblWithdrawalAmountValidator.setText("Amount entered must not exceed current account limit");
                    }
                    else
                    {
                        Integer.parseInt(txtWithdrawalAmount.getText());
                        //set transaction note to withdrawal if left empty
                        String note = "Withdrawal";
                        if (!txtWithdrawalNote.getText().equals("")) {
                            note = txtWithdrawalNote.getText();
                        }
                        //create new transaction entry
                        JSONObject jo = client.listen(Constants.Transaction.WITHDRAW, Constants.Transaction.AMOUNT, txtWithdrawalAmount.getText(), Constants.Transaction.TRANSACTION_NOTE, note);

                        //update lblWithdrawAccountBalance with updated account balance
                        lblWithdrawAccountBalance.setText("$" + jo.get(Constants.Account.GET_ACCOUNT_BALANCE).toString());
                        //clear input fields
                        txtWithdrawalNote.setText("");
                        txtWithdrawalAmount.setText("");
                        //clear validator
                        lblWithdrawalAmountValidator.setText("");
                    }
                } catch (NumberFormatException ex) {
                    //log
                    LogHelper.log(Level.SEVERE, "Withdrawal amount must be in integer.", ex);
                    //validator label
                    if (language[0].equals("eng")) {
                        lblWithdrawalAmountValidator.setText("Withdrawal amount must be in integer.");
                    } else {
                        lblWithdrawalAmountValidator.setText("取款金额必须为整数.");
                    }
                }
            }
        });
        //Create event listener for language button
        btnChangeLang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //attach change language screen
                setScreen(base, changeLanguage);
            }
        });
        //set language to chinese
        btnChinese.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                language[0] = "chi";
                setLanguage(Chinese);
                setScreen(base, login);
            }
        });
        //set language to english
        btnEnglish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                language[0] = "eng";
                setLanguage(English);
                setScreen(base, login);
            }
        });
        //go to set transaction limit page
        btnTransactionLimit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Send request to server to get transaction limit for current user
                JSONObject jo = client.listen(Constants.Account.GET_TRANSACTION_LIMIT);
                //populate label with data
                lblCurrentTransactionLimitAmount.setText("$"+jo.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                //attach transaction limit screen
                setScreen(screen, TransactionLimit);
            }
        });
        //go to create new user page
        btnNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //populate create new user dropdownlist
                ddlNewUserAccount.addItem("savings");
                ddlNewUserAccount.addItem("current");
                //attach create new user screen
                setScreen(base, NewUser);
            }
        });
        //submit create new user request
        btnCreateNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //send request to create new user to server
                String username = txtNewUsername.getText();
                String password = txtNewUserPassword.getText();
                String email = txtNewUserEmail.getText();
                String number = txtNewUserPhoneNumber.getText();
                //Input Validation
                String UsernameResult = InputSanitisation.validNameString(username);
                String PasswordResult = InputSanitisation.validPin(password);
                String emailResult = InputSanitisation.validEmail(email);
                String numberResult = InputSanitisation.validPhone(number);
                if (PasswordResult.equals("true") && emailResult.equals("true") && UsernameResult.equals("true") && numberResult.equals("true")) {
                    JSONObject jo = client.listen(Constants.User.CREATE_USER
                            , Constants.User.USERNAME, txtNewUsername.getText()
                            , Constants.User.PASSWORD, txtNewUserPassword.getText()
                            , Constants.User.EMAIL, txtNewUserEmail.getText()
                            , Constants.User.PHONE, txtNewUserPhoneNumber.getText().equals("") ? "0" : txtNewUserPhoneNumber.getText()
                            , Constants.Account.CREATE_ACCOUNT, ddlNewUserAccount.getSelectedIndex());
                    //clear input
                    txtNewUsername.setText("");
                    txtNewUserPassword.setText("");
                    txtNewUserEmail.setText("");
                    txtNewUserPhoneNumber.setText("");
                    //attach login screen
                    setScreen(base,login);
                } else {
                    if (!UsernameResult.equals("true")) {
                        lblNewUserValidator.setText(UsernameResult);
                    }
                    else if (!PasswordResult.equals("true")) {
                        lblNewUserValidator.setText(PasswordResult);
                    }
                    else if (!emailResult.equals("true")) {
                        lblNewUserValidator.setText(emailResult);
                    }
                    else if (!numberResult.equals("true")) {
                        lblNewUserValidator.setText(numberResult);
                    }
                }
            }
        });
        //back to login screen
        btnNewUserBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setScreen(base, login);
            }
        });
        btnConfirmDeposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //catch if amount entered is not integer (incorrect format)
                try {
                    Integer.parseInt(txtDepositAmount.getText());
                    //create new transaction entry
                    //set transaction note to deposit if left empty
                    String note = "Deposit";
                    if (!txtDepositNote.getText().equals("")) {
                        note = txtDepositNote.getText();
                    }
                    //create new transaction entry
                    JSONObject jo = client.listen(Constants.Transaction.DEPOSIT, Constants.Transaction.AMOUNT, txtDepositAmount.getText(), Constants.Transaction.TRANSACTION_NOTE, note);

                    //update lblDepositAccountbalance with updated account balance
                    lblDepositAccountbalance.setText("$" + jo.get(Constants.Account.GET_ACCOUNT_BALANCE).toString());
                    //clear input fields
                    txtDepositNote.setText("");
                    txtDepositAmount.setText("");


                } catch (NumberFormatException ex) {
                    //log
                    LogHelper.log(Level.SEVERE, "Deposit amount must be in integer.", ex);
                    //validator label
                    if (language[0].equals("eng")) {
                        lblWithdrawalAmountValidator.setText("Deposit amount must be in integer.");
                    } else {
                        lblWithdrawalAmountValidator.setText("入金金额必须为整数.");
                    }
                }

            }
        });
        btnTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //catch if amount entered is not integer (incorrect format)
                try {
                    //input validation to check if input transfer amount is higher than current balance
                    if (Double.parseDouble(lblTransferAccountBalance.getText().substring(1)) < Double.parseDouble(txtTransferAmount.getText())) {
                        lblTransferAmountValidator.setText("Amount entered must not exceed current account balance");
                    }
                    else if (Double.parseDouble(txtTransferAmount.getText()) > Double.parseDouble(lblTransactionLimitAmmountTransfer.getText().substring(1)))
                    {
                        //display error message
                        lblTransferAmountValidator.setText("Amount entered must not exceed current account limit");
                    }
                    else
                    {
                        Integer.parseInt(txtTransferAmount.getText());
                        //create new transaction entry
                        JSONObject transfer = new JSONObject(client.listen(Constants.Transaction.TRANSFER
                                , Constants.Transaction.AMOUNT, txtTransferAmount.getText()
                                , Constants.Transaction.TRANSACTION_NOTE, "Transfer"
                                , Constants.Transaction.PAYEE, txtTransferToAccount.getText()));
                        //update lblTransferAccountBalance with updated account balance
                        String choice = "";
                        if(ddlTransactionAccounts.getItemCount() != 0)
                        {
                            choice = ddlTransactionAccounts.getItemAt(ddlTransactionAccounts.getSelectedIndex()).toString();
                            JSONObject jo = client.listen(Constants.Account.GET_ACCOUNT_BALANCE, Constants.Account.ACCOUNT_ID, choice);
                            lblTransferAccountBalance.setText("$" + jo.get(Constants.Account.GET_ACCOUNT_BALANCE).toString());
                        }
                        //clear input fields
                        txtTransferToAccount.setText("");
                        txtTransferAmount.setText("");
                    }
                } catch (NumberFormatException ex) {
                    //log
                    LogHelper.log(Level.SEVERE, "Transfer amount must be in integer.", ex);
                    //validator label
                    if (language[0].equals("eng")) {
                        lblTransferAmountValidator.setText("Transfer amount must be in integer.");
                    } else {
                        lblTransferAmountValidator.setText("转账金额必须为整数.");
                    }
                }
            }
        });
        //add action listener for withdrawal dropdownlist
        ddlWithdrawAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = "";
                if(ddlWithdrawAccounts.getItemCount() != 0)
                {
                    choice = ddlWithdrawAccounts.getItemAt(ddlWithdrawAccounts.getSelectedIndex()).toString();
                    //send select account request to server
                    JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT, Constants.Account.SELECTED_ACCOUNT, ddlWithdrawAccounts.getSelectedIndex()));
                    //Onclick, send request to server to get balance of accountId selected
                    JSONObject jo = client.listen(Constants.Account.GET_ACCOUNT_BALANCE, Constants.Account.ACCOUNT_ID, choice);
                    //update balance label to display updated balance amount
                    lblWithdrawAccountBalance.setText("$" + jo.get(Constants.Account.GET_ACCOUNT_BALANCE).toString());
                    //get limit for account
                    //send select account request to server
                    JSONObject jo2 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT
                            , Constants.Account.SELECTED_ACCOUNT,
                            ddlWithdrawAccounts.getSelectedIndex()));
                    JSONObject jo4 = client.listen(Constants.Account.GET_TRANSACTION_LIMIT);
                    //populate label with data
                    lblTransactionLimitAmountWithdrawal.setText("$"+jo4.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                }

            }
        });
        //add action listener for transaction history dropdownlist
        ddlTransactionAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = "";
                if(ddlTransactionAccounts.getItemCount() != 0)
                {
                    choice = ddlTransactionAccounts.getItemAt(ddlTransactionAccounts.getSelectedIndex()).toString();
                    //send select acc request to server
                    client.listen(Constants.Account.SELECT_ACCOUNT, Constants.Account.SELECTED_ACCOUNT, ddlTransactionAccounts.getSelectedIndex());
                    //Onclick, send request to server to get transaction history of accountId selected
                    JSONObject jo = client.listen(Constants.Account.TRANSACTION_HISTORY, Constants.Account.ACCOUNT_ID, choice);
                    //populate date to screen
                    JSONArray ja = new JSONArray(jo.get(Constants.Account.TRANSACTION_HISTORY).toString());
                    //clear text area
                    AllTransactions.setText("");
                    ja.forEach(record -> {
                        JSONObject joo = new JSONObject(record.toString());
                        //append new transactions to text area
                        AllTransactions.append("  Transaction Note: " + joo.get(Constants.Transaction.TRANSACTION_NOTE) + "\n");
                        AllTransactions.append("  Amount: $" + joo.get(Constants.Transaction.AMOUNT) + "\n");
                        AllTransactions.append("  Transaction Date: " + joo.get(Constants.Transaction.DATE) + "\n");
                        AllTransactions.append("  Transaction Time: " + joo.get(Constants.Transaction.TIME_STAMP) + "\n");
                        AllTransactions.append("\n\n");
                    });
                }

                //set scroller to focus to top
                AllTransactions.setCaretPosition(0);
            }
        });
        //add action listener for deposit dropdownlist
        ddlDepositAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = "";
                if(ddlDepositAccounts.getItemCount() != 0)
                {
                    choice = ddlDepositAccounts.getItemAt(ddlDepositAccounts.getSelectedIndex()).toString();
                    //send select account request to server
                    JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT
                            , Constants.Account.SELECTED_ACCOUNT, ddlDepositAccounts.getSelectedIndex()));
                    //Onclick, send request to server to get balance of accountId selected
                    JSONObject jo = client.listen(Constants.Account.GET_ACCOUNT_BALANCE, Constants.Account.ACCOUNT_ID, choice);
                    //update balance label to display updated balance amount
                    lblDepositAccountbalance.setText("$" + jo.get(Constants.Account.GET_ACCOUNT_BALANCE).toString());
                }

            }
        });
        //add event listener for forget password
        btnForgetPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtUsername.getText().equals("")) {
                    lblLoginValidator.setText("Enter Username to change password!");
                } else {
                    //send request to server to change password
                    JSONObject jo = client.listen(Constants.User.FORGET_PIN, Constants.User.USERNAME, txtUsername.getText());
                }
            }
        });
        ddlTransferAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = "";
                if(ddlTransferAccounts.getItemCount() != 0)
                {
                    choice = ddlTransferAccounts.getItemAt(ddlTransferAccounts.getSelectedIndex()).toString();
                    //send select account request to server
                    JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT
                            , Constants.Account.SELECTED_ACCOUNT, ddlTransferAccounts.getSelectedIndex()));
                    //Onclick, send request to server to get balance of accountId selected
                    JSONObject jo = client.listen(Constants.Account.GET_ACCOUNT_BALANCE, Constants.Account.ACCOUNT_ID, choice);
                    //update balance label to display updated balance amount
                    lblTransferAccountBalance.setText("$" + jo.get(Constants.Account.GET_ACCOUNT_BALANCE).toString());
                    //get limit for account
                    //send select account request to server
                    JSONObject jo2 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT
                            , Constants.Account.SELECTED_ACCOUNT,
                            ddlTransferAccounts.getSelectedIndex()));
                    JSONObject jo4 = client.listen(Constants.Account.GET_TRANSACTION_LIMIT);
                    //populate label with data
                    lblTransactionLimitAmmountTransfer.setText("$"+jo4.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                }

            }
        });
        btnUpdateTransactionLimit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = "";
                if(ddlTransactionLimitAccounts.getItemCount() != 0)
                {
                    choice = ddlTransactionLimitAccounts.getItemAt(ddlTransactionLimitAccounts.getSelectedIndex()).toString();
                    //send select account request to server
                    JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT
                            , Constants.Account.SELECTED_ACCOUNT,
                            ddlTransactionLimitAccounts.getSelectedIndex()));
                    if(!txtNewTransactionLimitAmount.equals(""))
                    {
                        //send request to server to update transaction limit for current user
                        JSONObject jo = client.listen(Constants.Account.CHANGE_TRANSACTION_LIMIT,
                                Constants.Account.CHANGE_TRANSACTION_LIMIT, txtNewTransactionLimitAmount.getText(),
                                Constants.Account.SELECTED_ACCOUNT, Integer.toString(ddlTransactionLimitAccounts.getSelectedIndex()));
                        //get updated transaction limit
                        JSONObject jo2 = client.listen(Constants.Account.GET_TRANSACTION_LIMIT);
                        //update label with data
                        lblCurrentTransactionLimitAmount.setText("$"+jo2.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                        lblTransactionLimitAmmountTransfer.setText("$"+jo2.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                        lblTransactionLimitAmountWithdrawal.setText("$"+jo2.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                        //clear input
                        txtNewTransactionLimitAmount.setText("");
                    }
                }

            }
        });
        btnViewAccountSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //populate account summary data
                JSONObject jo = client.listen(Constants.Account.GET_ACCOUNT_SUMMARY);
                JSONArray ja = new JSONArray(jo.get(Constants.Account.GET_ACCOUNT_SUMMARY).toString());
                //clear text area
                AccountSummary.setText("");
                ja.forEach(record -> {
                    JSONObject joo = new JSONObject(record.toString());
                    //append account summary to textarea
                    AccountSummary.append("Account type: " + joo.get(Constants.Account.ACCOUNT_TYPE) + "\n");
                    AccountSummary.append("Account ID: " + joo.get(Constants.Account.ACCOUNT_ID) + "\n");
                    AccountSummary.append("Balance: $" + joo.get(Constants.Account.BALANCE) + "\n");
                });
                //set scroller to focus to top
                AccountSummary.setCaretPosition(0);
                //attach account summary screen
                setScreen(screen, ViewAccountSummary);
            }
        });
        btnOpenNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear validator
                lblNewAccountCreationValidator.setText("");
                //attach open new account screen
                setScreen(screen, OpenNewAccount);
            }
        });
        btnOpenAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ddlOpenNewAccount.getItemCount()!=0)
                {
                    //send request to open account for current user
                    JSONObject jo = client.listen(Constants.Account.CREATE_ACCOUNT
                            , Constants.Account.CREATE_ACCOUNT, ddlOpenNewAccount.getSelectedIndex());
                    lblNewAccountCreationValidator.setText("Account Opened Successfully!");
                    //Send request to server to get all accounts of current users
                    //update dropdownlists
                    JSONObject retrieveAccounts = client.listen(Constants.Account.ALL_ACCOUNTS);
                    //clear and reload ddl
                    ddlWithdrawAccounts.removeAllItems();
                    ddlDepositAccounts.removeAllItems();
                    ddlTransferAccounts.removeAllItems();
                    ddlTransactionAccounts.removeAllItems();
                    ddlTransactionLimitAccounts.removeAllItems();
                    JSONArray ja2 = new JSONArray(retrieveAccounts.get(Constants.Account.ALL_ACCOUNTS).toString());
                    ja2.forEach(record -> {
                        JSONObject joo2 = new JSONObject(record.toString());
                        //populate dropdownlists with user accounts
                        ddlWithdrawAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                        ddlDepositAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                        ddlTransferAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                        ddlTransactionAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                        ddlTransactionLimitAccounts.addItem(joo2.get(Constants.Account.ACCOUNT_TYPE) + " : " + joo2.get(Constants.Account.ACCOUNT_ID));
                    });
                }

            }
        });
        ddlTransactionLimitAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = "";
                if(ddlTransactionLimitAccounts.getItemCount() != 0)
                {
                    choice = ddlTransactionLimitAccounts.getItemAt(ddlTransactionLimitAccounts.getSelectedIndex()).toString();
                    //send select account request to server
                    JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SELECT_ACCOUNT, Constants.Account.SELECTED_ACCOUNT, ddlTransactionLimitAccounts.getSelectedIndex()));
                    //Onclick, send request to server to get transfer limit of accountId selected
                    JSONObject jo = client.listen(Constants.Account.GET_TRANSACTION_LIMIT, Constants.Account.ACCOUNT_ID, choice);
                    //update balance label to display updated balance amount
                    lblCurrentTransactionLimitAmount.setText("$" + jo.get(Constants.Account.GET_TRANSACTION_LIMIT).toString());
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {

    }

    //setLanguage for all buttons and labels
    protected void setLanguage(Map<String, String> language) {
        //login
        lblUser.setText(language.get("0"));
        lblPass.setText(language.get("1"));
        btnLogin.setText(language.get("2"));
        btnForgetPassword.setText(language.get("46"));
        btnChangeLang.setText(language.get("3"));
        //main menu
        btnBack.setText(language.get("4"));
        btnLogout.setText(language.get("5"));
        lblWelcomeMessage.setText(language.get("6"));
        btnViewTransactions.setText(language.get("7"));
        btnWithdrawal.setText(language.get("8"));
        btnDeposit.setText(language.get("9"));
        btnBankTransfer.setText(language.get("10"));
        btnSettings.setText(language.get("11"));
        //view transaction
        lblViewTransaction.setText(language.get("12"));
        //withdrawal
        lblWithdraw.setText(language.get("13"));
        lblWithdrawAccount.setText(language.get("14"));
        lblWithdrawalNote.setText(language.get("15"));
        lblWithdrawalBalance.setText(language.get("16"));
        lblWithdrawalAmount.setText(language.get("17"));
        btnConfirmWithdrawal.setText(language.get("18"));
        //deposit
        lblDeposit.setText(language.get("19"));
        lblDepositAccount.setText(language.get("20"));
        lblDepositNote.setText(language.get("21"));
        lblDepositBalance.setText(language.get("22"));
        lblDepositAmount.setText(language.get("23"));
        btnConfirmDeposit.setText(language.get("24"));
        //bank transfer
        lblBankTransfer.setText(language.get("25"));
        lblTransferAccount.setText(language.get("26"));
        lblTransferBalance.setText(language.get("27"));
        lblTransferToAccount.setText(language.get("28"));
        lblTransferAmount.setText(language.get("29"));
        btnTransfer.setText(language.get("30"));
        //settings
        lblsetting.setText(language.get("31"));
        lblsUser.setText(language.get("32"));
        btnChangePassword.setText(language.get("33"));
        lblEmail.setText(language.get("34"));
        lblphoneNumber.setText(language.get("35"));
        btnSaveChanges.setText(language.get("36"));
        //change password
        lbloldPassword.setText(language.get("37"));
        lblnewPassword.setText(language.get("38"));
        lblconfirmPassword.setText(language.get("39"));
        btnUpdatePassword.setText(language.get("40"));
        //change language
        lblchooseLang.setText(language.get("41"));
        btnEnglish.setText(language.get("42"));
        btnChinese.setText(language.get("43"));
        //create new user
        btnNewUser.setText(language.get("44"));
        lblCreateNewUser.setText(language.get("44"));
        lblNewUsername.setText(language.get("0"));
        lblNewUserpw.setText(language.get("1"));
        btnNewUserBack.setText(language.get("4"));
        btnCreateNewUser.setText(language.get("44"));
        lblNewUserEmail.setText(language.get("34"));
        lblNewUserPhoneNumber.setText(language.get("35"));
        lblNewUserAccount.setText(language.get("26"));
        //view transaction limit
        btnTransactionLimit.setText(language.get("45"));
        lblTransactionLimit.setText(language.get("45"));
        lblCurrentTransactionLimit.setText(language.get("47"));
        lblNewTransactionLimit.setText(language.get("48"));
        btnUpdateTransactionLimit.setText(language.get("49"));
        //transaction lbl
        lblTransactionLimitTransfer.setText(language.get("47"));

        lblTransactionLimitWithdrawal.setText(language.get("47"));
        //view account summary
        btnViewAccountSummary.setText(language.get("50"));
        lblViewAccountSummary.setText(language.get("50"));
        btnOpenNewAccount.setText(language.get("51"));
        lblOpenNewAccount.setText(language.get("51"));
        btnOpenAccount.setText(language.get("52"));
    }

    public JPanel getBase() {
        return base;
    }

    protected void setScreen(JPanel cardLayoutBase, JPanel screenName) {
        //attach screen
        cardLayoutBase.removeAll();
        cardLayoutBase.add(screenName);
        cardLayoutBase.repaint();
        cardLayoutBase.revalidate();
    }
}
