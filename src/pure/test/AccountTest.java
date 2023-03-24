package pure.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pure.bank.User;
import pure.bank.Account;

import java.security.SecureRandom;
import java.util.List;

import static org.junit.Assert.*;

public class AccountTest {

    static User test = new User("test", "123123");
    static Account test2 = new Account("1", "Current","5000","1");

    @Before
    public void login(){
        test.Login("test", "123123");
    }

    @After
    public void logout(){
        test.logout();
    }

    @Test
    public void testChangeTransferLimit() {
        //Transaction Limit to change to
        int newTransferLimit = 100;
        test2.changeTransactionLimit(newTransferLimit);
        assertEquals(test2.getTransactionLimit(),Integer.toString(newTransferLimit));
        test2.changeTransactionLimit(1000);
        assertNotEquals(test2.getTransactionLimit(),Integer.toString(newTransferLimit));
    }

    @Test
    public void testCreateAccount() {
        // Select Current Account
        int selection = 0;
        // Create Account for userID "1"
        String userID =test2.getUID();
        test2.createAccount(selection, userID);
        // Check if account created
        test.getUserFromDatabase();
        List<Account> Accounts = test.getAccounts();
        assertEquals("savings",Accounts.get(Accounts.size() - 1).getAccountType());

        // Select Savings Account
        int selection2 = 1;
        test2.createAccount(selection2, userID);
        // Check if account created
        test.getUserFromDatabase();
        Accounts = test.getAccounts();
        assertEquals("current",Accounts.get(Accounts.size() - 1).getAccountType());
    }

}