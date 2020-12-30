package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
                if (checkUserSubs(User.id))
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

                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean checkUserSubs(int id)
    {
        Date date = Date.valueOf(LocalDate.now());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ConnectionUser conu = new ConnectionUser();
        Connection con = conu.getConnection();
        String sql  = "select SubDate from usersdata where Id = " + id;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            rs.next();

            Date date2 = rs.getDate("SubDate");

            if (date.compareTo(date2) > 0) {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }
}
