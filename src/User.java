import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.util.logging.Level;

public class User
{
    private String UID;
    private String Username;
    private String Password;

    // Salt is static for now, probably wont need it to be static when we read from the CSV file
    private static String Salt;
    private String email;
    private String phone;
    private List<Account> Accounts;
    //Create a new user
    public User(String Username, String Password, String email, String phone, Bank CurrentBank)
    {
        this.Username = Username;
        this.email = email;
        this.phone = phone;
        Salt = generateSalt();
        this.Password = generateHash(Password, Salt);
        //modified to generate new UID from bank
        this.UID = CurrentBank.generateNewUserUID();
        this.Accounts = new ArrayList<Account>();
        System.out.printf("UserName: %s\nUID: %s",Username,UID);
    }
    //Add an account for user
    public void AddAccount(Account NewAccount)
    {
        this.Accounts.add(NewAccount);
    }
    protected String getUsername() {
        return Username;
    }

    protected void setUsername(String username) {
        this.Username = username;
    }

    protected String getPassword() {
        return Password;
    }

    protected void setPassword(String password) {
        Password = generateHash(password, Salt);
    }

    protected List<Account> getAccounts() {
        return Accounts;
    }

    protected void setAccounts(List<Account> userAccounts) {
        Accounts = userAccounts;
    }

    protected String getUID() {
        return UID;
    }

    public void setSalt() {
        Salt = generateSalt();
    }

    public static String getSalt() {
        return Salt;
    }

    public void changePin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter old pin: ");
        String oldPin = sc.nextLine();

        //Add condition to cancel
        while (!this.getPassword().equals(generateHash(oldPin, this.getSalt()))) {
            System.out.print("Pin does not match!\nEnter old pin: ");
            oldPin = sc.nextLine();
        }

        System.out.print("Enter new pin: ");
        String newPin = sc.nextLine();

        while(newPin.length() != 6) {
            System.out.print("Pin must be 6 digits!\nEnter new pin: ");
            newPin = sc.nextLine();
        }

        System.out.print("Confirm new pin: ");
        String newPin2 = sc.nextLine();

        while (!newPin.equals(newPin2)) {
            System.out.println("Pin does not match!\nConfirm new pin: ");
            newPin2 = sc.nextLine();
        }
        this.Password = generateHash(newPin, this.getSalt());

    }
    // Salt is now not randomized, but generated from the username and UID of the user
    // Salt could probably just be the username and/or UID in plaintext...
    public String generateSalt() {
        Salt = hash(this.Username + getUID());
        return Salt;
    }

    public String generateHash(String password, String salt) {
        //MD5 hash
        return hash(password + salt);
    }

    public String hash(String stringToHash){
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(stringToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return hash;

    }

    //Validate password before login
    //*Issue* salt is randomized thus it cant be recreated so validating of password might have issue,
    // maybe just use normal hash instead of adding additional salt?
    // Salt is now generated from the username and UID of the user
    // To remove next commit If this is fine :)
    public boolean validatePassword(String password) {
        String hashedPassword = generateHash(password, getSalt());
        return hashedPassword.equals(this.Password);
    }

    // For writing to CSV TODO
    public String[] UserToArray() {
        return new String[]{this.UID, this.Username, this.Password, Salt, this.email, this.phone};
    }

    // For reading from CSV TODO
    public void ArrayToUser(String[] userArray) {
        this.UID = userArray[0];
        this.Username = userArray[1];
        this.Password = userArray[2];
        Salt = userArray[3];
        this.email = userArray[4];
        this.phone = userArray[5];

    }

    // For testing
    public static void main(String[] args){
        User test = new User("test", "123456", "test", "test", new Bank("test"));
        System.out.print(test.validatePassword("123456"));
        test.changePin();
        System.out.println("Enter new pin: ");
        Scanner sc = new Scanner(System.in);
        String newPin = sc.nextLine();
        System.out.println(test.validatePassword(newPin));
    }
}
