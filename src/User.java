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
    private boolean loginStatus;
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
        this.loginStatus = false;
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
        System.out.print("\nEnter old pin (or enter '0' to cancel): ");
        String oldPin = sc.nextLine();

        if (oldPin.equals("0")) {
            System.out.println("Pin change cancelled.");
            return;
        }
        while (!this.getPassword().equals(generateHash(oldPin, getSalt()))) {
            System.out.print("Pin does not match!\nEnter '0' to cancel\nEnter old pin: ");
            oldPin = sc.nextLine();
        }

        System.out.print("Enter new pin (or enter '0' to cancel): ");
        String newPin = sc.nextLine();
        if (newPin.equals("0")) {
            System.out.println("Pin change cancelled.");
            return;
        }

        while(newPin.length() != 6) {
            if (newPin.equals("0")) {
                System.out.println("Pin change cancelled.");
                return;
            }
            System.out.print("Pin must be 6 digits!\nEnter new pin(or enter '0' to cancel): ");
            newPin = sc.nextLine();
        }

        System.out.print("Confirm new pin: ");
        String newPin2 = sc.nextLine();

        while (!newPin.equals(newPin2)) {
            System.out.println("Pin does not match!\nConfirm new pin: ");
            newPin2 = sc.nextLine();
        }
        this.Password = generateHash(newPin, getSalt());

    }
    // Salt is now not randomized, but generated from the username and UID of the user
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
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return hash;

    }

    public boolean validatePassword(String password) {
        String hashedPassword = generateHash(password, getSalt());
        return hashedPassword.equals(this.Password);
    }

    public void PrintAllAccountSummary()
    {
        System.out.printf("\n\n%s's All Account Summary\n",this.Username);
        for(int i = 0;i<this.Accounts.size();i++)
        {
            System.out.printf("%d) %s\n",i+1,this.Accounts.get(i).getAccountSummary());
        }
        System.out.println();
    }
    public void PrintAccountTransactionHistory(int AccountIndex)
    {
        this.Accounts.get(AccountIndex).PrintTransactionHistory();
    }
    //Get balance of particular account
    public double GetAccountBalance(int AccountIndex)
    {
        return this.Accounts.get(AccountIndex).GetAccountBalance();
    }
    //Get UID of particular account
    public String GetAccountUID(int AccountIndex)
    {
        return this.Accounts.get(AccountIndex).getUID();
    }
    public void AddAccountTransaction(int AccountIndex, double Amount, String TransactionNote)
    {
        this.Accounts.get(AccountIndex).AddTransaction(Amount,TransactionNote);
    }

    public boolean Login(String name, String password) {
        if (this.Username.equals(name) && validatePassword(password)) {
            this.loginStatus = true;
            return true;
        }
        return false;
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
        System.out.println("Enter pin: ");
        Scanner sc = new Scanner(System.in);
        String newPin = sc.nextLine();
        System.out.println(test.validatePassword(newPin));
    }
}
