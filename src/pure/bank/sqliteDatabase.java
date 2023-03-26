package pure.bank;

import java.io.File;
import java.sql.*;

/**
 * SqliteDatabase Class <br>
 * All SqliteDatabase Defined Methods are defined here
 */
public class sqliteDatabase {

    /**
     * This method is used to connect with the sqlite database
     * @return Returns the connection to the database
     */
    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:Resources/db/atm.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            //System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * This method is used to generate a new database
     * @param fileName The name of the database file
     */
    public static void createNewDatabase(String fileName) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        String url = "jdbc:sqlite:Resources/db/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is used to populate the database with the tables (Users, Accounts, Transactions)
     */
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Resources/db/atm.db";

        String[] sqlArray = {
                "CREATE TABLE IF NOT EXISTS users(userID integer PRIMARY KEY, username text NOT NULL, password text NOT NULL, salt text NOT NULL, email text NOT NULL, phone text NOT NULL, loginStatus boolean NOT NULL)",
                "CREATE TABLE IF NOT EXISTS accounts(accountID integer PRIMARY KEY, accountType text NOT NULL, transactionLimit integer NOT NULL, userID integer NOT NULL, FOREIGN KEY(userID) REFERENCES users(userID))",
                "CREATE TABLE IF NOT EXISTS transactions(transactionID integer PRIMARY KEY, amount real NOT NULL, timeStamp text NOT NULL, transactionNote text NULL, date text NOT NULL, payee text NULL,accountID text NOT NULL, FOREIGN KEY(accountID) REFERENCES accounts(accountID))"
        };

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            for (String sqlStatement : sqlArray) {
                stmt.execute(sqlStatement);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        sqliteDatabase app = new sqliteDatabase();
        File f = new File("./Resources/db/atm.db");
        if (f.exists() && !f.isDirectory()) {
            connect();
            createNewTable();
        } else {
            createNewDatabase("atm.db");
            System.out.print("Creating Table");
            createNewTable();
        }
    }
}