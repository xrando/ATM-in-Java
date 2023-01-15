import java.util.Currency;

public class Account {
    private int AccountNumber;
    private Currency Balance;

    public int getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        AccountNumber = accountNumber;
    }

    public Currency getBalance() {
        return Balance;
    }

    public void setBalance(Currency balance) {
        Balance = balance;
    }
}
