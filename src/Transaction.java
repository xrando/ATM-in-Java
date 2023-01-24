import java.util.Date;
import java.util.Scanner;

public class Transaction {
    private double Amount;
    private Date TimeStamp;
    private String TransactionNote;
    private Account TransactionAccount;

    //Create a new transaction
    public Transaction(double NewAmount, Account NewTransactionAccount) {
        this.Amount = NewAmount;
        this.TransactionAccount = NewTransactionAccount;
        this.TimeStamp = new Date();
        this.TransactionNote = "";
    }

    //Create a new transaction
    public Transaction(double NewAmount, String NewTransactionNote, Account NewTransactionAccount) {
        //Call constructor with 2 arguments
        this(NewAmount, NewTransactionAccount);

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
    public String GetTransactionSummary() {
        if (this.Amount < 0) {
            return String.format("%s : $(%.2f) : %s", this.TimeStamp.toString(), -this.Amount, this.TransactionNote);
        } else {
            return String.format("%s : $%.2f : %s", this.TimeStamp.toString(), this.Amount, this.TransactionNote);
        }
    }

    public void GetChoice() {
        double TAmount;
        String TNote;
        System.out.println("");
        System.out.println("Please select mode of transaction:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                //Continue with Deposit
                System.out.println("You have selected to deposit.");
                System.out.println("Please key in the amount you wish to deposit E.g. 500.00");
                TAmount = sc.nextDouble();
                System.out.println("Enter a Transaction Note");
                TNote = sc.next();
                new Transaction(TAmount, TNote, this.TransactionAccount);
                this.TransactionAccount.AddTransaction(TAmount, TNote);
                System.out.printf("Deposit of %.2f Completed", TAmount);
                break;
            case 2:
                //Continue with Withdrawal
                System.out.println("You have selected to withdraw.");
                System.out.println("Please key in the amount you wish to withdraw E.g. 500.00");
                TAmount = sc.nextDouble();
                System.out.println("Enter a Transaction Note");
                TNote = sc.next();
                new Transaction(-TAmount, TNote, this.TransactionAccount);
                this.TransactionAccount.AddTransaction(TAmount, TNote);
                System.out.printf("Withdrawal of %.2f Completed", TAmount);
                break;
            default:
                System.out.println("Please key in a valid option");
        }
    }

    public static void main(String[] args) {
        Account TransactionAccount = new Account("James", new User("james", "password", "james@email.com", "87654321", new Bank("OCBC")), new Bank("OCBC"));
        Transaction transaction = new Transaction(0, TransactionAccount);
        while (true) {
            transaction.GetChoice();
            System.out.println("I am here");
            TransactionAccount.PrintTransactionHistory();
        }
    }
}


