import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JLabel lblPasswordValidator;
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
    private JTextField txtChangePassword;
    private JTextField txtConfirmPassword;
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

    public GUI()
    {
        String user = "ben", pw = "1234";
        //Create event listener for login button
        btnLogin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Get user input from textboxs and on successful login, show main menu
                if (txtUsername.getText().equals(user)&&txtPassword.getText().equals(pw))
                {
                    txtUsername.setText("");
                    txtPassword.setText("");
                    lblUsernameValidator.setText("");
                    lblPasswordValidator.setText("");
                    //set welcome msg
                    lblWelcomeMessage.setText("Welcome back " + user);
                    //attach main menu screen
                    setScreen(base,main);
                }
                else if (!txtUsername.getText().equals(user))
                {
                    lblPasswordValidator.setText("");
                    lblUsernameValidator.setText("Wrong username");
                }
                else if (!txtPassword.getText().equals(pw))
                {
                    lblUsernameValidator.setText("");
                    lblPasswordValidator.setText("Wrong password");
                }
            }
        });
        //Create event listener for logout button
        btnLogout.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //re-attach login screen
                setScreen(base,login);
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

                //if changes found to particulars
                //loop
                //display updated message if changes found
                lblChangeSuccessful.setText("Particulars updated!");

            }
        });
        //Create event listener for confirm withdrawal button
        btnConfirmWithdrawal.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO: compute account balance after withdrawal
                //if account balance is negative after subtracting amount entered, set lblWithdrawalAmountValidator with error msg


                //else if success, create new transaction entry
                //update lblAccountBalance with updated account balance
            }
        });
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
    //sample codes to run Gui in any main
    /*public static void main(String[] args)
    {
        GUI UI = new GUI();
        JFrame frame = new JFrame("Pure ATM");
        frame.setContentPane(UI.getBase());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/
}
