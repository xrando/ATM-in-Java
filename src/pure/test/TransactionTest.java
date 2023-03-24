package pure.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pure.bank.Account;
import pure.bank.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TransactionTest {

    static User test = new User("test", "123123");
    static Account test2 = new Account("1", "Current","5000","1");
    static

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

//    @Test
//    public void testCreateAccount() {
//        // Select Savings Account
//        int selection = 1;
//        // Create Account for userID "1"
//        String userID ="1";
//        test2.createAccount(selection, userID);
//        // Check if account created
//        test.getAccounts();
//
//    }

}