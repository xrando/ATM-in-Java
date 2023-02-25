package ATM.Bank;

import ATM.Utilities.LogHelper;

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
        this.UID = genUID();
        this.Salt = generateSalt(Username, this.UID);
        this.Password = generatePasswordHash(Password, Salt);
        this.Accounts = new ArrayList<Account>();
        this.loginStatus = false;
        System.out.printf("UserName: %s\nUID: %s",Username,UID);
    }

    public User(String username, String password, String email, String phone){
        this.Username = username;
        this.email = email;
        this.phone = phone;
        this.UID = genUID();
        this.Salt = generateSalt(username, this.UID);
        this.Password = password;
        //this.Password = generatePasswordHash(password, Salt);
        this.loginStatus = false;
        //ATM.Bank.Bank CurrentBank = new ATM.Bank.Bank("ATM.Bank.Bank of Testing");
        this.Accounts = new ArrayList<Account>();

    }

    public User(String username, String password){
        this.Username = username;
        this.email = " ";
        this.phone = " ";
        //this.UID = "9999";
        //this.Salt = generateSalt(username, this.UID);
        //this.Password = generatePasswordHash(password, Salt);
        this.Password = password;
        this.loginStatus = false;
        this.Accounts = new ArrayList<Account>();

    }

    public User(){

    }

    //Add an account for user
    public void AddAccount(Account NewAccount)
    {
        this.Accounts.add(NewAccount);
    }
    public String getUsername() {
        return Username;
    }

    protected void setUsername(String username) {
        this.Username = username;
    }

    protected String getPassword() {
        return Password;
    }

    protected void setPassword(String password) {
        Password = generatePasswordHash(password, Salt);
    }

    public List<Account> getAccounts() {
        return Accounts;
    }

    protected void setAccounts(List<Account> userAccounts) {
        Accounts = userAccounts;
    }

    protected String getUID() {
        return this.UID;
    }

    public String getSalt() {
        return this.Salt;
    }
    public boolean getLoginStatus() {
        return loginStatus;
    }

    public boolean getLoginStatusFromDB(String username) {
        String query = "Select loginStatus from users where username = ?";
        boolean loginStatus = false;
        try {
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            loginStatus = rs.getBoolean("loginStatus");
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return loginStatus;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone(){
        return phone;
    }

    // Returns a incremented UID
    public String genUID(){
        String UID = "";
        String query = "Select max(UID) as MAXUID from users";

        try {
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            UID = rs.getString("MAXUID");
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // Increment UID
        return String.valueOf(Integer.parseInt(UID) + 1);
    }

    public boolean changePin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter old pin (or enter '0' to cancel): ");
        String oldPin = sc.nextLine();

        if (oldPin.equals("0")) {
            System.out.println("Pin change cancelled.");
            return false;
        }
        while (!this.getPassword().equals(generatePasswordHash(oldPin, getSalt()))) {
            System.out.print("Pin does not match!\nEnter '0' to cancel\nEnter old pin: ");
            oldPin = sc.nextLine();
        }

        System.out.print("Enter new pin (or enter '0' to cancel): ");
        String newPin = sc.nextLine();
        if (newPin.equals("0")) {
            System.out.println("Pin change cancelled.");
            return false;
        }

        while(newPin.length() != 6) {
            if (newPin.equals("0")) {
                System.out.println("Pin change cancelled.");
                return false;
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
        this.Password = generatePasswordHash(newPin, getSalt());

        return true;
    }

    public boolean changePin(String oldPin, String newPin) {
        // Get user Info from DB
        // If user is not logged in, return false
        if (!this.getLoginStatusFromDB(this.getUsername())){
            return false;
        }

        // If old pin is not correct, return false
        if (!this.getPassword().equals(generatePasswordHash(oldPin, this.Salt))) {
            System.out.println(getSalt());
            System.out.println(this.Salt);
            System.out.println(generatePasswordHash(oldPin, this.Salt));
            System.out.println(this.getPassword());
            return false;
        }

        // If new pin is not 6 digits, return false
        if(newPin.length() != 6) {
            return false;
        }

        this.Password = generatePasswordHash(newPin, getSalt());

        return true;
    }


    public String generateSalt(String username, String UID) {
        String Salt = hash(username + UID);
        return Salt;
    }

    public String generatePasswordHash(String password, String salt) {
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
        System.out.printf("\n\n%s's All ATM.ATM.Bank.Bank.Account Summary\n",this.Username);
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

    public List<Account> getAccountsFromDatabase(String UID){
        List<Account> Accounts = new ArrayList<>();
        try{
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement("SELECT accountID FROM accounts WHERE userID = ?");
            ps.setString(1, this.UID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getInt("accountID"));
                //Add to AccountTransactions
                Accounts.add(new Account(rs.getInt("accountID")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.Accounts = Accounts;
        return Accounts;
    }

    public String[] getPasswordFromDatabase(String UID) {
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
        String hashedPassword = generatePasswordHash(password, passwordAndSalt[1]);
        System.out.println("Hashed Password: " + hashedPassword);

        String passwordFromDatabase = passwordAndSalt[0];
        System.out.println("Password from DB:" + passwordFromDatabase);

        return hashedPassword.equalsIgnoreCase(passwordFromDatabase);
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
            System.out.println("ATM.ATM.Bank.Bank.User does not exist!");
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

    public boolean Login(){
        //check if user exists
        this.UID = CheckUserExist(this.Username);
        if (UID == null) {
            LogHelper.LOGGER.log(Level.SEVERE, "Failed Login attempt, Attempted Username: " + this.Username);
            return false;
        }
        //check if password is correct
        //this.Password = generatePasswordHash(this.Password, generateSalt(this.Username, this.UID));
        //System.out.print("Password: " + this.Password);
        //System.out.println("UID: " + this.UID);
        if (validatePassword(this.Password, this.UID)) {
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
            return true;
        }
        else {
            System.out.println("Login failed!");
            return false;
        }
    }

    public void getUserFromDatabase(){
        String UID = this.UID;

        String sql = "SELECT * FROM users WHERE UID = ?";
        User user = null;
        try(Connection conn = sqliteDatabase.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, UID);
            ResultSet rs = pstmt.executeQuery();
            this.Username = rs.getString("Username");
            this.Password = rs.getString("Password");
            this.Salt = rs.getString("Salt");
            this.UID = rs.getString("UID");
            this.loginStatus = rs.getBoolean("loginStatus");
            this.email = rs.getString("email");
            this.phone = rs.getString("phone");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean Login(String username, String password) {
        //check if user exists
        String UID = CheckUserExist(username);
        if (UID == null) {
            LogHelper.LOGGER.log(Level.SEVERE, "Failed Login attempt, Attempted Username: " + username);
            //return null;
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
            //User user = new User();
            try{
                Connection conn = sqliteDatabase.connect();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE UID = ?");
                ps.setString(1, UID);
                ResultSet rs = ps.executeQuery();
                //user.UID = rs.getString("UID");
                //user.Username = rs.getString("Username");
                //user.Password = rs.getString("Password");
                //user.Salt = rs.getString("Salt");
                //user.email = rs.getString("Email");
                //user.phone = rs.getString("Phone");
                //user.loginStatus = rs.getBoolean("loginStatus");
                this.UID = rs.getString("UID");
                this.Username = rs.getString("Username");
                this.Password = rs.getString("Password");
                this.Salt = rs.getString("Salt");
                this.email = rs.getString("Email");
                this.phone = rs.getString("Phone");
                this.loginStatus = rs.getBoolean("loginStatus");
                conn.close();
            } catch (SQLException e) {
                LogHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

            //user.Accounts = user.getAccountsFromDatabase(user.UID);
            return true;
            //System.out.println("ATM.ATM.Bank.Bank.User Accounts: " + user.Accounts);
            //return user;

        } else {
            System.out.println("Incorrect password!");
            //return null;
            return false;
        }
    }

    // Create new user
    // Create ATM.ATM.Bank.Bank.User with username: 10 alphanumeric characters only
    // Create ATM.ATM.Bank.Bank.User with pin: 6 digit numbers only
    // Create ATM.ATM.Bank.Bank.User with email: Validated email address
    // Create ATM.ATM.Bank.Bank.User with phone: 8 digit (Singapore) numbers only
    // Update database with new user
    // Created ATM.ATM.Bank.Bank.User will proceed with Login
    public boolean CreateUserSeq(){
        System.out.println("New ATM.ATM.Bank.Bank.User Registration");
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
        System.out.println("ATM.ATM.Bank.Bank.User created!\n Proceed to login!");
        return true;
    }

    public boolean CreateUser(String username, String password, String email, String phone){
        User user = new User(username, password, email, phone);
        insertUser(user);
        System.out.println("ATM.ATM.Bank.Bank.User created!\n Proceed to login!");
        return true;
    }

    public boolean CreateUser(String username, String password){
        User user = new User(username, password);
        user.UID = user.genUID();
        user.Salt = user.generateSalt(username, user.UID);
        user.Password = user.generatePasswordHash(password, user.Salt);
        insertUser(user);
        System.out.println("User created!\n Proceed to login!");
        return true;
    }

    // Create new user with current user object
    public boolean CreateUser(){
        insertUser(this);
        return true;
    }

    // Insert user into database (New users only)
    public void insertUser(User user) {
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
    public boolean updateUser(User user){
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
            return false;
        }
        return true;
    }
    public void updateUser(){
        String sql = "UPDATE users SET Username = ?, Password = ?, Salt = ?, email = ?, phone = ?, loginStatus = ? WHERE UID = ?";

        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.Username);
            pstmt.setString(2, this.Password);
            pstmt.setString(3, this.Salt);
            pstmt.setString(4, this.email);
            pstmt.setString(5, this.phone);
            pstmt.setString(6, this.loginStatus ? "1" : "0");
            pstmt.setString(7, this.UID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LogHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    //Logout Function
    public boolean logout(){
        this.setLoginStatus(false);
        this.updateUser();

        return !this.loginStatus;
    }

    public boolean logout(User user){
        try {
            user.setLoginStatus(false);
            updateUser(user);
        } catch (Exception e){
            LogHelper.LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }

    protected void setLoginStatus(boolean b) {
        this.loginStatus = b;
    }


    // For testing
    public static void main(String[] args){
        // To create new user
        //ATM.ATM.Bank.Bank.User newUser = new ATM.ATM.Bank.Bank.User();
        //newUser.CreateUser();

        //ATM.ATM.Bank.Bank.User test = new ATM.ATM.Bank.Bank.User("test", "123456", "test", "test", new ATM.Bank.Bank("test"));
        //insertUser(test);

        //To Login
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Enter username: ");
        //String username = sc.nextLine();
        //System.out.println("Enter pin: ");
        //String password = sc.nextLine();

        // ATM.ATM.Bank.Bank.User is initialised with data from database after login
        User test2 = new User();
        User test3 = new User("test3", "123456");
        test3.Login();

        //test3.CreateUser();

        boolean a = test2.Login("test", "123123");
        System.out.print(a);


        // To access ATM.ATM.Bank.Bank.User Data
        //System.out.println("UID: " + test2.getUID());
        //System.out.println("Username: " + test2.getUsername());
        //System.out.println("Password: " + test2.getPassword());
        //System.out.println("Salt: " + test2.getSalt());
        //System.out.println("Email: " + test2.getEmail());
        //System.out.println("Phone: " + test2.getPhone());
        //System.out.println("Login Status: " + test2.getLoginStatus());

        // Change Pin
        //test2.changePin();
        //System.out.println("Login Status: " + test2.getLoginStatus());

        // Logout
        //System.out.println("logging out");
        //test2.logout();
        //System.out.println("Login Status: " + test2.getLoginStatus());

        //System.out.println("test2 accounts" + accounts);
        //System.out.println("test2 accounts size" + accounts.size());
        //System.out.println("test2 accounts get(0)" + accounts.get(0));

        System.out.println("test2 UID: " + test3.getUID());
        List <Account> accounts = test2.getAccounts();
        int tCount = 0; // To remove when transaction ID is implemented
        // Accessing of accounts and transactions
        // This is assuming that the user has 1 and only 1 account
        for (Account account : accounts) {
            System.out.println("ATM.ATM.Bank.Bank.Account Name: " + account.getAccountName() + "\n");
            System.out.println("ATM.ATM.Bank.Bank.Account Balance: " + account.GetAccountBalance());
            List<Transaction> transactions = account.getAccountTransactions();
            for (Transaction transaction : transactions) {
                System.out.print("ATM.ATM.Bank.Bank.Transaction Number\n");
                System.out.println("ATM.ATM.Bank.Bank.Transaction ID: " + tCount);
                System.out.println("ATM.ATM.Bank.Bank.Transaction Amount: " + transaction.getAmount());
                System.out.println("ATM.ATM.Bank.Bank.Transaction Date: " + transaction.getTransactionDate());
                System.out.println("ATM.ATM.Bank.Bank.Transaction Note: " + transaction.getTransactionNote());
                tCount++; // To remove when transaction ID is implemented
            }
        }

        // Outputs:
        // UID: 1
        // Username: test
        // Password: dfb7664f26448841825721cec8b151d7
        // Salt: fbcaf11b8bbe8986ac7b78e6d9522682
        // Email: test
        // Phone: test
        // Login Status: true

        // Test ATM.ATM.Bank.Bank.User
        // Username: test
        // Password: 123123
        
        // Transactions and Accounts Output
        //Connection to SQLite has been established.
        //ATM.ATM.Bank.Bank.Account Balance: 901.3499999999999
        //Connection to SQLite has been established.
        //ATM.ATM.Bank.Bank.Transaction Number
        //ATM.ATM.Bank.Bank.Transaction ID: 0
        //ATM.ATM.Bank.Bank.Transaction Amount: 300.45
        //Connection to SQLite has been established.
        //ATM.ATM.Bank.Bank.Transaction Date: 2023-02-10 03:01:10
        //ATM.ATM.Bank.Bank.Transaction Note: Salary
        //ATM.ATM.Bank.Bank.Transaction Number
        //ATM.ATM.Bank.Bank.Transaction ID: 1
        //ATM.ATM.Bank.Bank.Transaction Amount: 300.45
        //Connection to SQLite has been established.
        //ATM.ATM.Bank.Bank.Transaction Date: 2023-02-10 03:01:10
        //ATM.ATM.Bank.Bank.Transaction Note: Salary
        //ATM.ATM.Bank.Bank.Transaction Number
        //ATM.ATM.Bank.Bank.Transaction ID: 2
        //ATM.ATM.Bank.Bank.Transaction Amount: 300.45
        //Connection to SQLite has been established.
        //ATM.ATM.Bank.Bank.Transaction Date: 2023-02-10 03:01:10
        //ATM.ATM.Bank.Bank.Transaction Note: Salary

    }

}