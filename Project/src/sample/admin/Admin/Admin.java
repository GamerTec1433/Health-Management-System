package sample.admin.Admin;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import sample.SceneManager;
import sample.User;

public class Admin {
    @FXML
    private JFXRadioButton radioMem;
    @FXML
    private JFXRadioButton radioAdmin;
    SceneManager sceneManger = new SceneManager();
    public void changing()
    {
        if(radioMem.isSelected())
        {
           sceneManger.changeScene("admin/Member/Member.fxml","Adminstrator");
        }else if(radioAdmin.isSelected())
        {
            sceneManger.changeScene("admin/Admin/ProfileAdmin.fxml","Adminstrator");
        }else
        {
            sceneManger.changeScene("admin/Coach/Coach.fxml","Adminstrator");
        }

    }

    @FXML
    public void logout(Event event) {
        SceneManager sceneManager = new SceneManager();
        sceneManager.changeScene("Login.fxml", "Health Club Management System");
        User.id = 0;
        User.username = "";
        User.password = "";
    }
}
