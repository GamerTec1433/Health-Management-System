package sample.admin.Member;
import sample.SceneManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class UpdateMember {
    SceneManager sceneManger = new SceneManager();
    public void update(String col, String data , int id , Connection con)
    {
        String dbop = "update usersdata set "+col+" = ?  where Id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(dbop);
            ps.setString(1,data);
            ps.setInt(2,id);
            ps.execute();
            ps.close();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Update Member","Info Of User Updated Successfully!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Update Member"," Info Of User Updated Failed!");
        }
    }
    public void update(String col, int data , int id , Connection con)
    {
        String dbop = "update usersdata set "+col+" = ?  where Id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(dbop);
            ps.setInt(1,data);
            ps.setInt(2,id);
            ps.execute();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updateBilling( int data , int id , Connection con)
    {
        String dbop = "update billingdata set Money = ?  where UserId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(dbop);
            ps.setInt(1,data);
            ps.setInt(2,id);
            ps.execute();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
