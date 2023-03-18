package pure.util;

import java.util.InputMismatchException;

public class InputSanitisation {
    public static String validEmail(String email) throws InputMismatchException{
        try {
            if (email.length() < 1){
                throw new InputMismatchException("Email cannot be empty");
            }
            else if (!email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")) {
                throw new InputMismatchException("Invalid email");
            } else {
                return "true";
            }
        }
        catch (InputMismatchException e){
            return e.getMessage();
        }
    }

    // Probably not needed
    public static String validPhone(String phone) throws InputMismatchException{
        // Phone must be 8 digits, starting with 8 or 9 or just 0
        try{
            if (phone.length() < 1){
                throw new InputMismatchException("Phone number cannot be empty");
            }
            else if (!phone.matches("^([8-9][0-9]{7}|0)$")) {
                throw new InputMismatchException("Invalid phone number");
            } else {
                return "true";
            }
        }
        catch (InputMismatchException e){
            return e.getMessage();
        }
    }

    public static String validPin(String pin) throws InputMismatchException{
        try {
            if (pin.length() < 1) {
                throw new InputMismatchException("Pin cannot be empty");
            } else if (!pin.matches("[0-9]+")) {
                throw new InputMismatchException("Pin must be numeric");
            } else if (!(pin.length() == 6)) {
                throw new InputMismatchException("Pin must be 6 digits");
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
            } else if(!(name.length() <= 10)) {
                throw new InputMismatchException("Name must be 10 characters or less");
            } else if (!(name.matches("[a-zA-Z0-9]+"))) {
                throw new InputMismatchException("Name must be alphanumeric");
            } else {
                return "true";
            }
        }
        catch (InputMismatchException e){
            return e.getMessage();
        }
    }
}
