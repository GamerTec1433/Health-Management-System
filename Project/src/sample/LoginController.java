package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

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
            if (checkUser(ConnectionUser.MEMBERS, usernameTextField.getText(), passwordTextField.getText()))
            {
                System.out.println("Iam A Member");
                SceneManager sceneManager = new SceneManager();
                sceneManager.changeScene("member/MemberScreen.fxml", "Health Club Management System");
            }
            else
            {
                System.out.println("Wrong User");
            }
        }
        else if (rbSelectGroup.getSelectedToggle() == rbCoach)
        {
            if (checkUser(ConnectionUser.COACHES, usernameTextField.getText(), passwordTextField.getText()))
            {
                System.out.println("Iam A Coach");
                SceneManager sceneManager = new SceneManager();
                sceneManager.changeScene("coach/CoachScreen.fxml", "Health Club Management System");
            }
            else
            {
                System.out.println("Wrong User");
            }
        }
        else
        {
            System.out.println("Iam An Admin");
            SceneManager sceneManager = new SceneManager();
            sceneManager.changeScene("admin/AdminScreen.fxml", "Health Club Management System");
        }

    }

    public boolean checkUser(String table, String username, String password) {
        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();
        try {
            String sql = "SELECT * FROM " + table + " WHERE Name = ? AND Password = ?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            String usernameTemp = resultSet.getString("Name");
            String passwordTemp = resultSet.getString("Password");
            if (usernameTemp.equals(username) &&
                    passwordTemp.equals(password))
            {
                User.id = resultSet.getInt("id");
                User.username = usernameTemp;;
                User.password = passwordTemp;

                System.out.println(User.id + " " + User.username + " " + User.password);

                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return false;
    }
}
