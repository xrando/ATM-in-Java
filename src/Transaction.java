import java.util.Date;

public class Transaction
{
    private double Amount;
    private Date TimeStamp;
    private String TransactionNote;
    private Account TransactionAccount;
    //Create a new transaction
    public Transaction(double NewAmount, Account NewTransactionAccount)
    {
        this.Amount = NewAmount;
        this.TransactionAccount = NewTransactionAccount;
        this.TimeStamp = new Date();
        this.TransactionNote = "";
    }
    //Create a new transaction
    public Transaction(double NewAmount, String NewTransactionNote, Account NewTransactionAccount)
    {
        //Call constructor with 2 arguments
        this(NewAmount,NewTransactionAccount);

        //Set new transaction note
        this.TransactionNote = NewTransactionNote;

    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getTransactionNote() {
        return TransactionNote;
    }

    public void setTransactionNote(String transactionNote) {
        TransactionNote = transactionNote;
    }

    public Account getTransactionAccount() {
        return TransactionAccount;
    }

    public void setTransactionAccount(Account transactionAccount) {
        TransactionAccount = transactionAccount;
    }
    //Get transaction summary
    public String GetTransactionSummary()
    {
        if (this.Amount < 0)
        {
            return String.format("%s : $(%.2f) : %s", this.TimeStamp.toString(),-this.Amount,this.TransactionNote);
        }
        else
        {
            return String.format("%s : $%.2f : %s", this.TimeStamp.toString(),this.Amount,this.TransactionNote);
        }
    }
}
