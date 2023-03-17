package pure.ui;

import pure.constants.Constants;
import pure.client.Client;
import pure.util.InputSanitisation;
import pure.util.JSON;
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
    private JTextField txtUserName;
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
            entry("45", "View Account Summary"),
            entry("46", "Forget Password")
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
            entry("45", "查看帐户摘要"),
            entry("46", "忘记密码")
    );

    public GUI(Client client) {
        final String[] language = new String[]{"eng"};

        //Create event listener for login button
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Input validation
                String pinValid = InputSanitisation.validPin(txtPassword.getText());
                //System.out.println(pinValid);
                String nameValid = InputSanitisation.validNameString(txtUsername.getText());
                //System.out.println(nameValid);
                //if input pass validation
                if (pinValid.equals("true") && nameValid.equals("true")) {
                    //test account username: test, pw:123123
                    //init json object to store replies from server
                    JSONObject jo = JSON.tryParse(client.listen(Constants.User.Login, Constants.User.Password, txtPassword.getText(), Constants.User.Username, txtUsername.getText()));

                    //Get user input from textbox and on successful login, show main menu
                    if (jo.get(Constants.User.LoginStatus).toString().equalsIgnoreCase("true")) {
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
                        JSONObject SelectAccount = JSON.tryParse(client.listen(Constants.Account.SelectAccount, Constants.Account.SelectedAccount, 0));

                        //populate dropdownlists with data
                        //Send request to server to get all accounts of current users
                        //Retrieve All User Accounts
                        JSONObject retrieveAccounts = JSON.tryParse(client.listen(Constants.Account.AllAccounts));
                        JSONArray ja2 = new JSONArray(retrieveAccounts.get(Constants.Account.AllAccounts).toString());
                        ja2.forEach(record -> {
                            JSONObject joo2 = new JSONObject(record.toString());
                            /*System.out.print(Constants.Account.AccountId + " : " + joo2.get(Constants.Account.AccountId) + "\t");
                            System.out.print(Constants.Account.AccountType + " : " + joo2.get(Constants.Account.AccountType) + "\t");
                            System.out.print(Constants.Account.UserID + " : " + joo2.get(Constants.Account.UserID) + "\n");*/

                            //populate dropdownlists with user accounts
                            ddlWithdrawAccounts.addItem(joo2.get(Constants.Account.AccountType) + " : " + joo2.get(Constants.Account.AccountId));
                            ddlDepositAccounts.addItem(joo2.get(Constants.Account.AccountType) + " : " + joo2.get(Constants.Account.AccountId));
                            ddlTransferAccounts.addItem(joo2.get(Constants.Account.AccountType) + " : " + joo2.get(Constants.Account.AccountId));
                            ddlTransactionAccounts.addItem(joo2.get(Constants.Account.AccountType) + " : " + joo2.get(Constants.Account.AccountId));
                        });
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
                JSONObject logout = JSON.tryParse(client.listen(Constants.User.Logout));
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
                //attach view transaction screen
                setScreen(screen, ViewTransactions);
            }
        });
        //Create event listener for withdrawal button
        btnWithdrawal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //attach withdrawal screen
                setScreen(screen, Withdrawal);
                String choice = ddlWithdrawAccounts.getItemAt(ddlWithdrawAccounts.getSelectedIndex()).toString();
                //send select account request to server
                new JSONObject(client.listen(Constants.Account.SelectAccount, Constants.Account.SelectedAccount, String.valueOf(ddlWithdrawAccounts.getSelectedIndex())));
                //Onclick, send request to server to get balance of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(Constants.Account.GetAccountBalance, Constants.Account.AccountId, choice));
                //update balance label to display updated balance amount
                lblWithdrawAccountBalance.setText("$" + jo.get(Constants.Account.GetAccountBalance).toString());
            }
        });
        //Create event listener for deposit button
        btnDeposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //attach deposit screen
                setScreen(screen, Deposit);
                String choice = ddlDepositAccounts.getItemAt(ddlDepositAccounts.getSelectedIndex()).toString();
                //send select account request to server
                new JSONObject(client.listen(Constants.Account.SelectAccount, Constants.Account.SelectedAccount, ddlDepositAccounts.getSelectedIndex()));
                //Onclick, send request to server to get balance of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(Constants.Account.GetAccountBalance, Constants.Account.AccountId, choice));
                //update balance label to display updated balance amount
                lblDepositAccountbalance.setText("$" + jo.get(Constants.Account.GetAccountBalance).toString());
            }
        });
        //Create event listener for bank transfer button
        btnBankTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //attach bank transfer screen
                setScreen(screen, BankTransfer);
                String choice = ddlTransactionAccounts.getItemAt(ddlTransactionAccounts.getSelectedIndex()).toString();
                //send select account request to server
                new JSONObject(client.listen(Constants.Account.SelectAccount, Constants.Account.SelectedAccount, ddlTransactionAccounts.getSelectedIndex()));
                //Onclick, send request to server to get balance of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(Constants.Account.GetAccountBalance, Constants.Account.AccountId, choice));
                //update balance label to display updated balance amount
                lblTransferAccountBalance.setText("$" + jo.get(Constants.Account.GetAccountBalance).toString());
            }
        });
        //Create event listener for settings button
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //send Get User Info request
                JSONObject GetUserInfo = JSON.tryParse(client.listen(Constants.User.GetUserInformation));
                //populate fields with current user data
                txtUserName.setText(GetUserInfo.get(Constants.User.Username).toString());
                txtEmailAddress.setText(GetUserInfo.get(Constants.User.Email).toString());
                txtPhoneNumber.setText(GetUserInfo.get(Constants.User.Phone).toString());
                //attach settings screen
                setScreen(screen, Settings);
            }
        });
        //Create event listener for change password button
        btnChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    JSONObject jo = JSON.tryParse(client.listen(Constants.User.ChangePin
                            , Constants.User.oldPin, txtOldPassword.getText()
                            , Constants.User.newPin, txtNewPassword.getText()));
                    if (jo.toString().equals(null)) {
                        lblConfirmPasswordValidator.setText("Password change failed");
                    } else {
                        lblConfirmPasswordValidator.setText("Password changed successfully");
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
                    //send request to update particulars
                    JSONObject jo = JSON.tryParse(client.listen(Constants.User.UpdateUser, Constants.User.Username, txtUserName.getText(), Constants.User.Email, txtEmailAddress.getText(), Constants.User.Phone, txtPhoneNumber.getText()));
                    //display updated message if changes found
                    lblChangeSuccessful.setText("Particulars updated!");
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
                    } else {
                        Integer.parseInt(txtWithdrawalAmount.getText());
                        //set transaction note to withdrawal if left empty
                        String note = "Withdrawal";
                        if (!txtWithdrawalNote.getText().equals("")) {
                            note = txtWithdrawalNote.getText();
                        }
                        //create new transaction entry
                        JSONObject jo = JSON.tryParse(client.listen(Constants.Transaction.Withdraw, Constants.Transaction.Amount, txtWithdrawalAmount.getText(), Constants.Transaction.TransactionNote, note));

                        //update lblWithdrawAccountBalance with updated account balance
                        lblWithdrawAccountBalance.setText("$" + jo.get(Constants.Account.GetAccountBalance).toString());
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
                JSONObject jo = JSON.tryParse(client.listen(Constants.Account.GetTransactionLimit));
                //populate label with data
                lblCurrentTransactionLimitAmount.setText("$"+jo.get(Constants.Account.GetTransactionLimit).toString());
                //attach view account summary screen
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

                //Input Validation
                String result = InputSanitisation.validPin(password);
                //if (!result.equals("valid")) {
                //    lblNewUserValidator.setText(result);
                //    return;
                //}

                // validate email
                String emailResult = InputSanitisation.validEmail(email);
                //if (!emailResult.equals("valid")) {
                //    lblNewUserValidator.setText(emailResult);
                //    return;
                //}

                if (result.equals("true") && emailResult.equals("true")) {
                    JSONObject jo = JSON.tryParse(client.listen(Constants.User.CreateUser
                            , Constants.User.Username, txtNewUsername.getText()
                            , Constants.User.Password, txtNewUserPassword.getText()
                            , Constants.User.Email, txtNewUserEmail.getText()
                            , Constants.User.Phone, txtNewUserPhoneNumber.getText()
                            , Constants.User.Accounts, /*ddlNewUserAccount.getItemAt(ddlNewUserAccount.getSelectedIndex()))*/ddlNewUserAccount.getSelectedIndex()));
                    lblNewUserValidator.setText("User created successfully");
                    //attach login screen
                    //setScreen(base,login);
                } else {
                    if (!result.equals("true")) {
                        lblNewUserValidator.setText(result);
                    } else if (!emailResult.equals("true")) {
                        lblNewUserValidator.setText(emailResult);
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
                    //input validation to check if input deposit amount is higher than current balance
                    if (Double.parseDouble(lblDepositAccountbalance.getText().substring(1)) < Double.parseDouble(txtDepositAmount.getText())) {
                        lblDepositAmountValidator.setText("Amount entered must not exceed current account balance");
                    } else {
                        Integer.parseInt(txtDepositAmount.getText());
                        //create new transaction entry
                        //set transaction note to deposit if left empty
                        String note = "Deposit";
                        if (!txtDepositNote.getText().equals("")) {
                            note = txtDepositNote.getText();
                        }
                        //create new transaction entry
                        JSONObject jo = JSON.tryParse(client.listen(Constants.Transaction.Deposit, Constants.Transaction.Amount, txtDepositAmount.getText(), Constants.Transaction.TransactionNote, note));

                        //update lblDepositAccountbalance with updated account balance
                        lblDepositAccountbalance.setText("$" + jo.get(Constants.Account.GetAccountBalance).toString());
                    }


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
                    } else {
                        Integer.parseInt(txtTransferAmount.getText());
                        //create new transaction entry
                        JSONObject transfer = new JSONObject(client.listen(Constants.Transaction.Transfer
                                , Constants.Transaction.Amount, txtTransferAmount.getText()
                                , Constants.Transaction.TransactionNote, "Transfer"
                                , Constants.Transaction.Payee, txtTransferToAccount.getText()));
                        //update lblTransferAccountBalance with updated account balance
                        lblTransferAccountBalance.setText("$" + transfer.get(Constants.Account.GetAccountBalance).toString());
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
                String choice = ddlWithdrawAccounts.getItemAt(ddlWithdrawAccounts.getSelectedIndex()).toString();
                //send select account request to server
                JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SelectAccount, Constants.Account.SelectedAccount, ddlWithdrawAccounts.getSelectedIndex()));
                //Onclick, send request to server to get balance of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(Constants.Account.GetAccountBalance, Constants.Account.AccountId, choice));
                //update balance label to display updated balance amount
                lblWithdrawAccountBalance.setText("$" + jo.get(Constants.Account.GetAccountBalance).toString());
            }
        });
        //add action listener for transaction history dropdownlist
        ddlTransactionAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = ddlTransactionAccounts.getItemAt(ddlTransactionAccounts.getSelectedIndex()).toString();
                //send select acc request to server
                JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SelectAccount, Constants.Account.SelectedAccount, ddlTransactionAccounts.getSelectedIndex()));
                //Onclick, send request to server to get transaction history of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(Constants.Account.TransactionHistory, Constants.Account.AccountId, choice));
                //populate date to screen
                JSONArray ja = new JSONArray(jo.get(Constants.Account.TransactionHistory).toString());
                //clear text area
                AllTransactions.setText("");
                ja.forEach(record -> {
                    JSONObject joo = new JSONObject(record.toString());
                    //debug
                    System.out.print(Constants.Transaction.TransactionNote + " : " + joo.get(Constants.Transaction.TransactionNote) + "\t");
                    System.out.print(Constants.Transaction.Amount + " : " + joo.get(Constants.Transaction.Amount) + "\t");
                    System.out.print(Constants.Transaction.date + " : " + joo.get(Constants.Transaction.date) + "\n");
                    System.out.print(Constants.Transaction.TimeStamp + " : " + joo.get(Constants.Transaction.TimeStamp) + "\n");

                    //append new transactions to text area
                    AllTransactions.append("  Transaction Note: " + joo.get(Constants.Transaction.TransactionNote) + "\n");
                    AllTransactions.append("  Amount: $" + joo.get(Constants.Transaction.Amount) + "\n");
                    AllTransactions.append("  Transaction Date: " + joo.get(Constants.Transaction.date) + "\n");
                    AllTransactions.append("  Transaction Time: " + joo.get(Constants.Transaction.TimeStamp) + "\n");
                    AllTransactions.append("\n\n");
                });
                //set scroller to focus to top
                AllTransactions.setCaretPosition(0);
            }
        });
        //add action listener for deposit dropdownlist
        ddlDepositAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = ddlDepositAccounts.getItemAt(ddlDepositAccounts.getSelectedIndex()).toString();
                //send select account request to server
                JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SelectAccount
                        , Constants.Account.SelectedAccount, ddlDepositAccounts.getSelectedIndex()));
                //Onclick, send request to server to get balance of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(Constants.Account.GetAccountBalance, Constants.Account.AccountId, choice));
                //update balance label to display updated balance amount
                lblDepositAccountbalance.setText("$" + jo.get(Constants.Account.GetAccountBalance).toString());
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
                    JSONObject jo = JSON.tryParse(client.listen(Constants.User.ForgetPin, Constants.User.Username, txtUsername.getText()));
                }
            }
        });
        ddlTransferAccounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = ddlTransferAccounts.getItemAt(ddlTransferAccounts.getSelectedIndex()).toString();
                //send select account request to server
                JSONObject jo1 = new JSONObject(client.listen(Constants.Account.SelectAccount
                        , Constants.Account.SelectedAccount, ddlTransferAccounts.getSelectedIndex()));
                //Onclick, send request to server to get balance of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(Constants.Account.GetAccountBalance, Constants.Account.AccountId, choice));
                //update balance label to display updated balance amount
                lblTransferAccountBalance.setText("$" + jo.get(Constants.Account.GetAccountBalance).toString());
            }
        });
        btnUpdateTransactionLimit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtNewTransactionLimitAmount.equals(""))
                {
                    //send request to server to update transaction limit for current user
                    JSONObject jo = JSON.tryParse(client.listen(Constants.Account.ChangeTransactionLimit,Constants.Account.ChangeTransactionLimit, txtNewTransactionLimitAmount.getText()));
                    //get updated transaction limit
                    JSONObject jo1 = JSON.tryParse(client.listen(Constants.Account.GetTransactionLimit));
                    lblCurrentTransactionLimitAmount.setText("$"+jo1.get(Constants.Account.GetTransactionLimit).toString());
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
        //view account summary
        btnTransactionLimit.setText(language.get("45"));
        lblTransactionLimit.setText(language.get("45"));
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
