package pure.test;

import org.junit.jupiter.api.Test;
import pure.util.InputSanitisation;

import static org.junit.jupiter.api.Assertions.*;

public class InputSanitisationTest {

    @Test
    public void validEmail() {
        // Valid
        assertEquals("true", InputSanitisation.validEmail("test@test.com"));

        // Test for Empty String
        assertEquals("Email cannot be empty", InputSanitisation.validEmail(""));

        assertEquals("Invalid email", InputSanitisation.validEmail("test@test"));
        assertEquals("Invalid email", InputSanitisation.validEmail("@test"));
        assertEquals("Invalid email", InputSanitisation.validEmail("test@"));

        // Test for SQL injection
        assertEquals("Invalid email", InputSanitisation.validEmail("`"));
    }
    @Test
    public void validPhone() {
        // Valid
        assertEquals("true", InputSanitisation.validPhone("91234567"));
        // 0 is the default phone number
        assertEquals("true", InputSanitisation.validPhone("0"));

        // Test for Email String
        assertEquals("Phone number cannot be empty", InputSanitisation.validPhone(""));

        // Test for invalid phone numbers
        assertEquals("Invalid phone number", InputSanitisation.validPhone("12345678"));
        assertEquals("Invalid phone number", InputSanitisation.validPhone("1234567"));
        assertEquals("Invalid phone number", InputSanitisation.validPhone("123456789"));
        assertEquals("Invalid phone number", InputSanitisation.validPhone("1234567a"));
        assertEquals("Invalid phone number", InputSanitisation.validPhone("78654321"));

        // Test for SQL injection
        assertEquals("Invalid phone number", InputSanitisation.validPhone("`"));
    }

    @Test
    public void validPin() {
        // Valid
        assertEquals("true", InputSanitisation.validPin("123456"));

        // Test for Empty String
        assertEquals("Pin cannot be empty", InputSanitisation.validPin(""));

        // Test for invalid pins
        assertEquals("Pin must be 6 digits", InputSanitisation.validPin("12345"));
        assertEquals("Pin must be 6 digits", InputSanitisation.validPin("1234567"));
        assertEquals("Pin must be numeric", InputSanitisation.validPin("12345a"));
        assertEquals("Pin must be 6 digits", InputSanitisation.validPin("0"));
        assertEquals("Pin must be 6 digits", InputSanitisation.validPin("123"));

        // Test for SQL injection
        assertEquals("Pin must be numeric", InputSanitisation.validPin("`"));
    }

    @Test
    public void validNameString() {
        // True
        assertEquals("true", InputSanitisation.validNameString("test"));

        // Test for Empty String
        assertEquals("Name cannot be empty", InputSanitisation.validNameString(""));

        // Test for invalid name strings
        assertEquals("Name must be 10 characters or less", InputSanitisation.validNameString("12345678901"));
        assertEquals("Name must be alphanumeric", InputSanitisation.validNameString("test!"));

        // Test for SQL injection
        assertEquals("Name must be alphanumeric", InputSanitisation.validNameString("`"));
    }
}