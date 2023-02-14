import java.sql.*;
import java.util.ArrayList;

public class Account {
    private String AccountName;
    private String UID;
    private User AccountHolder;
    private String accountHolder;
    private String userID;
    private String accountID;
    private ArrayList<Transaction> AccountTransactions;
    //Create a new account
    public Account(String NewAccountName, User NewAccountHolder, Bank CurrentBank)
    {
        //Set account name and account holder
        this.AccountName = NewAccountName;
        this.AccountHolder = NewAccountHolder;

        //Generate new account UID
        this.UID = CurrentBank.generateNewAccountUID();

        //Initialize transactions
        this.AccountTransactions = new ArrayList<Transaction>();

    }

    public Account(){

    }
    public Account(int accountID){ //Created a new constructor to accept accountID
        this.AccountName = "Default";
        this.accountID = Integer.toString(accountID);

    }
    public Account(String NewAccountName, User NewAccountHolder)
    {
        //Set account name and account holder
        this.AccountName = NewAccountName;
        this.AccountHolder = NewAccountHolder;
        //Generate new account UID
        //this.UID = CurrentBank.generateNewAccountUID();
        //Initialize transactions
        this.AccountTransactions = new ArrayList<Transaction>();
    }


    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getAccountHolder() {
        return this.accountHolder;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public void setAccountHolder(User accountHolder) {
        AccountHolder = accountHolder;
    }

    public ArrayList<Transaction> getAccountTransactions() {
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
                AccountTransactions.add(new Transaction(rs.getDouble("amount"), rs.getString("transactionNote"), rs.getString("date"), rs.getString("timeStamp"), rs.getString("accountID")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.AccountTransactions = AccountTransactions;
        return AccountTransactions;
    }

    public void setAccountTransactions(ArrayList<Transaction> accountTransactions) {
        AccountTransactions = accountTransactions;
    }
    public String getAccountSummary()
    {
        //Calculate Account's balance
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
    public double GetAccountBalance()
    {
        double balance =0;
        ArrayList <Transaction> AccountTransactions = new ArrayList<Transaction>();
        AccountTransactions = this.getAccountTransactions();
        for(Transaction transaction : AccountTransactions)
        {
            balance+=transaction.getAmount();
        }
        return balance;
    }
    //Print transaction history of the account
    public void PrintTransactionHistory()
    {
        System.out.printf("\nTransaction history for account %s\n",this.UID);
        for (int i=this.AccountTransactions.size()-1;i>=0;i--)
        {
            System.out.println(this.AccountTransactions.get(i).GetTransactionSummary());
        }
        System.out.println();
    }
    //Add transaction in this account
    public void AddTransaction(double Amount, String TransactionNote)
    {
        //Create new transaction obj and add to list
        Transaction NewTransaction = new Transaction(Amount,TransactionNote,this.accountID);
        this.AccountTransactions.add(NewTransaction);
    }

    public Account getAccount(String userID) {
        Account account = new Account();
        try{
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE userID = ?");
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            account.accountHolder = rs.getString("accountHolder");
            account.userID = rs.getString("userID");
            account.accountID = rs.getString("accountID");
            conn.close();
            return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
