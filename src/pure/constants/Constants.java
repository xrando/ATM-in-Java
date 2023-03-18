package pure.constants;

import pure.util.ConfigurationManager;

import java.util.ArrayList;

public abstract class Constants {
    public static final String CONFIG_FILE_NAME = "config.properties";

    public abstract static class Stream {
        public final static String EOF = "over";
        public final static String EOS = "exit";
        public final static String RES = "Response";
    }

    public abstract static class Socket {
        public static final int PORT = Integer.parseInt(ConfigurationManager.GetConfig("Port"));
        public static final String HOST = ConfigurationManager.GetConfig("Server");
        public static final int TIMEOUT = ConfigurationManager.GetConfigAsInt("Timeout");
    }

    public abstract static class SSL {
        public static final String PROTOCOL = ConfigurationManager.GetConfig("Protocol");
        public static final String SERVER_KEYSTORE = ConfigurationManager.GetConfig("Server_Key");
        public static final String SERVER_KEYSTORE_PASS = ConfigurationManager.GetConfig("Server_Pass");
        public static final String CLIENT_KEYSTORE = ConfigurationManager.GetConfig("Client_Key");
        public static final String CLIENT_KEYSTORE_PASS = ConfigurationManager.GetConfig("Client_Pass");
    }

    public abstract static class JSON {
        public static final String Type = "Type";
    }

    public abstract static class User { //return bool
        public static final String Login = "Login";
        public static final String Logout = "Logout";
        public static final String Username = "Username";
        public static final String Email = "Email";
        public static final String Phone = "Phone";
        public static final String Password = "Password";
        public static final String LoginStatus = "LoginStatus";

        public static final String ForgetPin = "ForgetPin";


        public static final String CreateUser = "CreateUser";
        public static final String UpdateUser = "UpdateUser";
        public static final String ChangePin = "ChangePin";
        public static final String Accounts = "Accounts";
        public static final String oldPin = "oldPin";
        public static final String newPin = "newPin";
        public static final String GetUserInformation = "GetUserInformation";
    }

    public abstract static class Account {
        public static final String SelectAccount = "SelectAccount";
        public static final String SelectedAccount = "SelectedAccount";
        public static final String TransactionHistory = "TransactionHistory";
        public static final String CreateAccount = "CreateAccount";
        public static final String AllAccounts = "AllAccounts";
        public static final String AccountId = "accountId";
        public static final String AccountType = "accountType";
        public static final String UserID = "userID";
        public static final String GetAccountBalance = "GetAccountBalance";
        public static final String ChangeTransactionLimit = "changeTransactionLimit";
        public static final String GetTransactionLimit = "GetTransactionLimit";
    }

    public abstract static class Transaction {
        public static final String Withdraw = "Withdraw";
        public static final String Deposit = "Deposit";
        public static final String Transfer = "Transfer";
        public static final String Amount = "amount";
        public static final String TransactionNote = "transactionNote";
        public static final String TimeStamp = "timeStamp";
        public static final String date = "date";
        public static final String Payee = "payee";
        public static final String AccountId = "accountID";
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
        //public abstract boolean logout(ATM.ATM.Bank.Bank.User user);

        // Actual Creation of user
        public abstract boolean CreateUser(String username, String password, String email, String phone);

        // ATM.ATM.Bank.Bank.User Creation with 2 fields only, email and phone will be set to " "
        public abstract boolean CreateUser(String username, String password);

        //Create ATM.ATM.Bank.Bank.User Sequence for Client, with Input Sanitization
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

        //insert ATM.ATM.Bank.Bank.User, For user creation
        //public abstract void insertUser(ATM.ATM.Bank.Bank.User user);

        // Update ATM.ATM.Bank.Bank.User
        //public abstract void updateUser(ATM.ATM.Bank.Bank.User user);

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
        public abstract String getAccountType();

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

        //Return the ATM.ATM.Bank.Bank.Transaction Date and TimeStamp of transactions based on transactionID
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