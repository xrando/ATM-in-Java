import java.util.ArrayList;

public class Account {
    private String AccountName;
    private String UID;
    private User AccountHolder;
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

    public User getAccountHolder() {
        return AccountHolder;
    }

    public void setAccountHolder(User accountHolder) {
        AccountHolder = accountHolder;
    }

    public ArrayList<Transaction> getAccountTransactions() {
        return AccountTransactions;
    }

    public void setAccountTransactions(ArrayList<Transaction> accountTransactions) {
        AccountTransactions = accountTransactions;
    }
}
