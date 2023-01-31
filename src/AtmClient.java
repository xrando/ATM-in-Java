import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AtmClient {
    private JPanel BasePanel;
    private JPanel LoginPanel;
    private JPanel MainMenuPanel;
    private JButton BtnLogin;
    private JTextField TxtUsername;
    private JTextField TxtPassword;
    private JLabel LblUsernameValidator;
    private JLabel LblPasswordValidator;
    private JPanel TransactionsPanel;
    private JPanel WithdrawPanel;
    private JPanel DepositPanel;
    private JPanel TransferPanel;
    private JPanel SettingsPanel;
    private JButton BtnTransactions;
    private JButton BtnWithdraw;
    private JButton BtnTransfer;
    private JButton BtnDeposit;
    private JButton BtnLogout;
    private JButton BtnSettings;
    private JTextPane TpTransactions;
    private JButton BtnTBack;
    private JTextField TxtWithdrawalAmt;
    private JButton BtnWithdrawMoney;
    private JButton BtnWBack;
    private JTextField TxtDepositAmt;
    private JButton BtnDepositMoney;
    private JButton BtnDBack;
    private JButton BtnTFBack;

    public AtmClient()
    {
        //Create event listener for login button
        BtnLogin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //On successful login, show main menu
                if (TxtUsername.getText().equals("ben")&&TxtPassword.getText().equals("1234"))
                {
                    BasePanel.removeAll();
                    BasePanel.add(MainMenuPanel);
                    BasePanel.repaint();
                    BasePanel.revalidate();
                }
                else if (!TxtUsername.getText().equals("ben"))
                {
                    LblPasswordValidator.setText("");
                    LblUsernameValidator.setText("Wrong username");
                }
                else if (!TxtPassword.getText().equals("1234"))
                {
                    LblUsernameValidator.setText("");
                    LblPasswordValidator.setText("Wrong password");
                }
            }
        });
        //Create event listener for View transaction button
        BtnTransactions.addActionListener(new ActionListener()
        {
            //show transactions panel
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(TransactionsPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
        BtnWithdraw.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(WithdrawPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
        BtnDeposit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(DepositPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
        BtnTransfer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(TransferPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
        BtnSettings.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(SettingsPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
        BtnLogout.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(LoginPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
        BtnTBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(MainMenuPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
        BtnWBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(MainMenuPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
        BtnDBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(MainMenuPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
        BtnTFBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BasePanel.removeAll();
                BasePanel.add(MainMenuPanel);
                BasePanel.repaint();
                BasePanel.revalidate();
            }
        });
    }
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Pure ATM");
        frame.setContentPane(new AtmClient().BasePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
