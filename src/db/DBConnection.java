package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/employee_db";
    private static final String USER = "root"; // change if needed
    private static final String PASS = "";     // change if needed

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // load driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MariaDB JDBC Driver not found!");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
