package sample.admin.Admin;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.ConnectionUser;
import sample.SceneManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class ProfileAdmin {
    SceneManager sceneManger = new SceneManager();
        @FXML
        private JFXTextField nameField;
        @FXML
        private JFXTextField passwordField;
        @FXML
        private JFXTextField idField;
        @FXML
        private JFXButton adminBack;
        public void onClickEditt(ActionEvent actionEvent){
            ConnectionUser addUser = new ConnectionUser();
            Connection con = addUser.getConnection();
            String name = nameField.getText();
            int id = Integer.parseInt(idField.getText());
            String password = passwordField.getText();
            if(!name.isEmpty() || !name.isBlank()){
                update("Name",name,id,con);
            }
            if(!password.isEmpty() ||  !password.isBlank()){
                update("Password",password,id,con);
            }
            idField.setText("");
            nameField.setText("");
            passwordField.setText("");
        }
        public void update(String col, String data , int id , Connection con)
        {
            String dbop = "update admin set "+col+" = ?  where Id = ?";
            try {
                PreparedStatement ps = con.prepareStatement(dbop);
                ps.setString(1,data);
                ps.setInt(2,id);
                ps.execute();
                ps.close();
                sceneManger.openAlertBox("Extra/AlertBox.fxml","Profile Admin","Updated Successfully!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                sceneManger.openAlertBox("Extra/AlertBox.fxml","Profile Admin","Updated Failed!");
            }
        }
    public void backPage1()
    {
        adminBack.setOnAction(e->actions());
    }
    public void actions()
    {
        SceneManager sceneManger = new SceneManager();
        sceneManger.changeScene("admin/Admin/Admin.fxml","admins");
    }
}