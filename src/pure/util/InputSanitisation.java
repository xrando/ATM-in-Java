package pure.util;

import java.util.InputMismatchException;

public class InputSanitisation {
    public static String validEmail(String email) throws InputMismatchException{
        try {
            if (!email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")) {
                throw new InputMismatchException("Invalid email");
                //return "Invalid email";
            } else if (email.length() < 1) {
                throw new InputMismatchException("Email cannot be empty");
//                return "Email cannot be empty";
            } else {
                return "true";
            }
        }
        catch (InputMismatchException e){
            return e.getMessage();
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

    public static String validPin(String pin) throws InputMismatchException{
        try {
            if (!(pin.length() == 6)) {
                throw new InputMismatchException("Pin must be 6 digits");
//            return "Pin must be 6 digits";
            } else if (!pin.matches("[0-9]+")) {
                throw new InputMismatchException("Pin must be numeric");
//                return "Pin must be numeric";
            } else {
                return "true";
            }
        }
        catch (InputMismatchException e){
            return e.getMessage();
        }
    }

    public static String validNameString(String name) throws InputMismatchException{
        try {
            if (name.length() < 1) {
                throw new InputMismatchException("Name cannot be empty");
//                return "Name cannot be empty";
            } else if (!(name.matches("[a-zA-Z0-9]+"))) {
                throw new InputMismatchException("Name must be alphanumeric");
//                return "Name must be alphanumeric";
            } else {
                return "true";
            }
        }
        catch (InputMismatchException e){
            return e.getMessage();
        }
    }

    public static void main(String[] args) {
        System.out.println(validEmail("test@test.com"));
        System.out.println(validPin("123456"));
        System.out.println(validNameString("'"));
    }
}
