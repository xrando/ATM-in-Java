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

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	uid integer PRIMARY KEY,\n"
                + "	username text NOT NULL,\n"
                + "	password text NOT NULL,\n"
                + " salt text NOT NULL,\n"
                + " email text NOT NULL,\n"
                + " phone text NOT NULL,\n"
                + " loginStatus boolean NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String name, String password) {
        String sql = "INSERT INTO users(username,password,salt,email,phone,loginStatus) VALUES(?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, "salty");
            pstmt.setString(4, "email");
            pstmt.setString(5, "phone");
            pstmt.setBoolean(6, false);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        sqliteDatabase app = new sqliteDatabase();
        File f = new File("./Resources/db/atm.db");
        if (f.exists() && !f.isDirectory()) {
            app.connect();
            try {
                System.out.print("Creating Table");
                createNewTable();
            } finally {
                app.insert("poonxy", "Password");
                app.insert("woojw", "hacksOn");
                app.insert("richie", "helpMe");
                app.insert("benjamin", "SqlSucks");
            }
        } else {
            createNewDatabase("atm.db");
            try {
                System.out.print("Creating Table");
                createNewTable();
            } finally {
                app.insert("poonxy", "Password");
                app.insert("woojw", "hacksOn");
                app.insert("richie", "helpMe");
                app.insert("benjamin", "SqlSucks");
            }
        }
    }
}