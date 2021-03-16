package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import sample.SQLQueries.*;

public class LoginController implements Initializable {
    public Button loginBtn;
    public TextField usernameTextField;
    public TextField passwordTextField;

    public ToggleGroup rbSelectGroup;
    public RadioButton rbMember;
    public RadioButton rbCoach;
    public RadioButton rbAdmin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Start Program!!");
    }

    public void login() {
        System.out.println(usernameTextField.getText() + " " + passwordTextField.getText());

        if (rbSelectGroup.getSelectedToggle() == rbMember)
        {
            if (SQLQueries.checkUser(ConnectionUser.MEMBERS, usernameTextField.getText(), passwordTextField.getText()))
            {
                System.out.println("Iam A Member");
                if (SQLQueries.checkUserSubs(User.id))
                {
                    SceneManager sceneManager = new SceneManager();
                    sceneManager.changeScene("member/MemberScreen.fxml", "Health Club Management System");
                }
                else
                {
                    SceneManager sceneManager = new SceneManager();
                    sceneManager.openAlertBox("Extra/AlertBox.fxml", "Subscription Ended", "Please Renew the Subscription to continue in our service");
                }
            }
            else
            {
                SceneManager sceneManager = new SceneManager();
                sceneManager.openAlertBox("Extra/AlertBox.fxml", "Invalid Username/Password", "Please check your name or password");

                System.out.println("Wrong User");
            }
        }
        else if (rbSelectGroup.getSelectedToggle() == rbCoach)
        {
            if (SQLQueries.checkUser(ConnectionUser.COACHES, usernameTextField.getText(), passwordTextField.getText()))
            {
                System.out.println("Iam A Coach");
                SceneManager sceneManager = new SceneManager();
                sceneManager.changeScene("coach/CoachScreen.fxml", "Health Club Management System");
            }
            else
            {
                SceneManager sceneManager = new SceneManager();
                sceneManager.openAlertBox("Extra/AlertBox.fxml", "Invalid Username/Password", "Please check your name or password");

                System.out.println("Wrong User");
            }
        }
        else
        {
            if (SQLQueries.checkUser(ConnectionUser.ADMIN, usernameTextField.getText(), passwordTextField.getText()))
            {
                System.out.println("Iam An Admin");
                SceneManager sceneManager = new SceneManager();
                sceneManager.changeScene("admin/Admin/Admin.fxml", "Health Club Management System");
            }
            else
            {
                SceneManager sceneManager = new SceneManager();
                sceneManager.openAlertBox("Extra/AlertBox.fxml", "Invalid Username/Password", "Please check your name or password");

                System.out.println("Wrong User");
            }
        }

    }


}
