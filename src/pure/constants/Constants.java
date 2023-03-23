package pure.constants;

import pure.util.ConfigurationManager;

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
        public static final String Balance = "Balance";
        public static final String ChangeTransactionLimit = "changeTransactionLimit";
        public static final String GetTransactionLimit = "GetTransactionLimit";
        public static final String GetAccountSummary = "GetAccountSummary";
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
}
