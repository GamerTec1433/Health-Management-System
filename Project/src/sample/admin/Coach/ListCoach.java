package sample.admin.Coach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.ConnectionUser;
import sample.SceneManager;
import sample.admin.FetchData.FetchCoach;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ListCoach {
    SceneManager sceneManger = new SceneManager();
    public ObservableList<FetchCoach> getData(){
        ObservableList<FetchCoach> data = FXCollections.observableArrayList();
        ConnectionUser addUser = new ConnectionUser();
        Connection con = addUser.getConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String dbop = "select * from coachdata;";
            stmt.execute(dbop);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                int age = rs.getInt("Age");

                data.add(new FetchCoach(id,name,password,age));
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","List Coach","Error in data listing!");
        }
        return data;
    }
}
