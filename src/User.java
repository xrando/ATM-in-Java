import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.sql.*;

public class User
{
    private String UID;
    private String Username;
    private String Password;
    private String Salt;
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
        this.UID = CurrentBank.generateNewUserUID();
        this.Salt = generateSalt(Username, this.UID);
        this.Password = generateHash(Password, Salt);
        this.Accounts = new ArrayList<Account>();
        this.loginStatus = false;
        System.out.printf("UserName: %s\nUID: %s",Username,UID);
    }

    public User(String username, String password, String email, String phone){
        Bank CurrentBank = new Bank("Bank of Testing");
        this.Username = username;
        this.email = email;
        this.phone = phone;
        this.UID = CurrentBank.generateNewUserUID();
        this.Salt = generateSalt(username, this.UID);
        this.Password = generateHash(password, Salt);
        this.Accounts = new ArrayList<Account>();
        this.loginStatus = false;

    }

    public User(){

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
        return this.UID;
    }

    //public void setSalt() {
    //    Salt = generateSalt();
    //}

    public String getSalt() {
        return this.Salt;
    }
    public boolean getLoginStatus() {
        return loginStatus;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone(){
        return phone;
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
        // Update Database
        // Probably better to update the database at the end of the session
        try {
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement("UPDATE Users SET Password = ? WHERE UID = ?");
            ps.setString(1, this.Password);
            ps.setString(2, this.UID);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }
    // Salt is now not randomized, but generated from the username and UID of the user
    public String generateSalt(String username, String UID) {
        String Salt = hash(username + UID);
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

    protected String[] getPasswordFromDatabase(String UID) {
        String getPasswordQuery = "SELECT password, salt FROM users WHERE uid = ?";
        String password = "";
        String Salt = "";
        //get password from database
        try {
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement(getPasswordQuery);
            ps.setString(1, UID);
            ResultSet rs = ps.executeQuery();
            password = rs.getString("password");
            Salt = rs.getString("salt");
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new String[]{password, Salt};
    }

    public boolean validatePassword(String password, String UID) {
        // get password and Salt from database
        String[] passwordAndSalt = getPasswordFromDatabase(UID);
        String hashedPassword = generateHash(password, passwordAndSalt[1]);
        System.out.println("Hashed Password: " + hashedPassword);

        String passwordFromDatabase = passwordAndSalt[0];
        System.out.println("Password from DB:" + passwordFromDatabase);

        return hashedPassword.equals(passwordFromDatabase);
    }
    public String CheckUserExist(String username){
        String sql = "SELECT UID FROM users WHERE Username = ?";
        String UID = "";
        try(Connection conn = sqliteDatabase.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            UID = rs.getString("UID");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (UID.equals("")) {
            System.out.println("User does not exist!");
            return null;
        }
        return UID;
    }

    public boolean checkUsername(String username){
        String sql = "SELECT Username FROM users WHERE Username = ?";
        String Username = "";
        try(Connection conn = sqliteDatabase.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            Username = rs.getString("Username");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return !Username.equals("");
    }

    public User Login(String username, String password) {
        //check if user exists
        String UID = CheckUserExist(username);
        if (UID == null) {
            return null;
        }
        //check if password is correct
        if (validatePassword(password, UID)) {
            System.out.println("Login successful!");
            //set login status to true
            try {
                Connection conn = sqliteDatabase.connect();
                PreparedStatement ps = conn.prepareStatement("UPDATE Users SET loginStatus = ? WHERE UID = ?");
                ps.setString(1, "1");
                ps.setString(2, UID);
                ps.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
            //return user object populated with data from database
            User user = new User();
            try{
                Connection conn = sqliteDatabase.connect();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE UID = ?");
                ps.setString(1, UID);
                ResultSet rs = ps.executeQuery();
                user.UID = rs.getString("UID");
                user.Username = rs.getString("Username");
                user.Password = rs.getString("Password");
                user.Salt = rs.getString("Salt");
                user.email = rs.getString("Email");
                user.phone = rs.getString("Phone");
                user.loginStatus = rs.getBoolean("loginStatus");
                conn.close();
            } catch (SQLException e) {
                LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
            return user;

        } else {
            System.out.println("Incorrect password!");
            return null;
        }
    }

    // Create new user
    public void CreateUser(){
        System.out.println("New User Registration");
        Scanner sc = new Scanner(System.in);

        //Username
        System.out.println("Enter username (Max 10 Alphanumeric Characters Only): ");
        String username = sc.nextLine();
        while (checkUsername(username) || !username.matches("[a-zA-Z0-9]+") || username.length() > 10 || username.length() < 1) {
            System.out.println("Invalid username!");
            System.out.println("Enter username (Max 10 Alphanumeric Characters Only): ");
            username = sc.nextLine();
        }

        //Pin
        System.out.println("Enter pin (6 digit NUMBERS only): ");
        String password = sc.nextLine();
        while (password.length() != 6 || !password.matches("[0-9]+") || password.length() < 1 || password.length() > 6) {
            System.out.println("Pin must be 6 digits long!");
            System.out.println("Enter pin: ");
            password = sc.nextLine();
        }

        //Email
        System.out.println("Enter a valid email (abc123@abc.com): ");
        String email = sc.nextLine();
        while (!email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+") || email.length() < 1) {
            System.out.println("Invalid email!");
            System.out.println("Enter a valid email (abc123@abc.com): ");
            email = sc.nextLine();
        }

        //Phone
        System.out.println("Enter phone number: ");
        String phone = sc.nextLine();
        // Check for Singapore Phone number starting from 8 or 9 and 8 digits long
        while (!phone.matches("^[89][0-9]{7}$") || phone.length() < 1) {
            System.out.println("Invalid phone number!");
            System.out.println("Enter phone number: ");
            phone = sc.nextLine();
        }

        User user = new User(username, password, email, phone);

        insertUser(user);
        System.out.println("User created!\n Proceed to login!");
    }

    // Insert user into database (New users only)
    public static void insertUser(User user) {
        String sql = "INSERT INTO users(UID, Username, Password, Salt, email, phone, loginStatus) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUID());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getSalt());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, "0");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Updates user in database (Existing users only)
    public static void updateUser(User user){
        String sql = "UPDATE users SET Username = ?, Password = ?, Salt = ?, email = ?, phone = ?, loginStatus = ? WHERE UID = ?";

        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getSalt());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPhone());
            pstmt.setString(6, user.getLoginStatus() ? "1" : "0");
            pstmt.setString(7, user.getUID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void logout(){
        this.setLoginStatus(false);
        updateUser(this);
    }

    private void setLoginStatus(boolean b) {
        this.loginStatus = b;
    }


    // For testing
    public static void main(String[] args){
        //User newUser = new User();
        //newUser.CreateUser();

        //Login test
        //User test = new User("test", "123456", "test", "test", new Bank("test"));
        //insertUser(test);

        //test Login
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = sc.nextLine();
        System.out.println("Enter pin: ");
        String password = sc.nextLine();

        // User is initialised with data from database after login
        User test2 = new User();
        test2 = test2.Login(username, password);
        System.out.println("UID: " + test2.getUID());
        System.out.println("Username: " + test2.getUsername());
        System.out.println("Password: " + test2.getPassword());
        System.out.println("Salt: " + test2.getSalt());
        System.out.println("Email: " + test2.getEmail());
        System.out.println("Phone: " + test2.getPhone());
        System.out.println("Login Status: " + test2.getLoginStatus());

        //test2.logout();
        System.out.println("Login Status: " + test2.getLoginStatus());

        // Outputs:
        // UID: 338467
        // Username: test
        // Password: dfb7664f26448841825721cec8b151d7
        // Salt: fbcaf11b8bbe8986ac7b78e6d9522682
        // Email: test
        // Phone: test
        // Login Status: true

        // Test User
        // Username: test
        // Password: 123123
    }

    // Notes:
    // 1. User to login with User ID instead?
    // 2. To add error handling for incorrect username and password

}
