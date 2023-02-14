import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
public class Transaction {
    private double Amount;
    private Date TimeStamp;
    private String TransactionNote;
    private String TransactionAccount;

    //Create a new transaction
    public Transaction(double NewAmount, String NewTransactionNote, String NewTransactionAccount) {
        //Set new transaction Amount
        this.Amount = NewAmount;
        //Set new transaction note
        this.TransactionNote = NewTransactionNote;
        this.TransactionAccount = NewTransactionAccount;
    }

    public Transaction(){
        this.Amount=0;
        //Set new transaction note
        this.TransactionNote = "NewTransactionNote";
        this.TransactionAccount = null;
    }

    // Perhaps Transaction constructor can add a date and time stamp so that we don't have to access the database to get it for each transaction
    // Add a transaction ID as well
    public Transaction(double amount, String transactionNote, String date, String timeStamp, String accountID) {
        this.Amount = amount;
        this.TransactionNote = transactionNote;
        this.TransactionAccount = accountID;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getTimeStamp() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String strTime = dateFormat.format(date);
        return strTime;
    }
    public String getDate(){
        String sql = "Select date, timeStamp from transactions where transactionID = ?";
        String dateTime = null;
        try{
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "3"); // To update this to the transaction ID todo
            ResultSet rs = ps.executeQuery();
            //Add to AccountTransactions
            //System.out.print(rs.getString("date"));
            //System.out.print(rs.getString("timeStamp"));
            dateTime = rs.getString("date") + " " + rs.getString("timeStamp");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dateTime;
    }

    public String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        return strDate;
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

    public String getTransactionAccount() {
        return TransactionAccount;
    }

    public void setTransactionAccount(String transactionAccount) {
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

    public void AddTransactionToSQL(Account account,Transaction transactionDetails){
        String sql = "INSERT INTO transactions( amount, timeStamp, transactionNote, date, accountID) VALUES(?,?,?,?,?)";

        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, transactionDetails.Amount);
            pstmt.setString(2, getTimeStamp());
            pstmt.setString(3, transactionDetails.TransactionNote);
            pstmt.setString(4, getCurrentDate());
            pstmt.setString(5, account.getAccountID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void GetChoice(Account TransactionAccount) {
        double TAmount;
        String TNote;
        System.out.println("");
        System.out.println("Please select mode of transaction:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        System.out.println("3. Transaction Summary");
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
                Transaction deposit = new Transaction(TAmount, TNote,TransactionAccount.getAccountID());
                deposit.AddTransactionToSQL(TransactionAccount,deposit);
                System.out.printf("Deposit of %.2f Completed", TAmount);
                break;
            case 2:
                //Continue with Withdrawal
                System.out.println("You have selected to withdraw.");
                System.out.println("Please key in the amount you wish to withdraw E.g. 500.00");
                TAmount = sc.nextDouble();
                System.out.println("Enter a Transaction Note");
                TNote = sc.next();
                Transaction withdraw = new Transaction(-TAmount, TNote,TransactionAccount.getAccountID());
                withdraw.AddTransactionToSQL(TransactionAccount,withdraw);
                System.out.printf("Withdrawal of %.2f Completed", TAmount);
                break;
            default:
                System.out.println("Please key in a valid option");
        }
    }

    public static void main(String[] args) {
        //test Login
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        System.out.println("Enter pin: ");
        String password = sc.nextLine();
        //login
        User test2 = new User();
        test2 = test2.Login(username, password);
        System.out.println("UID: " + test2.getUID());
        System.out.println("Username: " + test2.getUsername());
        System.out.println("Password: " + test2.getPassword());
        System.out.println("Salt: " + test2.getSalt());
        System.out.println("Email: " + test2.getEmail());
        System.out.println("Phone: " + test2.getPhone());
        System.out.println("Login Status: " + test2.getLoginStatus());

        Account TransactionAccount = new Account();
        TransactionAccount = TransactionAccount.getAccount((test2.getUID()));
        Transaction transaction = new Transaction();
        while (true) {
            transaction.GetChoice(TransactionAccount);
            //TransactionAccount.PrintTransactionHistory();
        }
    }
}


