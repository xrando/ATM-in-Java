import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.*;

public class User
{
    private String UID;
    private String Username;
    private String Password;
    private String Salt;
    private String email;
    private String phone;
    private List<Account> Accounts;
    //Create a new user
    public User(String Username, String Password, String email, String phone, Bank CurrentBank)
    {
        this.Username = Username;
        this.email = email;
        this.phone = phone;
        this.Salt = generateSalt();
        this.Password = generateHash(Password, this.Salt);
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
        Password = generateHash(password, this.Salt);
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
        this.Salt = generateSalt();
    }

    public String getSalt() {
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
    public String generateSalt() {
        Random rand = new Random();
        StringBuilder Salt = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            Salt.append(rand.nextInt(10));
        }
        return Salt.toString();
    }

    public String generateHash(String password, String salt) {
        //MD5 hash
        String stringToHash = password + salt;
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
            e.printStackTrace();
        }

        return hash;
    }
    //Validate password before login
    //*Issue* salt is randomized thus it cant be recreated so validating of password might have issue,
    // maybe just use normal hash instead of adding additional salt?
    public boolean validatePassword(String password)
    {
        return false;
    }
}
