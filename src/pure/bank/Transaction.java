package pure.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.lang.Math;

public class Transaction {
    private final double Amount;
    private String timeStamp;
    private String date;
    private final String TransactionNote;
    private final String accountID;
    private final String payee;

    //Create a new transaction
    public Transaction(double NewAmount, String NewTransactionNote, String accountID) {
        //Set new transaction Amount
        this.Amount = NewAmount;
        //Set new transaction note
        this.TransactionNote = NewTransactionNote;
        //Set transaction AccountID
        this.accountID = accountID;
        //
        this.date = getCurrentDate();
        this.timeStamp = getTimeStamp();
        this.payee = "";
    }

    //Create a new transaction (with payee)
    public Transaction(double NewAmount, String NewTransactionNote, String payee, String accountID) {
        //Set new transaction Amount
        this.Amount = NewAmount;
        //Set new transaction note
        this.TransactionNote = NewTransactionNote;
        //Set transaction AccountID
        this.accountID = accountID;
        //
        this.date = getCurrentDate();
        this.timeStamp = getTimeStamp();
        this.payee = payee;
    }

    public Transaction() {
        this.Amount = 0;
        //Set new transaction note
        this.TransactionNote = "NewTransactionNote";
        this.accountID = null;
        this.payee = "";
    }

    // Perhaps ATM.ATM.Bank.Bank.Transaction constructor can add a date and time stamp so that we don't have to access the database to get it for each transaction
    // Add a transaction ID as well
    public Transaction(double amount, String transactionNote, String date, String timeStamp, String payee, String accountID) {
        this.Amount = amount;
        this.date = date;
        this.timeStamp = timeStamp;
        this.TransactionNote = transactionNote;
        this.accountID = accountID;
        this.payee = payee;
    }

    public static void transactionEmail(Transaction transactionDetails, String username, String email) {
        // Send email to user with new password
        String subject = "Transaction Alert";
        String body = "Dear " + username + ",\n\n" +
                "We refer to your Transaction dated " + transactionDetails.getTransactionDate() + ". We are pleased to confirm that the transaction was completed.\n\n" +
                "Date & Time: " + transactionDetails.getTransactionDate() + " , " + transactionDetails.getTransactionTime() + "\n" +
                "Amount: " + (-transactionDetails.getAmount()) + "\n" +
                "From: " + transactionDetails.getAccountID() + "\n" +
                "To: " + transactionDetails.getPayee() + "\n\n" +
                "To view your transactions, login to your account to view your transaction history.\n" +
                "If you did not request a password change, please contact us immediately.\n\n" +
                "Pure Bank LTD\n" +
                "pureinc933@gmail.com\n\n" +
                "Please do not reply to this email as it is automatically generated.";
        // Send email
        //Helper.SendMail(email , subject, body);
    }

    public static void main(String[] args) {

    }

    public double getAmount() {
        return this.Amount;
    }

    public String getPayee() {
        return this.payee;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public String getTimeStamp() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String strTime = dateFormat.format(date);
        return strTime;
    }

    public String getTransactionDate() {
        return this.date;
    }

    public String getTransactionTime() {
        return this.timeStamp;
    }

    public String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public String getTransactionNote() {
        return TransactionNote;
    }

    public boolean AddTransactionToSQL(Transaction transactionDetails, Account account) throws IllegalArgumentException {
        String sql = "INSERT INTO transactions( amount, timeStamp, transactionNote, date, payee,accountID) VALUES(?,?,?,?,?,?)";

        // 2-way Transfer
        if (transactionDetails.payee != "") {
            try {
                if (Math.abs(transactionDetails.Amount) > Double.valueOf(account.getTransactionLimit())) {
                    throw new IllegalArgumentException("Value Entered is greater than transaction limit");
                } else {
                    //deduct for account owner
                    try (Connection conn = sqliteDatabase.connect();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setDouble(1, (-transactionDetails.Amount));
                        pstmt.setString(2, transactionDetails.timeStamp);
                        pstmt.setString(3, transactionDetails.TransactionNote);
                        pstmt.setString(4, transactionDetails.date);
                        pstmt.setString(5, transactionDetails.payee);
                        pstmt.setString(6, transactionDetails.accountID);
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        return false;
                    }
                    //add to payee account
                    try (Connection conn = sqliteDatabase.connect();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setDouble(1, transactionDetails.Amount);
                        pstmt.setString(2, transactionDetails.timeStamp);
                        pstmt.setString(3, transactionDetails.TransactionNote);
                        pstmt.setString(4, transactionDetails.date);
                        pstmt.setString(5, transactionDetails.accountID);
                        pstmt.setString(6, transactionDetails.payee);
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        return false;
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        // 1-way Transfer
        else {
            if (transactionDetails.getAmount() < 0) {
                try {
                    if (Math.abs(transactionDetails.Amount) > Double.valueOf(account.getTransactionLimit())) {
                        throw new IllegalArgumentException("Value Entered is greater than transaction limit");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
            try (Connection conn = sqliteDatabase.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, transactionDetails.Amount);
                pstmt.setString(2, transactionDetails.timeStamp);
                pstmt.setString(3, transactionDetails.TransactionNote);
                pstmt.setString(4, transactionDetails.date);
                pstmt.setString(5, transactionDetails.payee);
                pstmt.setString(6, transactionDetails.accountID);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }
}


