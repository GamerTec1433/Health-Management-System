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
    public static String getCount(String columnCount, String table, String where , String cond){
        String value = "";

        String sql = "Select count(" + columnCount + ") from " + table + " where " + where + " = \"" + cond + "\"";
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
    public static String getCount(String columnCount, String table, String where1 , String cond1, String where2, String cond2){
        String value = "";

        String sql = "Select count(" + columnCount + ") from " + table + " where " + where1 + " = " + cond1 + " and " + where2 + " = " + cond2;
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
    public static void updateSql(String table, String column, String value, String where1, String cond1, String where2, String cond2) {
        try {
            ConnectionUser connectionUser = new ConnectionUser();
            Connection conn = connectionUser.getConnection();

            String sql = "update " + table + " set " + column + " = ? where " + where1 + " = ? and " + where2 + " = " + cond2;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, Integer.parseInt(cond1));
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
        System.out.println(sql);
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
    public static boolean sendMessageSQL(String str, Connection con, boolean isMessageSent, ResultSet resultSet) throws SQLException {
        String sqlInsert = "Insert Into " + ConnectionUser.MESSAGES + " Values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
        preparedStatement.setInt(1, resultSet.getInt("id"));
        preparedStatement.setString(2, str);
        preparedStatement.setDate(3, Date.valueOf(LocalDate.now().toString()));
        preparedStatement.setInt(4, User.id);
        preparedStatement.setBoolean(5, false);
        preparedStatement.execute();
        preparedStatement.close();
        isMessageSent = true;
        return true;
    }
}
