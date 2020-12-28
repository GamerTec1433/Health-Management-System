package sample;

import java.sql.*;
import java.time.LocalDate;

public class SQLQueries {
    public static String getCount(String columnCount, String table) {
        String value = "";

        String sql = "Select count(" + columnCount + ") from " + table;
        ConnectionUser connectionUser = new ConnectionUser();
        Connection conn = connectionUser.getConnection();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            value += rs.getInt(1);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }
    public static String getCount(String columnCount, String table, int id) {
        String value = "";

        String sql = "Select count(" + columnCount + ") from " + table + " where UserId = " + id;
        ConnectionUser connectionUser = new ConnectionUser();
        Connection conn = connectionUser.getConnection();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            value += rs.getInt(1);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }
    public static String getDateDiff(String columnName, String table, int id) {
        String value = "";

        String sql = "Select " + columnName + " from " + table + " where Id = " + id;
        ConnectionUser connectionUser = new ConnectionUser();
        Connection conn = connectionUser.getConnection();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            if (rs.next())
            {
                Date subs = rs.getDate(1);
                Date nowDate = Date.valueOf(LocalDate.now().toString());
                System.out.println(subs);
                long time = subs.getTime() - nowDate.getTime();

                value += (time / (1000 * 60 * 60 * 24)) % 365;
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return value;
    }
    public static void updateSql(String table, String column, String value, int userId) {
        try {
            ConnectionUser connectionUser = new ConnectionUser();
            Connection conn = connectionUser.getConnection();

            String sql = "update " + table + " set " + column + " = ? where Id = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static String getString(String columnName, String table, int id) {
        String value = "";

        String sql = "Select " + columnName + " from " + table + " where Id = "+ id;
        ConnectionUser connectionUser = new ConnectionUser();
        Connection conn = connectionUser.getConnection();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            value += rs.getString(1);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }
    public static int getInt(String columnName, String table, int id) {
        int value = 0;

        String sql = "Select " + columnName + " from " + table + " where Id = "+ id;
        ConnectionUser connectionUser = new ConnectionUser();
        Connection conn = connectionUser.getConnection();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            value = rs.getInt(1);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }
    public static Date getDate(String columnName, String table, int id, boolean isUser) {
        Date value = Date.valueOf("2000-01-01");

        String sql = "";
        if (isUser)
        {
            sql = "Select " + columnName + " from " + table + " where Id = "+ id;
        }
        else
        {
            sql = "Select " + columnName + " from " + table + " where UserId = "+ id;
        }
        ConnectionUser connectionUser = new ConnectionUser();
        Connection conn = connectionUser.getConnection();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            value = rs.getDate(1);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }
}
