import java.io.File;
import java.sql.*;

/**
 * @author sqlitetutorial.net
 */
public class sqliteDatabase {

    //Made this static so that I can call it from other classes
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

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void disconnect() {
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

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

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

    //Modified this to create the User table
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Resources/db/atm.db";

        String[] sqlArray = {
                "CREATE TABLE IF NOT EXISTS users(userID integer PRIMARY KEY, username text NOT NULL, password text NOT NULL, salt text NOT NULL, email text NOT NULL, phone text NOT NULL, loginStatus boolean NOT NULL)",
                "CREATE TABLE IF NOT EXISTS accounts(accountID integer PRIMARY KEY, accountHolder integer NOT NULL, userID integer NOT NULL, FOREIGN KEY(userID) REFERENCES users(userID))",
                "CREATE TABLE IF NOT EXISTS transactions(transactionID integer PRIMARY KEY, amount real NOT NULL, timeStamp text NOT NULL, transactionNote text NULL, date text NOT NULL, accountID text NOT NULL, FOREIGN KEY(accountID) REFERENCES accounts(accountID))"
                //"CREATE TABLE IF NOT EXIST bank(bankUser integer PRIMARY KEY,bankName text NOT NULL,bankAccounts text NOT NULL)",

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

    public void insert(String username, String password, String salt, String email, String phone, boolean loginStatus) {
        String sql = "INSERT INTO users(username,password,salt,email,phone,loginStatus) VALUES(?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, salt);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setBoolean(6, loginStatus);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAll() {
        String sql = "SELECT username, email, phone FROM users";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("username") + "\t" +
                        rs.getString("email") + "\t" +
                        rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        sqliteDatabase app = new sqliteDatabase();
        File f = new File("./Resources/db/atm.db");
        if (f.exists() && !f.isDirectory()) {
            app.connect();
            createNewTable();
        } else {
            createNewDatabase("atm.db");
            try {
                System.out.print("Creating Table");
                createNewTable();
            } finally {
                //app.selectAll();
            }
        }
    }
}