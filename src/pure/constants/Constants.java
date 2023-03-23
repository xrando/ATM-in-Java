package pure.constants;

import pure.util.ConfigurationManager;

public final class Constants {
    private Constants() {
    }

    public static final String CONFIG_FILE_NAME = "config.properties";

    public final class Stream {
        private Stream() {
        }

        public final static String EOF = "over";
        public final static String EOS = "exit";
        public final static String RES = "Response";
    }

    public final class Socket {
        private Socket() {
        }

        public static final int PORT = Integer.parseInt(ConfigurationManager.GetConfig("Port"));
        public static final String HOST = ConfigurationManager.GetConfig("Server");
        public static final int TIMEOUT = ConfigurationManager.GetConfigAsInt("Timeout");
    }

    public final class SSL {
        private SSL() {
        }

        public static final String PROTOCOL = ConfigurationManager.GetConfig("Protocol");
        public static final String SERVER_KEYSTORE = ConfigurationManager.GetConfig("Server_Key");
        public static final String SERVER_KEYSTORE_PASS = ConfigurationManager.GetConfig("Server_Pass");
        public static final String CLIENT_KEYSTORE = ConfigurationManager.GetConfig("Client_Key");
        public static final String CLIENT_KEYSTORE_PASS = ConfigurationManager.GetConfig("Client_Pass");
    }

    public final class JSON {
        private JSON() {
        }

        public static final String TYPE = "Type";
    }

    public final class User {
        private User() {
        }

        public static final String LOGIN = "Login";
        public static final String LOGOUT = "Logout";
        public static final String USERNAME = "Username";
        public static final String EMAIL = "Email";
        public static final String PHONE = "Phone";
        public static final String PASSWORD = "Password";
        public static final String LOGIN_STATUS = "LoginStatus";
        public static final String FORGET_PIN = "ForgetPin";
        public static final String CREATE_USER = "CreateUser";
        public static final String UPDATE_USER = "UpdateUser";
        public static final String CHANGE_PIN = "ChangePin";
        public static final String ACCOUNTS = "Accounts";
        public static final String OLD_PIN = "oldPin";
        public static final String NEW_PIN = "newPin";
        public static final String GET_USER_INFORMATION = "GetUserInformation";
    }

    public final class Account {
        private Account() {
        }

        public static final String SELECT_ACCOUNT = "SelectAccount";
        public static final String SELECTED_ACCOUNT = "SelectedAccount";
        public static final String TRANSACTION_HISTORY = "TransactionHistory";
        public static final String CREATE_ACCOUNT = "CreateAccount";
        public static final String ALL_ACCOUNTS = "AllAccounts";
        public static final String ACCOUNT_ID = "accountId";
        public static final String ACCOUNT_TYPE = "accountType";
        public static final String USER_ID = "userID";
        public static final String GET_ACCOUNT_BALANCE = "GetAccountBalance";
        public static final String BALANCE = "Balance";
        public static final String CHANGE_TRANSACTION_LIMIT = "changeTransactionLimit";
        public static final String GET_TRANSACTION_LIMIT = "GetTransactionLimit";
        public static final String GET_ACCOUNT_SUMMARY = "GetAccountSummary";
    }

    public final class Transaction {
        private Transaction() {
        }

        public static final String WITHDRAW = "Withdraw";
        public static final String DEPOSIT = "Deposit";
        public static final String TRANSFER = "Transfer";
        public static final String AMOUNT = "amount";
        public static final String TRANSACTION_NOTE = "transactionNote";
        public static final String TIME_STAMP = "timeStamp";
        public static final String DATE = "date";
        public static final String PAYEE = "payee";
        public static final String ACCOUNT_ID = "accountID";
    }
}
