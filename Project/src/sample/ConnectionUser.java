package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUser {
    public final static String coachesTable = "coaches";
    public final static String membersTable = "members";
    public final static String exercisesTable = "exercises";

    public static Connection connection;

    public ConnectionUser()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mydatabase?useSSL=false", "root", "blood1433");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
