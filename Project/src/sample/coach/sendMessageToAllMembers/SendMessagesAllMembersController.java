package sample.coach.sendMessageToAllMembers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.Event;
import javafx.fxml.FXML;
import sample.ConnectionUser;
import sample.User;

import java.sql.*;

public class SendMessagesAllMembersController {
    @FXML
    private JFXTextArea textArea;

    @FXML
    private JFXButton sendBtn;

    @FXML
    public void sendMessage(Event event)
    {
        String str = textArea.getText();

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
                String sqlInsert = "Insert Into messages Values (?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
                preparedStatement.setInt(1, resultSet.getInt("id"));
                preparedStatement.setString(2, str);
                preparedStatement.execute();
                preparedStatement.close();
                isMessageSent = true;
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
