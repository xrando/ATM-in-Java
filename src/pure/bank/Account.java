package pure.bank;

import pure.util.LogHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Account Class <br>
 * All Account Defined Methods are defined here
 */
public class Account {
    private String AccountName;
    private String UID;
    private String accountType;
    private String transactionLimit;
    private String userID;
    private String accountID;
    private ArrayList<Transaction> AccountTransactions;

    /**
     * Default Account Constructor
     */
    public Account() {

    }

    /**
     * Account Constructor
     *
     * @param Account - Contains information of the account
     */
    public Account(Account Account) {
        //Set Current Account ID
        this.accountID = Account.getAccountID();

        //Set Current Account ID
        this.accountType = Account.getAccountType();

        //Set Current Account's UID
        this.userID = Account.getUID();

        //Set Transaction Limit
        this.transactionLimit = Account.getTransactionLimit();

        //Initialize transactions
        this.AccountTransactions = new ArrayList<Transaction>();
    }

    public Account(String accountID, String accountType, String transactionLimit, String userID) {
        //Set account name and account holder
        this.accountID = accountID;
        this.accountType = accountType;
        this.userID = userID;
        this.transactionLimit = transactionLimit;
        this.AccountTransactions = new ArrayList<Transaction>();
    }

    public String getAccountType() {
        return this.accountType;
    }

    public String getTransactionLimit() {
        return this.transactionLimit;
    }

    //Get User ID of the account
    public String getUID() {
        return this.userID;
    }

    //Get Account ID of the account
    public String getAccountID() {
        return this.accountID;
    }

    public ArrayList<Transaction> getAccountTransactions() {
        return this.AccountTransactions;
    }

    /**
     * This method is used to get the login status of a user from the database
     * @param newTransactionLimit The new transaction limit to change to for the user
     * @param accountID The accountID of the account
     * @return True if the user is logged in, false if the user is logged out
     */
    public boolean changeTransactionLimit(int newTransactionLimit, String accountID) {
        this.transactionLimit = Integer.toString(newTransactionLimit);
        updateAccount(accountID);
        return true;
    }

    public boolean updateAccount(String accountID) {
        String sql = "UPDATE accounts SET accountType = ?, transactionLimit = ? , userID = ? WHERE accountID = ?";

        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.accountType);
            pstmt.setString(2, this.transactionLimit);
            pstmt.setString(3, this.userID);
            pstmt.setString(4, accountID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LogHelper.log(Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }

    public ArrayList<Transaction> retrieveAccountTransactions() {
        ArrayList<Transaction> AccountTransactions = new ArrayList<Transaction>();
        try {
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM transactions WHERE accountID = ?");
            ps.setString(1, this.accountID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AccountTransactions.add(new Transaction(rs.getDouble("amount"),
                        rs.getString("transactionNote"),
                        rs.getString("date"),
                        rs.getString("timeStamp"),
                        rs.getString("payee"),
                        rs.getString("accountID")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.AccountTransactions = AccountTransactions;
        return AccountTransactions;
    }

    public double GetAccountBalance() {
        double balance = 0;
        ArrayList<Transaction> AccountTransactions = new ArrayList<Transaction>();
        AccountTransactions = this.retrieveAccountTransactions();
        for (Transaction transaction : AccountTransactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public double GetWithDrawalsBalance() throws ParseException {
        double balance = 0;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormat.format(date);
        ArrayList<Transaction> AccountTransactions = new ArrayList<Transaction>();
        AccountTransactions = this.retrieveAccountTransactions();
        for (Transaction transaction : AccountTransactions) {
            String tDate = transaction.getTransactionDate();
            if (transaction.getAmount() < 0 && tDate.equals(todayDate)) {
                balance += transaction.getAmount();
            }
        }
        System.out.println("Withdrawan Amount today: " + balance);
        return balance;
    }

    public List<Account> getTransactionAccount(String userID) {
        List<Account> accountList = new ArrayList<Account>();
        try {
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE userID = ?");
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accountList.add(new Account(rs.getString("accountID"),
                        rs.getString("accountType"),
                        rs.getString("transactionLimit"),
                        rs.getString("userID")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accountList;
    }

    public boolean createAccount(int selection, String userID) {
        String createType = "";
        switch (selection) {
            case 0:
                createType = "savings";
                break;
            case 1:
                createType = "current";
                break;
            default:
                return false;
        }
        String sql = "INSERT INTO accounts( accountType, transactionLimit ,userID) VALUES(?,?,?)";
        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, createType);
            pstmt.setString(2, "1000");
            pstmt.setString(3, userID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Account " + createType + " Created");
        return true;
    }

}
