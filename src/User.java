import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.*;

public class User {
    private String UID;
    private String Username;
    private String Password;
    private String Salt;
    private String email;
    private String phone;
    private List<Account> Accounts;

    public User(String Username, String Password, String email, String phone) {
        this.Username = Username;
        this.email = email;
        this.phone = phone;
        this.Salt = generateSalt();
        this.Password = generateHash(Password, this.Salt);
        this.UID = generateUID();
        this.Accounts = new ArrayList<Account>();
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = generateHash(password, this.Salt);
    }

    public List<Account> getAccounts() {
        return Accounts;
    }

    public void setAccounts(List<Account> userAccounts) {
        Accounts = userAccounts;
    }

    public String getUID() {
        return UID;
    }

    public void setUID() {
        UID = generateUID();
    }

    public void setSalt() {
        Salt = generateSalt();
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

    public String generateUID() {
        Random rand = new Random();
        StringBuilder UID = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            UID.append(rand.nextInt(10));
        }
        return UID.toString();
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
}
