package ATM.Bank;

import ATM.Utilities.LogHelper;
import ATM.Utilities.Helper;

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

    public String getUID() {
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

    public String changePin(String oldPin, String newPin) { //Use this for GUI
        // Get user Info from DB
        // If user is not logged in, return false
        if (!this.getLoginStatusFromDB(this.getUsername())){
            LogHelper.log(Level.SEVERE, this.getUsername() + " is not logged in.");
            return "User is not logged in.";
        }

        // If old pin is not correct, return false
        if (!this.getPassword().equals(generatePasswordHash(oldPin, this.Salt))) {
            LogHelper.log(Level.WARNING, this.getUsername() + " entered wrong old pin.");
            return "Old pin is not correct.";
        }

        // If new pin is not 6 digits, return false
        if(newPin.length() != 6) {
            LogHelper.log(Level.WARNING, this.getUsername() + " entered pin that is not 6 digits.");
            return "New pin must be 6 digits.";
        }

        if (newPin.equals(oldPin)) {
            LogHelper.log(Level.WARNING, this.getUsername() + " entered same pin as old pin.");
            return "New pin must be different from old pin.";
        }

        if (newPin.equals("0")) {
            LogHelper.log(Level.INFO, this.getUsername() + " entered '0' as new pin.");
            return "Pin change cancelled.";
        }

        this.Password = generatePasswordHash(newPin, getSalt());

        System.out.println("Pin changed successfully.");
        return "Pin changed successfully.";
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
            LogHelper.log(Level.SEVERE, e.getMessage(), e);
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

    public String getEmailFromDatabase(String UID){
        String getEmailQuery = "SELECT email FROM users WHERE uid = ?";
        String email = "";
        //get password from database
        try {
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement(getEmailQuery);
            ps.setString(1, UID);
            ResultSet rs = ps.executeQuery();
            email = rs.getString("email");
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return email;
    }

    /////////////////////////////////
    // Validation methods
    ////////////////////////////////
    public boolean validatePassword(String password, String UID) {
        // get password and Salt from database
        String[] passwordAndSalt = getPasswordFromDatabase(UID);
        String hashedPassword = generatePasswordHash(password, passwordAndSalt[1]);
        //System.out.println("Hashed Password: " + hashedPassword);

        String passwordFromDatabase = passwordAndSalt[0];
        //System.out.println("Password from DB:" + passwordFromDatabase);

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

    // These functions are used to validate user input
    public boolean ValidateUserName(String username){
        return !checkUsername(username) && username.matches("[a-zA-Z0-9]+") && username.length() <= 10 && username.length() >= 1;
    }

    public boolean ValidatePin(String pin){
        return pin.matches("[0-9]+") && pin.length() == 6;
    }

    public boolean ValidateEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$") && !(email.length() < 1);
    }

    // Checks for 8 digit Singapore phone numbers
    public boolean ValidatePhone(String phone){
        return phone.matches("^[89][0-9]{7}$") && !(phone.length() < 1);
    }

    /////////////////////////////////////////////////////////////////////////
    // User Creation Functions
    /////////////////////////////////////////////////////////////////////////

    // Create new user
    // Create User with username: 10 alphanumeric characters only
    // Create User with pin: 6 digit numbers only
    // Create User with email: Validated email address
    // Create User with phone: 8 digit (Singapore) numbers only
    // Update database with new user
    // Created User will proceed with Login
    public boolean CreateUserSeq(){
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
        System.out.println("Bank.User created!\n Proceed to login!");
        return true;
    }

    // Use if User entered email and phone
    public boolean CreateUser(String username, String password, String email, String phone){
        User user = new User(username, password, email, phone);
        // Validate user input
        System.out.println("Phone validation: " + ValidatePhone(phone));
        System.out.println("Email validation: " + ValidateEmail(email));
        System.out.println("Pin validation: " + ValidatePin(password));
        System.out.println("Username validation: " + ValidateUserName(username));

        if (ValidatePhone(phone) && ValidateEmail(email) && ValidatePin(password) && ValidateUserName(username)){
            user.UID = user.genUID();
            user.Salt = user.generateSalt(username, user.UID);
            user.Password = user.generatePasswordHash(password, user.Salt);
            insertUser(user);
            System.out.println("User created!\n Proceed to login!");
            return true;
        } else {
            System.out.println("Invalid input!");
            return false;
        }
    }

    public boolean CreateUser(String username, String password){
        User user = new User(username, password);
        //validate user input
        if (ValidatePin(password) && ValidateUserName(username)){
            user.UID = user.genUID();
            user.Salt = user.generateSalt(username, user.UID);
            user.Password = user.generatePasswordHash(password, user.Salt);
            insertUser(user);
            System.out.println("User created!\n Proceed to login!");
            return true;
        } else {
            System.out.println("Invalid input!");
            return false;
        }
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
            LogHelper.log(Level.SEVERE, e.getMessage());
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
            //this.Salt = rs.getString("Salt");
            this.UID = rs.getString("UID");
            //this.loginStatus = rs.getBoolean("loginStatus");
            this.email = rs.getString("email");
            this.phone = rs.getString("phone");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //Populate User.List
        Account TransactionAccount = new Account();
        this.Accounts = TransactionAccount.getTransactionAccount(this.UID);
        for (Account account: this.Accounts) {
            System.out.println("Account ID: " +account.getAccountID() + " Account Type: " + account.getAccountType());
        }
    }

    public boolean Login(){
        //check if user exists
        this.UID = CheckUserExist(this.Username);
        if (UID == null) {
            LogHelper.log(Level.SEVERE, "Failed Login attempt, Attempted Username: " + this.Username);
            return false;
        }
        // Check if user is already logged in
        if (this.loginStatus || getLoginStatusFromDB(this.Username)) {
            LogHelper.log(Level.SEVERE, "Attempted Relogin attempt, Attempted Username: " + this.Username);
            return false;
        }
        //check if password is correct
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
                LogHelper.log(Level.SEVERE, e.getMessage(), e);
            }
            this.loginStatus = true;
            return true;
        }
        else {
            System.out.println("Login failed!");
            return false;
        }
    }

    public boolean Login(String username, String password) {
        //check if user exists
        String UID = CheckUserExist(username);
        if (UID == null) {
            LogHelper.log(Level.SEVERE, "Failed Login attempt, Attempted Username: " + username);
            //return null;
        }
        // Check if user is already logged in
        if (this.loginStatus || getLoginStatusFromDB(username)) {
            LogHelper.log(Level.SEVERE, "Attempted Relogin attempt, Attempted Username: " + username);
            return false;
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
                LogHelper.log(Level.SEVERE, e.getMessage(), e);
            }
            //return user object populated with data from database
            //User user = new User();
            try{
                Connection conn = sqliteDatabase.connect();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE UID = ?");
                ps.setString(1, UID);
                ResultSet rs = ps.executeQuery();
                this.UID = rs.getString("UID");
                this.Username = rs.getString("Username");
                this.Password = rs.getString("Password");
                this.Salt = rs.getString("Salt");
                this.email = rs.getString("Email");
                this.phone = rs.getString("Phone");
                this.loginStatus = rs.getBoolean("loginStatus");
                conn.close();
                getUserFromDatabase();
            } catch (SQLException e) {
                LogHelper.log(Level.SEVERE, e.getMessage(), e);
            }

            return true;

        } else {
            System.out.println("Incorrect password!");
            return false;
        }
    }

    //Logout Function
    public boolean logout(){
        if (this.loginStatus == false){
            return true;
        }
        if (this.UID == null){
            return false;
        }

        if (this.loginStatus == true){
            this.setLoginStatus(false);
            this.updateUser();
            return true;
        }
        return false;
    }

    public boolean logout(User user){
        try {
            user.setLoginStatus(false);
            updateUser(user);
        } catch (Exception e){
            LogHelper.log(Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }

    protected void setLoginStatus(boolean b) {
        this.loginStatus = b;
    }

    //Forget Pin
    public boolean forgetPin(String name){
        //check if user exists
        String UID = CheckUserExist(name);

        if (UID == null) {
            LogHelper.log(Level.SEVERE, "Failed Pin Change attempt, Name: " + name);
            return false;
        }

        String email = getEmailFromDatabase(UID);

        if (email == null){
            return false;
        }

        String salt = getPasswordFromDatabase(UID)[1];
        // Generate a random password
        // Generate a 6 digit random number all digits should be different
        String clearPin = String.format("%06d", new Random().nextInt(999999));
        String newPassword = generatePasswordHash(clearPin, salt);
        // Send email to user with new password
        String subject = "A Password change is requested";
        String body = "Dear " + name + ",\n\n" +
                "Your new password is: " + clearPin + "\n\n" +
                "Please change your password after you login.\n\n" +
                "If you did not request a password change, please contact us immediately.\n\n" +
                "Thank you,\n"+
                "Pure Bank LTD\n"+
                "pureinc933@gmail.com\n\n"+
                "Please do not reply to this email as it is automatically generated.";
        // Send email
        Helper.SendMail(email, subject, body);

        // Update password in database
        try {
            Connection conn = sqliteDatabase.connect();
            PreparedStatement ps = conn.prepareStatement("UPDATE Users SET Password = ? WHERE UID = ?");
            ps.setString(1, newPassword);
            ps.setString(2, UID);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            LogHelper.log(Level.SEVERE, e.getMessage(), e);
        }

        return true;
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
        //test2.Login("test", "046903");
        //test2.changePin("046903", "123123");
        //test2.logout();

        //test2.forgetPin("test");

        test2.Login("test", "445286");
        test2.logout();
//        User test3 = new User("test3", "123456");
//        test3.Login();
//
//        test3.CreateUser();

        //test2.CreateUser("test5", "123123");

        //Create user test
        //User test3 = new User();

        // To access User Data
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
        //System.out.println("Login Status: " + test2.getLoginStatus());

        //System.out.println("test2 accounts" + accounts);
        //System.out.println("test2 accounts size" + accounts.size());
        //System.out.println("test2 accounts get(0)" + accounts.get(0));

//        System.out.println("test2 UID: " + test3.getUID());
//        List <Account> accounts = test2.getAccounts();
//        int tCount = 0; // To remove when transaction ID is implemented
//        // Accessing of accounts and transactions
//        // This is assuming that the user has 1 and only 1 account
//        for (Account account : accounts) {
//            System.out.println("ATM.ATM.Bank.Bank.Account Name: " + account.getAccountName() + "\n");
//            System.out.println("ATM.ATM.Bank.Bank.Account Balance: " + account.GetAccountBalance());
//            List<Transaction> transactions = account.getAccountTransactions();
//            for (Transaction transaction : transactions) {
//                System.out.print("ATM.ATM.Bank.Bank.Transaction Number\n");
//                System.out.println("ATM.ATM.Bank.Bank.Transaction ID: " + tCount);
//                System.out.println("ATM.ATM.Bank.Bank.Transaction Amount: " + transaction.getAmount());
//                System.out.println("ATM.ATM.Bank.Bank.Transaction Date: " + transaction.getTransactionDate());
//                System.out.println("ATM.ATM.Bank.Bank.Transaction Note: " + transaction.getTransactionNote());
//                tCount++; // To remove when transaction ID is implemented
//            }
//        }

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
        //Transaction Number
        //Transaction ID: 0
        //Transaction Amount: 300.45
        //Connection to SQLite has been established.
        //Transaction Date: 2023-02-10 03:01:10
        //Transaction Note: Salary
        //Transaction Number
        //Transaction ID: 1
        //Transaction Amount: 300.45
        //Connection to SQLite has been established.
        //Transaction Date: 2023-02-10 03:01:10
        //Transaction Note: Salary
        //Transaction Number
        //Transaction ID: 2
        //Transaction Amount: 300.45
        //Connection to SQLite has been established.
        //Transaction Date: 2023-02-10 03:01:10
        //Transaction Note: Salary


    }

}
