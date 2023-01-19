import java.util.Currency;
import java.util.Date;

public class Transaction
{
    private Currency Amount;
    private Date TimeStamp;
    private String TransactionNote;
    private Account TransactionAccount;
    //Create a new transaction
    public Transaction(Currency NewAmount, Account NewTransactionAccount)
    {
        this.Amount = NewAmount;
        this.TransactionAccount = NewTransactionAccount;
        this.TimeStamp = new Date();
        this.TransactionNote = "";
    }
    //Create a new transaction
    public Transaction(Currency NewAmount, String NewTransactionNote, Account NewTransactionAccount)
    {
        //Call constructor with 2 arguments
        this(NewAmount,NewTransactionAccount);

        //Set new transaction note
        this.TransactionNote = NewTransactionNote;

    }

}
