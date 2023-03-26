package pure.test;

import org.junit.*;
import static org.junit.Assert.*;
import pure.bank.User;
import java.security.SecureRandom;

public class UserTest {

    static User test = new User("test", "123123");

    @Before
    public void login(){
        test.Login("test", "123123");
    }

    @After
    public void logout(){
        test.logout();
    }

    @Test
    public void testlogin() {
        //assertTrue(test.Login("test", "123123"));
        assertTrue(test.getLoginStatus());
    }

    @Test
    public void testlogout() {
        test.logout();
        assertFalse(test.getLoginStatus());
        assertFalse(test.getLoginStatusFromDB("test"));
    }

    @Test
    public void testPasswordAndHash(){
        String salt = test.getSalt();
        String pass = test.generatePasswordHash("123123", salt);
        assertEquals(pass, test.getPassword());
    }

    @Test
    public void testValidatePassword(){
        String UID = test.getUID();
        test.validatePassword("123123", UID);
    }

    @Test
    public void testGetSalt(){ // Probably not needed
        // Salt must be different each time
        for (int i = 0; i < 100; i++) {
            String salt = test.generateSalt();
            String salt2 = test.generateSalt();
            assertNotEquals(salt, salt2);
        }
    }

    // Test for changePin
    @Test
    public void testChangePin(){
        String oldpin = "123123";
        String newPin = "123456";
        test.changePin(oldpin, newPin);
        test.logout();
        assertTrue(test.Login("test", newPin));
        test.changePin(newPin, oldpin);
        test.logout();
        assertTrue(test.Login("test", oldpin));
        assertEquals("New pin must be different from old pin.", test.changePin(oldpin, oldpin), "New pin must be different from old pin.");
    }

    // Test create user
    @Test
    public void testCreateUser(){
        // Generate random number of length at most 6
        String Username = "test";
        User test2 = new User(Username, "123123");
        String UID = test2.genUID();
        Username = Username + UID;

        assertTrue(test2.CreateUser(Username, "123123", "test@test.com","98765432", 0));
        // Login with new user
        assertTrue(test2.Login(Username, "123123"));
        assertTrue(test2.logout());

        // Create User with same username should fail
        assertFalse(test2.CreateUser(Username, "123123", "test@test.com","0", 0));

        // Create User with pin < 6 and > 6 should fail
        assertFalse(test2.CreateUser("test3", "123", "test@test.com", "0", 0));
        assertFalse(test2.CreateUser("test4", "123123123", "test@test.com", "0", 0));
        assertFalse(test2.CreateUser("test5", "12312", "test@test.com", "0", 0));

        // Create User with email that is not valid should fail
        assertFalse(test2.CreateUser("test3", "123123", "testtest.com", "0", 0));


    }

    // Test Update User()
    @Test
    public void testUpdateEmail(){
        String oldEmail = test.getEmail();
        // Generate a random number of length at most 3
        int random = new SecureRandom().nextInt() % 1000;
        String newEmail = random + oldEmail;
        test.setEmail(newEmail);
        // This updates the DataBase
        test.logout();
        test.Login("test", "123123");
        assertEquals(newEmail, test.getEmail());
        // Reset email
        test.setEmail(oldEmail);
        test.logout();
        test.Login("test", "123123");
        assertEquals(oldEmail, test.getEmail());
    }

    // Test Check User Exists (test)
    @Test
    public void testCheckUserExists(){
        String testUID = test.getUID();
        assertEquals(testUID, test.CheckUserExist("test"));
    }

}