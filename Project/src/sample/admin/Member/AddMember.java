package sample.admin.Member;
import java.sql.*;
import java.time.LocalDate;
public class AddMember {
    private void updateSubdate(Connection con, Date EDate, String password) {
        String dbop = "update usersdata set SubDate = ? Where Id = ?;";
        try {
            PreparedStatement stmt = con.prepareStatement(dbop);
            stmt.setDate(1, EDate);

            int id = getNewMemberId(password, con);
            stmt.setInt(2, id);

            stmt.execute();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addBilling(Connection con, String password) throws SQLException {
        PreparedStatement stmt;
        int id = getNewMemberId(password, con);
        String dbop = "insert into billingdata (UserId, Money, Sdate, Edate)values(?, ?, ?, ?);";
        stmt = con.prepareStatement(dbop);
        stmt.setInt(1, id);
        stmt.setInt(2, 1000);

        LocalDate ld = LocalDate.now();
        Date d = Date.valueOf(ld.toString());
        stmt.setDate(3, d);


        d = Date.valueOf(incMonthDate(ld));
        stmt.setDate(4, d);
        stmt.execute();
        stmt.close();
        updateSubdate(con, d, password);
    }
    public  void addUser(Connection con, String name, String password, String age, String coachid) throws SQLException {
        PreparedStatement stmt;
        String dbop = "insert into usersdata (Name, Password, Age, CoachId)values(?, ?, ?, ?);";
        stmt = con.prepareStatement(dbop);
        stmt.setString(1, name);
        stmt.setString(2, password);
        stmt.setInt(3, Integer.parseInt(age));
        stmt.setInt(4, Integer.parseInt(coachid));
        stmt.execute();
    }
    private String incMonthDate(LocalDate localDate) {
        int month = (localDate.getMonthValue() + 1 ) % 13;
        if (month == 0)
        {
            return (localDate.getYear() + 1) + "-" + (month + 1) + "-" + localDate.getDayOfMonth();
        }
        else
        {
            return localDate.getYear() + "-" + month + "-" + localDate.getDayOfMonth();
        }
    }
    private int getNewMemberId(String password, Connection conn) {
        String query = "select Id from usersdata where Password = \"" + password+"\"";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);

            ResultSet set = stmt.getResultSet();
            set.next();
            int id = set.getInt("Id");
            stmt.close();
            return id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}