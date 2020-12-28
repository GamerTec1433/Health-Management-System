package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUser {
    public final static String ADMIN = "admin";
    public final static String COACHES = "coachdata";
    public final static String MEMBERS = "usersdata";
    public final static String EXERCISE = "exercisedata";
    public final static String BILLING = "billingdata";
    public final static String MESSAGES = "messagedata";

    public static Connection connection;

    public ConnectionUser()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/users?useSSL=false", "root", "blood1433");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
