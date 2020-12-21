package sample.coach.sendMessageToMember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.ConnectionUser;
import sample.User;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SendMessageToMemberController implements Initializable {

    @FXML
    private JFXTextField idMember;

    @FXML
    private JFXTextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idMember.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}(\\d{0,4})?")) {
                idMember.setText(oldValue);
            }
        });
    }

    @FXML
    public void sendMessage(Event event)
    {
        String str = textArea.getText();
        int id = Integer.parseInt(idMember.getText());

        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();

        boolean isMessageSent = false;
        try {
            String sql = "Select * From members Where coachId = " + User.id;
            Statement statement = con.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next())
            {
                if (resultSet.getInt("id") == id)
                {
                    String sqlInsert = "Insert Into messages Values (?, ?)";
                    PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
                    preparedStatement.setInt(1, resultSet.getInt("id"));
                    preparedStatement.setString(2, str);
                    preparedStatement.execute();
                    preparedStatement.close();
                    isMessageSent = true;
                    break;
                }
            }
            statement.cancel();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (isMessageSent)
        {
            System.out.println("Message Sent!");
        }
        else
        {
            System.out.println("Message Not Sent!");
        }
    }
}
