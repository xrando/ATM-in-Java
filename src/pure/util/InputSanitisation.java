package pure.util;

public class InputSanitisation {
    public static String validEmail(String email) {
        if (!email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")) {
            return "Invalid email";
        } else if (email.length() < 1) {
            return "Email cannot be empty";
        } else {
            return "true";
        }
    }

    // Probably not needed
    public static String validPhone(String phone) {
        if (!phone.matches("^[89][0-9]{7}$")) {
            return "Invalid phone number";
        } else if (phone.length() < 1) {
            return "Phone number cannot be empty";
        } else {
            return "true";
        }
    }

    public static String validPin(String pin) {
        if (!(pin.length() == 6)) {
            return "Pin must be 6 digits";
        } else if (!pin.matches("[0-9]+")) {
            return "Pin must be numeric";
        } else {
            return "true";
        }
    }

    public static String validNameString(String name) {
        if (name.length() < 1) {
            return "Name cannot be empty";
        } else if (!(name.matches("[a-zA-Z0-9]+"))) {
            return "Name must be alphanumeric";
        } else {
            return "true";
        }
    }

    public static void main(String[] args) {
        System.out.println(validEmail("test@test.com"));
        System.out.println(validPin("123456"));
        System.out.println(validNameString("'"));
    }
}
