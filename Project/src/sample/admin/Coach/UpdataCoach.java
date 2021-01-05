package sample.admin.Coach;
import sample.SceneManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class UpdataCoach {
    SceneManager sceneManger = new SceneManager();
    public void update(String col, String data , int id , Connection con)
    {
        String dbop = "update coachdata set "+col+" = ?  where Id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(dbop);
            ps.setString(1,data);
            ps.setInt(2,id);
            ps.execute();
            ps.close();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Update Coach","Update successful!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Update Coach","Error in data listing!");
        }
    }
    public void update(String col, int data , int id , Connection con)
    {
        String dbop = "update coachdata set "+col+" = ?  where Id = ?";
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
