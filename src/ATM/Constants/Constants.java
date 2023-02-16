package ATM.Constants;

import ATM.Utilities.ConfigurationManager;

import java.util.ArrayList;

public abstract class Constants {
    public abstract static class Stream {
        public final static String EOF = "over";
        public final static String EOS = "exit";
    }

    public abstract static class Socket {
        public static final int PORT = Integer.parseInt(ConfigurationManager.GetConfig("Port"));
        public static final String HOST = ConfigurationManager.GetConfig("Server");
    }

    public abstract static class SSL {
        public static final String PROTOCOL = "TLS";
        public static final String KEYSTORE = "Resources/SSL/ATM.jks";
        public static final String KEYSTOREPASS = "123456";
        public static final String TRUSTSTORE = "Resources/SSL/cacerts.jks";
        public static final String TRUSTSTOREPASS = "123456";
    }

    public abstract static class userFunctions {
        // Takes in username and password
        // Returns true if login is successful, Logs and returns false if login is unsuccessful
        public abstract boolean Login(String username, String password);

        //Same Login Function without parameters, this will use the current user object
        public abstract boolean Login();

        // Logout
        public abstract boolean logout();

        // same logout but with user object
        //public abstract boolean logout(User user);

        // Actual Creation of user
        public abstract boolean CreateUser(String username, String password, String email, String phone);

        // User Creation with 2 fields only, email and phone will be set to " "
        public abstract boolean CreateUser(String username, String password);

        //Create User Sequence for Client, with Input Sanitization
        public abstract boolean CreateUserSeq();

        // Change Pin Sequence
        // Returns true if pin was changed
        // Returns false if pin was not changed or if user cancelled
        public abstract boolean ChangePin();

        ////////////////////////////////////////////////////////////////////////////////
        // Methods below may not be needed
        ////////////////////////////////////////////////////////////////////////////////

        // Takes in password and UID and returns true if password is correct
        // For Login
        // Requires getPassword
        public abstract boolean validatePassword(String password, String UID);

        // Get password from DB
        // Returns String[]{password, Salt} in this order
        public abstract String[] getPassword(String UID);

        // Takes in username and returns UID if exists, returns null if not
        public abstract String CheckUserExist(String username);

        // takes in username and returns true if username is valid
        // For user creation
        public abstract boolean checkUsername(String username);

        //insert User, For user creation
        //public abstract void insertUser(User user);

        // Update User
        //public abstract void updateUser(User user);

        // Generate salt with username and UID, requires hash function
        public abstract String generateSalt(String username, String UID);

        // Hash function
        public abstract String hash(String stringToHash);

        // Generate Password Hash
        // Returns the password hash
        public abstract String generatePasswordHash(String password, String salt);

    }

    public abstract static class accountsFunctions {
        //Retrieve account type from account constructor
        public String getAccountType(){ return accountType;}

        //Retrieve userID from account constructor
        public abstract String getUID();

        //Retrieve accountID from account constructor
        public abstract String getAccountID();

        //Retrieve list of transactions based of accountID
        public abstract ArrayList<Transaction> getAccountTransactions();

        //Return string with formatted type of balance for printing (+/-)
        public abstract String getAccountSummary();

        //Return double of total balance of account by adding amounts
        public abstract double GetAccountBalance();

        //Add retrieved transactions to arraylist
        public abstract boolean AddTransaction(double Amount, String TransactionNote);

        //Return selected account from arraylist
        public abstract Account setTransactionAccount(String userID);

        //Create new entry of accounts for user
        public abstract void createAccount(String userID);

        ////////////////////////////////////////////////////////////////////////////////
        // Methods below may not be needed
        ////////////////////////////////////////////////////////////////////////////////

        //return string of transaction history of account(empty function)
        public abstract String PrintTransactionHistory();
    }

    public abstract static class transactionFunctions {
        //Retrieve amount in transaction constructor
        public abstract double getAmount();

        //Retrieve transaction note in transaction constructor
        public abstract String getTransactionNote();

        //Return current time in hh:mm:ss in string to be used in creating transaction entries
        public abstract String getTimeStamp();

        //Return current date in dd:MM:yyyy in string to be used in creating transaction entries
        public abstract String getCurrentDate();

        //Return the Transaction Date and TimeStamp of transactions based on transactionID
        public abstract String getTransactionDate();

        //Get transaction summary
        public abstract String GetTransactionHistory(Account TransactionAccount);

        //Execute SQL statement to push transaction into database, true if success,false if fail
        public abstract boolean AddTransactionToSQL(Account account, Transaction transactionDetails);

        ////////////////////////////////////////////////////////////////////////////////
        // Methods below may not be needed
        ////////////////////////////////////////////////////////////////////////////////

        //Menu function get user to select switch case menu
        public abstract boolean GetChoice(Account TransactionAccount);

    }

}
