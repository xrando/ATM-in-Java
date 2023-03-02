import ATM.Client.Client;
import ATM.Constants.Constants;
import ATM.Utilities.JSON;
import ATM.Utilities.LogHelper;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.logging.Level;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

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
    private JComboBox ddlAccounts;
    private JLabel lblAccountBalance;
    private JTextField txtWithdrawalAmount;
    private JLabel lblWithdrawalAmountValidator;
    private JButton btnConfirmWithdrawal;
    private JComboBox ddlAccount;
    private JTextField txtDepositAmount;
    private JLabel lblAccountbalance;
    private JButton btnConfirmDeposit;
    private JLabel lblWelcomeMessage;
    private JComboBox ddlaccount;
    private JLabel lblAccBalance;
    private JTextField txtTransferTo;
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
    private JLabel lblwithdraw;
    private JLabel lblwAcc;
    private JLabel lblwTnote;
    private JLabel lblwBal;
    private JLabel lblwAmt;
    private JLabel lbldeposit;
    private JLabel lbldAcc;
    private JLabel lbldTnote;
    private JLabel lbldBalance;
    private JLabel lbldAmt;
    private JLabel lblbankTransfer;
    private JLabel lbltAcc;
    private JLabel lbltBalance;
    private JLabel lbltransferAccount;
    private JLabel lbltransferAmount;
    private JLabel lblsetting;
    private JLabel lblsUser;
    private JLabel lblEmail;
    private JLabel lblphoneNumber;
    private JLabel lbloldPassword;
    private JLabel lblnewPassword;
    private JLabel lblconfirmPassword;
    private JLabel lblchooseLang;
    private JButton btnViewAccountSummary;
    private JPanel ViewAccountSummary;
    private JLabel lblViewAccountSummary;
    private JTextArea AccountSummary;
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
    private JComboBox ddlTAccounts;
    private JButton btnForgetPassword;
    private Map<String, String> English = Map.ofEntries(
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
            entry("45", "View Account Summary")
    );

    private Map<String, String> Chinese = Map.ofEntries(
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
            entry("45", "查看帐户摘要")
    );

    public GUI() throws Exception
    {
        final String[] language = new String[]{"eng"};
        //init client object
        Client client = new Client();
        //init json object to store replies from server
        //Create event listener for login button
        btnLogin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //test account username: test, pw:123123
                //init json object to store replies from server
                JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.User.Login).add(Constants.User.Password, txtPassword.getText()).add(Constants.User.Username, txtUsername.getText()).toString()));

                //Get user input from textboxs and on successful login, show main menu
                if (jo.get(Constants.User.LoginStatus).toString().toLowerCase().equals("true"))
                {
                    lblLoginValidator.setText("");
                    if(language[0].equals("eng"))
                    {
                        //set welcome msg
                        lblWelcomeMessage.setText("Welcome " + txtUsername.getText());
                    }
                    else
                    {
                        //set welcome msg
                        lblWelcomeMessage.setText("欢迎 " + txtUsername.getText());
                    }
                    //attach main menu screen
                    setScreen(base,main);

                    //Onlogin
                    //test
                    //send select account request to server
                    //JSONObject jo1 = new JSONObject(client.listen(new JSON(Constants.Account.SelectAccount).add(Constants.Account.SelectedAccount, "0").toString()));


                    //populate dropdownlists with data
                    //Send request to server to get all accounts of current users
                    //JSONObject jo2 = JSON.tryParse(client.listen(new JSON(Constants.Account.AllAccounts).toString()));
                    //populate dropdownlist with accountID
                    /*for(var a:jo2.names())
                    {
                        ddlAccounts.addItem(a.toString());
                        ddlAccount.addItem(a.toString());
                        ddlaccount.addItem(a.toString());
                        ddlTAccounts.addItem(a.toString());
                    }*/


                    //test
                    String [] t = new String[]{"test","test2","test3"};
                    //does work
                    Arrays.stream(t).forEach(s->ddlAccounts.addItem(s));
                    //not working
                    /*for(var a:t)
                    {
                        ddlAccounts.addItem(makeObj(a.toString()));
                        ddlAccount.addItem(makeObj(a.toString()));
                        ddlaccount.addItem(makeObj(a.toString()));
                        ddlTAccounts.addItem(makeObj(a.toString()));
                    }*/
                }
                else
                {
                    lblLoginValidator.setText("Wrong credentials");
                }
            }
        });
        //Create event listener for logout button
        btnLogout.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                txtUsername.setText("");
                txtPassword.setText("");
                //re-attach login screen
                setScreen(base,login);
                //set screen to main
                setScreen(screen,main);
                JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.User.Logout).toString()));
            }
        });
        //Create event listener for back button
        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //re-attach main menu
                setScreen(screen,menu);
            }
        });
        //Create event listener for view transactions button
        btnViewTransactions.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Send request to server to get all accounts for current user
                //JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.Account.AllAccounts).toString()));
                //populate dropdownlist with accounts
                //test
                //ddlTAccounts.addItem("test");
                //Done on login
                //attach view transaction screen
                setScreen(screen,ViewTransactions);



            }
        });
        //Create event listener for withdrawal button
        btnWithdrawal.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //attach withdrawal screen
                setScreen(screen,Withdrawal);
                //TODO: populate accounts ddl with accounts linked with current user
                //Send request to server to get all accounts of current users
                /*JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.Account.AllAccounts).toString()));
                //populate dropdownlist with accountID
                for(var a:jo.names())
                {
                    ddlAccounts.addItem(a.toString());
                }*/
                //done when login
                //test
                /*ddlAccounts.addItem("test");
                ddlAccounts.addItem("example");*/
            }
        });
        //Create event listener for deposit button
        btnDeposit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //attach deposit screen
                setScreen(screen,Deposit);
            }
        });
        //Create event listener for bank transfer button
        btnBankTransfer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //attach bank transfer screen
                setScreen(screen,BankTransfer);
            }
        });
        //Create event listener for settings button
        btnSettings.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //attach settings screen
                setScreen(screen,Settings);
            }
        });
        //Create event listener for change password button
        btnChangePassword.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //attach change password screen
                setScreen(screen,ChangePassword);
            }
        });
        //Create event listener for update password button
        btnUpdatePassword.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(txtNewPassword.getText().equals(txtConfirmPassword.getText()))
                {
                    //send request to server for password change
                    JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.User.ChangePin).add(Constants.User.oldPin, txtOldPassword.getText()).add(Constants.User.newPin, txtNewPassword.getText()).toString()));
                    if (jo.toString().equals(null)) {
                        lblConfirmPasswordValidator.setText("Password change failed");
                    } else {
                        lblConfirmPasswordValidator.setText("Password changed successfully");
                    }
                }
                else
                {
                    lblConfirmPasswordValidator.setText("Ensure that new password and confirm password entered are the same");
                }



                //attach settings screen
                setScreen(screen,Settings);

            }
        });
        //Create event listener for save changes button
        btnSaveChanges.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO: call setter methods to update user objects fields
                //if phone number entered is not integer (incorrect format)
                try
                {
                    Integer.parseInt(txtPhoneNumber.getText());


                    //if changes found to particulars
                    //loop
                    //display updated message if changes found
                    //lblChangeSuccessful.setText("Particulars updated!");

                }
                catch (NumberFormatException ex)
                {
                    //log
                    LogHelper.log(Level.SEVERE, "Phone number must be an integer.", ex);
                    //validator label
                    if(language[0].equals("eng"))
                    {
                        lblChangeSuccessful.setText("Phone number must be an integer.");
                    }
                    else
                    {
                        lblChangeSuccessful.setText("电话号码必须是整数.");
                    }
                }
            }
        });
        //Create event listener for confirm withdrawal button
        btnConfirmWithdrawal.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO: compute account balance after withdrawal
                //if amount entered is not integer (incorrect format)
                try
                {
                    Integer.parseInt(txtWithdrawalAmount.getText());
                    //set transaction note to withdrawal if left empty
                    String note="Withdrawal";
                    if (!txtWithdrawalNote.getText().equals(""))
                    {
                        note = txtWithdrawalNote.getText();
                    }
                    //create new transaction entry
                    JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.Transaction.Withdraw).add(Constants.Transaction.Amount, txtWithdrawalAmount.getText()).add(Constants.Transaction.TransactionNote, note).toString()));

                    //update lblAccountBalance with updated account balance
                    lblAccountBalance.setText(jo.toString());

                }
                catch (NumberFormatException ex)
                {
                    //log
                    LogHelper.log(Level.SEVERE, "Withdrawal amount must be in integer.", ex);
                    //validator label
                    if(language[0].equals("eng"))
                    {
                        lblWithdrawalAmountValidator.setText("Withdrawal amount must be in integer.");
                    }
                    else
                    {
                        lblWithdrawalAmountValidator.setText("取款金额必须为整数.");
                    }
                }

            }
        });
        //Create event listener for language button
        btnChangeLang.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //attach change language screen
                setScreen(base,changeLanguage);
            }
        });
        //set language to chinese
        btnChinese.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                language[0] = "chi";
                setLanguage(Chinese);
                setScreen(base,login);
            }
        });
        //set language to english
        btnEnglish.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                language[0] = "eng";
                setLanguage(English);
                setScreen(base,login);
            }
        });
        //view account summary
        btnViewAccountSummary.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Send request to server to get account summary information for current user
                JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.Account.AllAccountSummary).toString()));
                //populate view account summary screen with data
                AccountSummary.setText(jo.toString());
                //attach view account summary screen
                setScreen(screen,ViewAccountSummary);
            }
        });
        //go to create new user page
        btnNewUser.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //attach create new user screen
                setScreen(base,NewUser);
            }
        });
        //submit create new user request
        btnCreateNewUser.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //send request to create new user to server
                JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.User.CreateUser).add(Constants.User.Username, txtNewUsername.getText()).add(Constants.User.Password, txtNewUserPassword.getText()).toString()));
                //attach login screen and set label to notify successful user creation
                setScreen(base,login);


            }
        });
        //back to login screen
        btnNewUserBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setScreen(base,login);
            }
        });
        btnConfirmDeposit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO: compute account balance after deposit
                //catch if amount entered is not integer (incorrect format)
                try
                {
                    Integer.parseInt(txtDepositAmount.getText());
                    //create new transaction entry
                    //update lblAccountBalance with updated account balance

                }
                catch (NumberFormatException ex)
                {
                    //log
                    LogHelper.log(Level.SEVERE, "Deposit amount must be in integer.", ex);
                    //validator label
                    if(language[0].equals("eng"))
                    {
                        lblWithdrawalAmountValidator.setText("Deposit amount must be in integer.");
                    }
                    else
                    {
                        lblWithdrawalAmountValidator.setText("入金金额必须为整数.");
                    }
                }

            }
        });
        btnTransfer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //catch if amount entered is not integer (incorrect format)
                try
                {
                    Integer.parseInt(txtTransferAmount.getText());
                    //create new transaction entry
                    //update lblAccountBalance with updated account balance

                }
                catch (NumberFormatException ex)
                {
                    //log
                    LogHelper.log(Level.SEVERE, "Transfer amount must be in integer.", ex);
                    //validator label
                    if(language[0].equals("eng"))
                    {
                        lblTransferAmountValidator.setText("Transfer amount must be in integer.");
                    }
                    else
                    {
                        lblTransferAmountValidator.setText("转账金额必须为整数.");
                    }
                }
            }
        });
        //add action listener for withdrawal dropdownlist
        ddlAccounts.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String choice = ddlAccounts.getItemAt(ddlAccounts.getSelectedIndex()).toString();
                System.out.println("index selected: "+ ddlAccounts.getSelectedIndex());
                //send select account request to server
                JSONObject jo1 = new JSONObject(client.listen(new JSON(Constants.Account.SelectAccount).add(Constants.Account.SelectedAccount, ddlAccounts.getSelectedIndex()).toString()));
                //Onclick, send request to server to get balance of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.Account.GetAccountBalance).add(Constants.Account.AccountId, choice).toString()));
                //update balance label to display updated balance amount
                lblAccountBalance.setText(jo.toString());
            }
        });
        //add action listener for transaction history dropdownlist
        ddlTAccounts.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String choice = ddlTAccounts.getItemAt(ddlTAccounts.getSelectedIndex()).toString();
                //send select acc request to server
                JSONObject jo1 = new JSONObject(client.listen(new JSON(Constants.Account.SelectAccount).add(Constants.Account.SelectedAccount, ddlTAccounts.getSelectedIndex()).toString()));
                //Onclick, send request to server to get transaction history of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.Account.TransactionHistory).add(Constants.Account.AccountId, choice).toString()));
                //populate date to screen
                AllTransactions.setText(jo.toString());
            }
        });
        //add action listener for deposit dropdownlist
        ddlAccount.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String choice = ddlAccount.getItemAt(ddlAccount.getSelectedIndex()).toString();
                //Onclick, send request to server to get balance of accountId selected
                JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.Account.GetAccountBalance).add(Constants.Account.AccountId, choice).toString()));
                //update balance label to display updated balance amount
                lblAccountbalance.setText(jo.toString());
            }
        });
        //add event listener for forget password
        btnForgetPassword.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (txtUsername.getText().equals(""))
                {
                    lblLoginValidator.setText("Enter Username to change password!");
                }
                else
                {
                    //send request to server to change password
                    JSONObject jo = JSON.tryParse(client.listen(new JSON(Constants.User.ForgetPin).add(Constants.User.Username, txtUsername.getText()).toString()));
                }
            }
        });
    }
    protected Object makeObj(final String item)  {
        return new Object() { public String toString() { return item; } };
    }
    //setLanguage for all buttons and labels
    protected void setLanguage(Map<String, String> language)
    {
        //login
        lblUser.setText(language.get("0"));
        lblPass.setText(language.get("1"));
        btnLogin.setText(language.get("2"));
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
        lblwithdraw.setText(language.get("13"));
        lblwAcc.setText(language.get("14"));
        lblwTnote.setText(language.get("15"));
        lblwBal.setText(language.get("16"));
        lblwAmt.setText(language.get("17"));
        btnConfirmWithdrawal.setText(language.get("18"));
        //deposit
        lbldeposit.setText(language.get("19"));
        lbldAcc.setText(language.get("20"));
        lbldTnote.setText(language.get("21"));
        lbldBalance.setText(language.get("22"));
        lbldAmt.setText(language.get("23"));
        btnConfirmDeposit.setText(language.get("24"));
        //bank transfer
        lblbankTransfer.setText(language.get("25"));
        lbltAcc.setText(language.get("26"));
        lbltBalance.setText(language.get("27"));
        lbltransferAccount.setText(language.get("28"));
        lbltransferAmount.setText(language.get("29"));
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
        //view account summary
        btnViewAccountSummary.setText(language.get("45"));
        lblViewAccountSummary.setText(language.get("45"));
    }
    protected JPanel getBase()
    {
        return base;
    }

    protected void setScreen(JPanel cardLayoutBase, JPanel screenName)
    {
        //attach screen
        cardLayoutBase.removeAll();
        cardLayoutBase.add(screenName);
        cardLayoutBase.repaint();
        cardLayoutBase.revalidate();
    }

    public static void main(String[] args) throws Exception {
        GUI UI = new GUI();
        JFrame frame = new JFrame("Pure ATM");
        frame.setContentPane(UI.getBase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
