package pure.util;

import java.util.InputMismatchException;

public class InputSanitisation {
    /**
     * Checks if the input is a valid email <br>
     * This is to prevent SQL injection
     * @param email The email to be checked
     * @return String "true" if the email is valid, else the error message
     * @throws InputMismatchException if the email is invalid
     */
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

    /**
     * Checks if the input is a valid Singapore phone number <br>
     * This is to prevent SQL injection
     * @param phone The phone number to be checked
     * @return String "true" if the phone number is valid, else the error message
     * @throws InputMismatchException if the phone number is invalid
     */
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

    /**
     * Checks if the input is a valid Pin <br>
     * This is to prevent SQL injection
     * @param pin The pin to be checked
     * @return String "true" if the Pin is valid, else the error message
     * @throws InputMismatchException if the Pin is invalid
     */
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

    /**
     * Checks if the input is a valid name <br>
     * This is to prevent SQL injection
     * @param name The name to be checked
     * @return String "true" if the name is valid, else the error message
     * @throws InputMismatchException if the name is invalid
     */
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
