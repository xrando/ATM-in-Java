package pure.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pure.bank.User;
import static org.junit.jupiter.api.Assertions.*;
import java.security.SecureRandom;

class UserTest {

    static User test = new User("test", "123123");

    @BeforeEach
    void login(){
        test.Login("test", "123123");
    }

    @AfterEach
    void logout(){
        test.logout();
    }

    @Test
    void testlogin() {
        //assertTrue(test.Login("test", "123123"));
        assertTrue(test.getLoginStatus());
    }

    @Test
    void testlogout() {
        test.logout();
        assertFalse(test.getLoginStatus());
        assertFalse(test.getLoginStatusFromDB("test"));
    }

    @Test
    void testPasswordAndHash(){
        String salt = test.getSalt();
        String pass = test.generatePasswordHash("123123", salt);
        assertEquals(pass, test.getPassword());
    }

    @Test
    void testValidatePassword(){
        String UID = test.getUID();
        test.validatePassword("123123", UID);
    }

    @Test
    void testGetSalt(){ // Probably not needed
        // Salt must be different each time
        for (int i = 0; i < 100; i++) {
            String salt = test.generateSalt();
            String salt2 = test.generateSalt();
            assertNotEquals(salt, salt2);
        }
    }

    // Test for changePin
    @Test
    void testChangePin(){
        String oldpin = "123123";
        String newPin = "123456";
        test.changePin(oldpin, newPin);
        test.logout();
        assertTrue(test.Login("test", newPin));
        test.changePin(newPin, oldpin);
        test.logout();
        assertTrue(test.Login("test", oldpin));
    }

    // Test create user
    @Test
    void testCreateUser(){
        // Generate random number of length at most 6
        int random = new SecureRandom().nextInt() % 1000000;
        String Username = "test" + random;
        System.out.print("Username: " + Username);
        User test2 = new User(Username, "123123");

        assertTrue(test2.CreateUser(Username, "123123", "test@test.com",0));
        // Login with new user
        assertTrue(test2.Login(Username, "123123"));
        assertTrue(test2.logout());

        // Create User with same username should fail
        assertFalse(test2.CreateUser(Username, "123123", "test@test.com",0));

        // Create User with pin < 6 and > 6 should fail
        assertFalse(test2.CreateUser("test3", "123", "test@test.com", 0));
        assertFalse(test2.CreateUser("test3", "123123123", "test@test.com", 0));

        // Create User with email that is not valid should fail
        assertFalse(test2.CreateUser("test3", "123123", "testtest.com", 0));
    }

    // Test Update User()
    @Test
    void testUpdateEmail(){
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
    void testCheckUserExists(){
        String testUID = test.getUID();
        assertEquals(testUID, test.CheckUserExist("test"));
    }

}