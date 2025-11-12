package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/biotech_park?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "Prince yadav";
    private static final String PASSWORD = "Prince@123";
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found. Add the connector JAR to classpath.");
                e.printStackTrace();
                throw new SQLException(e);
            }
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        }
        return conn;
    }
}
