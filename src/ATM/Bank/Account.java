package ATM.Bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ATM.Utilities.TableHelper;

public class Account {
    private String AccountName;
    private String UID;
    private String accountType;
    private String userID;
    private String accountID;
    private ArrayList<Transaction> AccountTransactions;
    //Create a new account
    public Account(String NewAccountName, Bank CurrentBank)
    {
        //Set account name and account holder
        this.AccountName = NewAccountName;

        //Generate new account UID
        this.UID = CurrentBank.generateNewAccountUID();

        //Initialize transactions
        this.AccountTransactions = new ArrayList<Transaction>();

    }

    public Account(Account Account)
    {
        //Set Current Account ID
        this.accountID = Account.getAccountID();

        //Set Current Account ID
        this.accountType = Account.getAccountType();

        //Set Current Account's UID
        this.UID = Account.getUID();

        //Initialize transactions
        this.AccountTransactions = new ArrayList<Transaction>();
    }

    public Account(){

    }

    public Account(int accountID){ //Created a new constructor to accept accountID
        this.AccountName = "Default";
        this.accountID = Integer.toString(accountID);

    }

    public Account(String accountID, String accountType,String userID)
    {
        //Set account name and account holder
        this.accountID = accountID;
        this.accountType =accountType;
        this.userID =userID;
        //Generate new account UID
        //this.UID = CurrentBank.generateNewAccountUID();
        //Initialize transactions
        this.AccountTransactions = new ArrayList<Transaction>();
    }

    public Account(String savings, User newUser) {
    }


//    public String getAccountName() {
//        return AccountName;
//    }

    public String getAccountType(){ return this.accountType;}

    public String getUID() {
        return this.userID;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public ArrayList<Transaction> getAccountTransactions() {
        return this.AccountTransactions;
    }

    public ArrayList<Transaction> retrieveAccountTransactions() {
        ArrayList <Transaction> AccountTransactions = new ArrayList<Transaction>();
        try{
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM transactions WHERE accountID = ?");
            ps.setString(1, this.accountID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                //Add to AccountTransactions
                //System.out.print(rs.getString("accountID"));
                //System.out.print(rs.getString("amount"));
                AccountTransactions.add(new Transaction(rs.getDouble("amount"), rs.getString("transactionNote"), rs.getString("date"), rs.getString("timeStamp"), rs.getString("payee") ,rs.getString("accountID")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.AccountTransactions = AccountTransactions;
        return AccountTransactions;
    }

    public double GetAccountBalance() {
        double balance =0;
        ArrayList <Transaction> AccountTransactions = new ArrayList<Transaction>();
        AccountTransactions = this.retrieveAccountTransactions();
        for(Transaction transaction : AccountTransactions)
        {
            balance+=transaction.getAmount();
        }
        return balance;
    }

    //Print transaction history of the account
    public void PrintTransactionHistory()
    {
//        System.out.printf("\nTransaction history for account %s\n",this.UID);
//        for (int i=this.AccountTransactions.size()-1;i>=0;i--)
//        {
//            System.out.println(this.AccountTransactions.get(i).GetTransactionSummary());
//        }
//        System.out.println();
    }

    //Add transaction in this account
    public boolean AddTransaction(double Amount, String TransactionNote)
    {
        //Create new transaction obj and add to list
        Transaction NewTransaction = new Transaction(Amount,TransactionNote,this.accountID);
        this.AccountTransactions.add(NewTransaction);
        return true;
    }

//    public Account setTransactionAccount(String userID){
//        ArrayList <Account> accountList = new ArrayList<Account>();
//        try{
//            Connection conn = sqliteDatabase.connect();
//            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE userID = ?");
//            ps.setString(1, userID);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()){
//                accountList.add(new Account(rs.getString("accountID"),
//                                            rs.getString("accountType"),
//                                            rs.getString("userID")));
//            }
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        TableHelper tb = new TableHelper();
//        tb.setHeaders("No.","ATM.ATM.Bank.Bank.Account ID", "ATM.ATM.Bank.Bank.Account Type", "ATM.ATM.Bank.Bank.User ID");
//        for(int i =0; accountList.size()>i;i++){
//            tb.addRow(String.valueOf(i+1),
//                      String.valueOf(accountList.get(i).accountID),
//                      accountList.get(i).accountType,
//                      String.valueOf(accountList.get(i).userID));
//        }
//        tb.print(false);
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Please key in the number of the account which you would like to transact in:");
//        int userInput = sc.nextInt();
//        Account TransactionAccount = accountList.get(userInput-1);
//        new Account(TransactionAccount.accountID,TransactionAccount.accountType,TransactionAccount.userID);
//        return TransactionAccount;
//    }

    public String getAccountSummary()
    {
        //Calculate ATM.ATM.Bank.Bank.Account's balance
        double balance = this.GetAccountBalance();

        //Format account summary, to distinguish negative and positive balance, () will be used to display negative balance
        if(balance<0)
        {
            return String.format("%s : $(%.2f) : %s",this.UID,-balance,this.AccountName);
        }
        else
        {
            return String.format("%s : $%.2f : %s",this.UID,balance,this.AccountName);
        }
    }

    public List<Account> getTransactionAccount(String UID){
        List<Account> accountList = new ArrayList<Account>();
        try{
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE userID = ?");
            ps.setString(1, UID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                accountList.add(new Account(rs.getString("accountID"),
                        rs.getString("accountType"),
                        rs.getString("userID")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accountList;
    }

    public boolean createAccount(int selection,String userID) {
        String createType ="";
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
        String sql = "INSERT INTO accounts( accountType, userID) VALUES(?,?)";
        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, createType);
            pstmt.setString(2, userID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
