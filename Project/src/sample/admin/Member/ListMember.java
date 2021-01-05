package sample.admin.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.ConnectionUser;
import sample.SceneManager;
import sample.admin.FetchData.FetchMember;

import java.sql.*;
public class ListMember {
    SceneManager sceneManger = new SceneManager();
    public ObservableList<FetchMember> getData(){
        ObservableList<FetchMember> data = FXCollections.observableArrayList();
        ConnectionUser addUser = new ConnectionUser();
        Connection con = addUser.getConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String dbop = "select * from usersdata";
            stmt.execute(dbop);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                int age = rs.getInt("Age");
                String report = rs.getString("Report");
                Date subDate = rs.getDate("SubDate");
                int coachId = rs.getInt("CoachId");
                data.add(new FetchMember(id,name,password,age,report,subDate,coachId));
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","List Member","Error in data listing!");
        }
        return data;
    }
}
