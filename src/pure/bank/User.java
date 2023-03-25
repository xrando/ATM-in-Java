package pure.bank;

import pure.util.EmailHelper;
import pure.util.LogHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * User Class <br>
 * All User Defined Methods are defined here
 */
public class User {
    private String UID;
    private String Username;
    private String Password;
    private String Salt;
    private String email;
    private String phone;
    private List<Account> Accounts;
    private boolean loginStatus;

    //Create a new user
//    public User(String Username, String Password, String email, String phone, Bank CurrentBank) {
//        this.Username = Username;
//        this.email = email;
//        this.phone = phone;
//        this.UID = genUID();
//        //this.Salt = generateSalt();
//        //this.Password = generatePasswordHash(Password, Salt);
//        this.Accounts = new ArrayList<Account>();
//        this.loginStatus = false;
//        System.out.printf("UserName: %s\nUID: %s", Username, UID);
//    }

//    public User(String username, String password, String email, String phone) {
//        this.Username = username;
//        this.email = email;
//        this.phone = phone;
//        this.UID = genUID();
//        //this.Salt = generateSalt();
//        //this.Password = generatePasswordHash(Password, Salt);
//        //this.Password = generatePasswordHash(password, Salt);
//        this.loginStatus = false;
//        //ATM.Bank.Bank CurrentBank = new ATM.Bank.Bank("ATM.Bank.Bank of Testing");
//        this.Accounts = new ArrayList<Account>();
//
//    }

    /**
     * User Constructor
     * @param username - Username of the user
     * @param password - Password of the user
     */
    public User(String username, String password) {
        this.Username = username;
        this.email = " ";
        this.phone = " ";
        //this.UID = "9999";
        this.Salt = generateSalt();
        //this.Password = generatePasswordHash(password, Salt);
        this.Password = password;
        this.loginStatus = false;
        this.Accounts = new ArrayList<Account>();

    }

    /**
     * Default User Constructor
     */
    public User() {

    }

//    public static void genUsers() {
//        String email = "";
//        String password = "123123";
//        // Create 100 users
//        String[] names = {"john", "james", "jack", "jill", "jane", "joe", "jenny", "jerry", "bob", "jill", "tom", "brandon"};
//        for (int i = 0; i < 100; i++) {
//            int ranIndex = new SecureRandom().nextInt(names.length);
//            String name = names[ranIndex] + i;
//            // Create email for certain users
//            if (ranIndex % 8 == 0) {
//                email = "pureinc933@gmail.com";
//            } else {
//                email = name + "@gmail.com";
//            }
//
//            User user = new User();
//            //user.CreateUser(name, password, email, 1)
//        }
//        System.out.println("Done");
//    }
//
//    public static void genAccount() {
//        int accountType = 0;
//        Account account = new Account();
//        // Create 100 accounts assuming there are 0 - 99 userID
//        for (int i = 0; i < 100; i++) {
//            int ranIndex = new SecureRandom().nextInt(3);
//            accountType = (ranIndex - 1);
//            account.createAccount(accountType, String.valueOf(i));
//        }
//        System.out.println("Done");
//    }
//
//    public static void genTransferTransactions() {
//        String[] depositReason = {"Deposit", "Salary", "Part-time", "Cashback", "Reimbursement", "Transfer"};
//        for (int i = 0; i < 100; i++) {
//            int amount = new SecureRandom().nextInt(10000);
//            int reasonI = new SecureRandom().nextInt(depositReason.length);
//            int ranIndex = new SecureRandom().nextInt(100);
//            int ranIndex2 = new SecureRandom().nextInt(100);
//            if (ranIndex == ranIndex2) {
//                ranIndex2 = new SecureRandom().nextInt(100);
//            } else {
//                Account account = new Account();
//                Transaction transaction = new Transaction(amount, depositReason[reasonI], String.valueOf(ranIndex + 1), String.valueOf(ranIndex2 + 1));
//                transaction.AddTransactionToSQL(transaction, account);
//            }
//        }
//        System.out.println("Done");
//    }
//
//    public static void genPosTransactions() {
//        String payee = "";
//        String[] depositReason = {"Deposit", "Salary", "Part-time", "Cashback", "Reimbursement", "Transfer"};
//        for (int i = 0; i < 100; i++) {
//            int amount = new SecureRandom().nextInt(5000);
//            int reasonI = new SecureRandom().nextInt(depositReason.length);
//            int ranIndex = new SecureRandom().nextInt(100);
//            Account account = new Account();
//            Transaction transaction = new Transaction(amount, depositReason[reasonI], String.valueOf(ranIndex + 1));
//            transaction.AddTransactionToSQL(transaction, account);
//        }
//        System.out.println("Done");
//    }
//
    // For testing
//    public static void main(String[] args) {
//
//        //genUsers();
//        //genAccount();
//        //genPosTransactions();
//        //genTransferTransactions();
//        // To create new user
//        //ATM.ATM.Bank.Bank.User newUser = new ATM.ATM.Bank.Bank.User();
//        //newUser.CreateUser();
//
//        //ATM.ATM.Bank.Bank.User test = new ATM.ATM.Bank.Bank.User("test", "123456", "test", "test", new ATM.Bank.Bank("test"));
//        //insertUser(test);
//
//        //To Login
//        //Scanner sc = new Scanner(System.in);
//        //System.out.println("Enter username: ");
//        //String username = sc.nextLine();
//        //System.out.println("Enter pin: ");
//        //String password = sc.nextLine();
//
//        // ATM.ATM.Bank.Bank.User is initialised with data from database after login
//          User test2 = new User();
//        // test2.forgetPin("test");
//        test2.Login("test5", "123123");
//        test2.getUserFromDatabase();
//         Account account = new Account();
//        System.out.println(test2.Accounts.toString());
//        account = test2.Accounts.get(0);
//        // account.changeTransactionLimit(5000);
//        // account.updateAccount();
//        System.out.println("Limit" + account.getTransactionLimit());
//        System.out.println("Balance: "+account.GetAccountBalance());
//        Transaction transactionDetail = new Transaction(-7000,"Over Limit","1","2");
//        System.out.println(transactionDetail.AddTransactionToSQL(transactionDetail,account));
//        System.out.println("Balance: "+account.GetAccountBalance());
//
//        //test2.changePin("135518", "123123");
//        test2.logout();
//
//        //test2.forgetPin("test");
//
//        //User test3 = new User();
//        //test3.Login("test5", "123123");
//        //test2.forgetPin("test")
////
//        //test3.CreateUser();
//
//        //test2.CreateUser("test5", "123123");
//
//        //Create user test
//        //User test3 = new User();
//
//        // To access User Data
//        //System.out.println("UID: " + test2.getUID());
//        //System.out.println("Username: " + test2.getUsername());
//        //System.out.println("Password: " + test2.getPassword());
//        //System.out.println("Salt: " + test2.getSalt());
//        //System.out.println("Email: " + test2.getEmail());
//        //System.out.println("Phone: " + test2.getPhone());
//        //System.out.println("Login Status: " + test2.getLoginStatus());
//
//        // Change Pin
//        //test2.changePin();
//        //System.out.println("Login Status: " + test2.getLoginStatus());
//
//        // Logout
//        //System.out.println("logging out");
//        //System.out.println("Login Status: " + test2.getLoginStatus());
//
//        //System.out.println("test2 accounts" + accounts);
//        //System.out.println("test2 accounts size" + accounts.size());
//        //System.out.println("test2 accounts get(0)" + accounts.get(0));
//
////        System.out.println("test2 UID: " + test3.getUID());
////        List <Account> accounts = test2.getAccounts();
////        int tCount = 0; // To remove when transaction ID is implemented
////        // Accessing of accounts and transactions
////        // This is assuming that the user has 1 and only 1 account
////        for (Account account : accounts) {
////            System.out.println("ATM.ATM.Bank.Bank.Account Name: " + account.getAccountName() + "\n");
////            System.out.println("ATM.ATM.Bank.Bank.Account Balance: " + account.GetAccountBalance());
////            List<Transaction> transactions = account.getAccountTransactions();
////            for (Transaction transaction : transactions) {
////                System.out.print("ATM.ATM.Bank.Bank.Transaction Number\n");
////                System.out.println("ATM.ATM.Bank.Bank.Transaction ID: " + tCount);
////                System.out.println("ATM.ATM.Bank.Bank.Transaction Amount: " + transaction.getAmount());
////                System.out.println("ATM.ATM.Bank.Bank.Transaction Date: " + transaction.getTransactionDate());
////                System.out.println("ATM.ATM.Bank.Bank.Transaction Note: " + transaction.getTransactionNote());
////                tCount++; // To remove when transaction ID is implemented
////            }
////        }
//
//        // Outputs:
//        // UID: 1
//        // Username: test
//        // Password: dfb7664f26448841825721cec8b151d7
//        // Salt: fbcaf11b8bbe8986ac7b78e6d9522682
//        // Email: test
//        // Phone: test
//        // Login Status: true
//
//        // Test ATM.ATM.Bank.Bank.User
//        // Username: test
//        // Password: 123123
//
//        // Transactions and Accounts Output
//        //Connection to SQLite has been established.
//        //ATM.ATM.Bank.Bank.Account Balance: 901.3499999999999
//        //Connection to SQLite has been established.
//        //Transaction Number
//        //Transaction ID: 0
//        //Transaction Amount: 300.45
//        //Connection to SQLite has been established.
//        //Transaction Date: 2023-02-10 03:01:10
//        //Transaction Note: Salary
//        //Transaction Number
//        //Transaction ID: 1
//        //Transaction Amount: 300.45
//        //Connection to SQLite has been established.
//        //Transaction Date: 2023-02-10 03:01:10
//        //Transaction Note: Salary
//        //Transaction Number
//        //Transaction ID: 2
//        //Transaction Amount: 300.45
//        //Connection to SQLite has been established.
//        //Transaction Date: 2023-02-10 03:01:10
//        //Transaction Note: Salary
//
//
//    }

    //Add an account for user
//    public void AddAccount(Account NewAccount) {
//        this.Accounts.add(NewAccount);
//    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public List<Account> getAccounts() {
        return Accounts;
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

    protected void setLoginStatus(boolean b) {
        this.loginStatus = b;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method is used to get the login status of a user from the database
     * @param username The username of the user
     * @return True if the user is logged in, false if the user is logged out
     */
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

    /**
     * This method is used to generate a UID for a new user <br>
     * This method will select the maximum UID from the database and increment it by 1 used when a new user is created
     * @return String UID
     */
    public String genUID() {
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

    /**
     * This method is used to change the pin of a user
     * Both parameters will be sanitized and verified before being used
     * Email will be sent to user's email address upon successful pin change
     * @param oldPin User's Current Pin
     * @param newPin User's New Pin
     * @return String Message
     */
    public String changePin(String oldPin, String newPin) { //Use this for GUI
        // Get user Info from DB
        // If user is not logged in, return false
        if (!this.getLoginStatusFromDB(this.getUsername())) {
            LogHelper.log(Level.SEVERE, this.getUsername() + " is not logged in.");
            return "User is not logged in.";
        }

        // If old pin is not correct, return false
        if (!this.getPassword().equals(generatePasswordHash(oldPin, this.Salt))) {
            LogHelper.log(Level.WARNING, this.getUsername() + " entered wrong old pin.");
            return "Old pin is not correct.";
        }

        // If new pin is not 6 digits, return false
        if (newPin.length() != 6) {
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

        // Send Email
        String subject = "Pure Bank LTD - Password Change";
        String body = "Dear " + this.getUsername() + ",\n\n" +
                "Your password has been changed.\n\n" +
                "If you did not request a password change, please contact us immediately.\n\n" +
                "Thank you,\n" +
                "Pure Bank LTD\n" +
                "pureinc933@gmail.com\n\n" +
                "Please do not reply to this email as it is automatically generated.";
        // Send email
        EmailHelper.SendMail(email, subject, body);


        System.out.println("Pin changed successfully.");
        return "Pin changed successfully.";
    }

    /**
     * This method is used to generate a salt for a new user with BCrypt <br>
     * Salt generation will use the default log_rounds (10)
     * @return String Salt
     */
    public String generateSalt() {
        return BCrypt.gensalt();
    }

    /**
     * This method is used to generate a salt for a new user <br>
     * This method uses BCrypt to generate the password hash
     * @param password User's password in plain text
     * @param salt User's salt
     * @return Password hash in String
     */
    public String generatePasswordHash(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

//    public List<Account> getAccountsFromDatabase(String UID) {
//        List<Account> Accounts = new ArrayList<>();
//        try {
//            Connection conn = sqliteDatabase.connect();
//            PreparedStatement ps = conn.prepareStatement("SELECT accountID FROM accounts WHERE userID = ?");
//            ps.setString(1, this.UID);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                System.out.println(rs.getInt("accountID"));
//                //Add to AccountTransactions
//                Accounts.add(new Account(rs.getInt("accountID")));
//            }
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        this.Accounts = Accounts;
//        return Accounts;
//    }

    /**
     * This method is used to get the password and salt from the database <br>
     * First element of the array is the password
     * Second element of the array is the salt
     * @param UID User's UID
     * @return String[] Password and Salt
     */
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

    /**
     * This method is used to get the email from the database <br>
     * Used to send email to user
     * @param UID User's UID
     * @return String Email of the user from the database
     */
    public String getEmailFromDatabase(String UID) {
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

    /**
     * This method is used to validate the password <br>
     * The UID will be used to get the password and salt from the database <br>
     * The password will be hashed using BCrypt and salted with the salt from the database
     * @param password User's password in plain text
     * @param UID User's UID
     * @return boolean True if password is correct, false if password is incorrect
     */
    public boolean validatePassword(String password, String UID) {
        // get password and Salt from database
        String[] passwordAndSalt = getPasswordFromDatabase(UID);
        //System.out.println(passwordAndSalt[0]);
        //System.out.println(passwordAndSalt[1]);
        //System.out.println("Password: " + password);
        String hashedPassword = BCrypt.hashpw(password, passwordAndSalt[1]);
        //System.out.println("Hashed Password: " + hashedPassword);

        String passwordFromDatabase = passwordAndSalt[0];
        //System.out.println("Password from DB:" + passwordFromDatabase);

        return hashedPassword.equalsIgnoreCase(passwordFromDatabase);
    }

    /**
     * This method is used to check if the user already exists in the database <br>
     * If the user does not exist, the method will return null <br>
     * If the user exists, the method will return the UID of the user
     * @param username Username of the user
     * @return String UID of the user or null if the user does not exist
     */
    public String CheckUserExist(String username) {
        String sql = "SELECT UID FROM users WHERE Username = ?";
        String UID = "";
        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

    /**
     * This method is used to check if the username already exists in the database <br>
     * If the username does not exist, the method will return false <br>
     * If the username exists, the method will return true <br>
     * Used during user creation
     * @param username Username to check
     * @return boolean True if username exists, false if username does not exist
     */
    public boolean checkUsername(String username) {
        String sql = "SELECT Username FROM users WHERE Username = ?";
        String Username = "";
        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            Username = rs.getString("Username");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return !Username.equals("");
    }

    /**
     * This method is used to validate the username must be alphanumeric and less than 10 characters <br>
     * The username must not already exist in the database <br>
     * Used during user creation <br>
     * Regex: [a-zA-Z0-9]+
     * @param username Username to validate
     * @return boolean True if username is valid, false if username is invalid
     */
    public boolean ValidateUserName(String username) {
        return !checkUsername(username) && username.matches("[a-zA-Z0-9]+") && username.length() <= 10 && username.length() >= 1;
    }

    /**
     * This method is used to validate the pin must be 6 digits long, and must be numbers only <br>
     * Regex: [0-9]+
     * @param pin Pin to validate
     * @return boolean True if pin is valid, false if pin is invalid
     */
    public boolean ValidatePin(String pin) {
        return pin.length() == 6 && pin.matches("[0-9]+");
    }

    /**
     * This method is used to validate a valid email address <br>
     * Regex: ^[A-Za-z0-9+_.-]+@(.+)$
     * @param email Email to validate
     * @return boolean True if email is valid, false if email is invalid
     */
    public boolean ValidateEmail(String email) {
        return !(email.length() < 1) && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * This method is used to validate the phone number <br>
     * The phone number must be 8 digits long (Singapore phone number) <br>
     * The phone number must be numbers only or 0 <br>
     * Regex: ^([8-9][0-9]{7}|0)$
     *
     * @param phone Phone number to validate
     * @return boolean True if phone number is valid, false if phone number is invalid
     */
    public boolean ValidatePhone(String phone) {
        return !(phone.length() < 1) &&phone.matches("^([8-9][0-9]{7}|0)$");
    }

    /**
     * User Creation Function <br>
     * All fields will be validated before creating user <br>
     * An email will be sent to the user's email address <br>
     * @param username - Username of user
     * @param password - Password of user
     * @param email - Email of user
     * @param phone - Phone number of user
     * @param AccType - Account type of user
     * @return - True if user is created, false if user is not created
     */
    public boolean CreateUser(String username, String password, String email,String phone, int AccType) {
        User user = new User(username, password);
        Account account = new Account();
        //validate user input
        if (ValidatePin(password) && ValidateUserName(username) && ValidateEmail(email) && ValidatePhone(phone)) {
            user.UID = user.genUID();
            user.Salt = user.generateSalt();
            user.Password = user.generatePasswordHash(password, user.Salt);
            user.email = email;
            user.phone = phone;
            account.createAccount(AccType, user.UID);
            insertUser(user);
            System.out.println("User created!\n Proceed to login!");

            // Send email to new User
            String subject = "Welcome to Pure Bank LTD!";
            String body = "Dear " + username + ",\n\n" +
                    "Thank you for registering with Pure Bank LTD!\n\n" +
                    "If you did not register for an account with us, please contact us immediately\n\n" +
                    "Thank you,\n" +
                    "Pure Bank LTD\n" +
                    "pureinc933@gmail.com\n\n" +
                    "Please do not reply to this email as it is automatically generated.";

            //Helper.SendMail(email, subject, body);

            return true;
        } else {
            System.out.println("Invalid input!");
            return false;
        }
    }

    /**
     *  Insert new user into database
     * @param user - User object to be inserted into database
     */
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


    /**
     * Update user in database with the given user object
     * @param user User object to be updated in the database
     * @return boolean True if user is updated, false if user is not updated
     */
    public boolean updateUser(User user) {
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

    /**
     * Update user in database with the current user object, used for updating existing user
     */
    public void updateUser() {
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

    /**
     * Get user from database <br>
     * This function is used to get user information from database, and populate the current User object with the information <br>
     */
    public void getUserFromDatabase() {
        String UID = this.UID;

        String sql = "SELECT * FROM users WHERE UID = ?";
        User user = null;
        try (Connection conn = sqliteDatabase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        for (Account account : this.Accounts) {
            System.out.println("Account ID: " + account.getAccountID() + " Transaction Limit: " + account.getTransactionLimit() + " Account Type: " + account.getAccountType());
        }
    }

    /**
     * Login Function for User <br>
     * This function is used to login a user <br>
     * ALl fields will be checked, if any field is incorrect, login will fail <br>
     * An email will be sent to the user if login is successful <br>
     * @param username  Username of user
     * @param password  Password of user
     * @return Returns true if login is successful, false if login is unsuccessful
     */
    public boolean Login(String username, String password) {
        //check if user exists
        String UID = CheckUserExist(username);
        if (UID == null) {
            LogHelper.log(Level.SEVERE, "Failed Login attempt, Attempted Username: " + username);
            return false;
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
            try {
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
                // Send email
                String subject = "Login Successful";
                String body = "Dear " + this.Username + ",\n\n" +
                        "Your login was successful.\n\n" +
                        "If you did not attempt to login, please contact us immediately.\n\n" +
                        "Thank you,\n" +
                        "Pure Bank LTD\n" +
                        "pureinc933@gmail.com\n\n" +
                        "Please do not reply to this email as it is automatically generated.";
                EmailHelper.SendMail(email, subject, body);

            } catch (SQLException e) {
                LogHelper.log(Level.SEVERE, e.getMessage(), e);
            }

            return true;

        } else {
            System.out.println("Incorrect password!");
            return false;
        }
    }

    /**
     * Logout Function for User
     * This function will check if user is logged in, if user is logged in, it will set login status to false
     * @return - Returns true if logout is successful, false if logout is unsuccessful
     */
    public boolean logout() {
        if (!this.loginStatus) {
            return true;
        }
        if (this.UID == null) {
            return false;
        }

        if (this.loginStatus) {
            this.setLoginStatus(false);
            this.updateUser();
            return true;
        }
        return false;
    }

    /**
     * Logout Function for User with User object
     * @param user User Object to be logged out
     * @return Returns true if logout is successful, false if logout is unsuccessful
     */
    public boolean logout(User user) {
        try {
            user.setLoginStatus(false);
            updateUser(user);
        } catch (Exception e) {
            LogHelper.log(Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }

    //Forget Pin

    /**
     * Forget Pin Function for User <br><br>
     * This function will check if user exists, if user exists, it will generate a random password with SecureRandom <br>
     * It will then email the user with the new password <br>
     * The new password will be hashed and salted before being stored in the database <br>
     * The email will prompt user to change password on the next login <br>
     * @param name Username of user
     * @return Returns true if forget pin is successful, false if forget pin is unsuccessful
     */
    public boolean forgetPin(String name) {
        //check if user exists
        String UID = CheckUserExist(name);

        if (UID == null) {
            LogHelper.log(Level.SEVERE, "Failed Pin Change attempt, Name: " + name);
            return false;
        }

        String email = getEmailFromDatabase(UID);

        if (email == null) {
            return false;
        }

        String salt = getPasswordFromDatabase(UID)[1];
        // Generate a random password
        // Generate a 6 digit random number all digits should be different with SecureRandom
        String clearPin = String.format("%06d", new SecureRandom().nextInt(999999));
        System.out.println("Clear Pin: " + clearPin);

        String newPassword = generatePasswordHash(clearPin, salt);
        // Send email to user with new password
        String subject = "A Password change is requested";
        String body = "Dear " + name + ",\n\n" +
                "Your new password is: " + clearPin + "\n\n" +
                "Please change your password after you login.\n\n" +
                "If you did not request a password change, please contact us immediately.\n\n" +
                "Thank you,\n" +
                "Pure Bank LTD\n" +
                "pureinc933@gmail.com\n\n" +
                "Please do not reply to this email as it is automatically generated.";
        // Send email
        //Helper.SendMail(email, subject, body);

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

}
