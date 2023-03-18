package pure.bank;

import pure.util.LogHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Account {
    private String AccountName;
    private String UID;
    private String accountType;
    private String transactionLimit;
    private String userID;
    private String accountID;
    private ArrayList<Transaction> AccountTransactions;
    //Create a new account

    public Account() {

    }

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

    public Account(int accountID) { //Created a new constructor to accept accountID
        this.AccountName = "Default";
        this.accountID = Integer.toString(accountID);

    }

    public Account(String accountID, String accountType, String transactionLimit, String userID) {
        //Set account name and account holder
        this.accountID = accountID;
        this.accountType = accountType;
        this.userID = userID;
        this.transactionLimit = transactionLimit;
        this.AccountTransactions = new ArrayList<Transaction>();
    }

    public Account(String savings, User newUser) {
    }

    public String getAccountType() {
        return this.accountType;
    }

    public String getTransactionLimit() {
        return this.transactionLimit;
    }

    public String getUID() {
        return this.userID;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public boolean changeTransactionLimit(int newTransactionLimit) {
        this.transactionLimit = Integer.toString(newTransactionLimit);
        updateAccount();
        return true;
    }

    public boolean updateAccount() {
        String sql = "UPDATE accounts SET accountType = ?, transactionLimit = ? , userID = ? WHERE accountID = ?";

        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.accountType);
            pstmt.setString(2, this.transactionLimit);
            pstmt.setString(3, this.userID);
            pstmt.setString(4, this.accountID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LogHelper.log(Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }

    public ArrayList<Transaction> getAccountTransactions() {
        return this.AccountTransactions;
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
        return true;
    }

}