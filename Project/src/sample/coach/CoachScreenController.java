package sample.coach;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ConnectionUser;
import sample.SceneManager;
import sample.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class CoachScreenController implements Initializable {

    // Timeline Table
    public TableView<TimelineItems> coachTable;
    @FXML
    private TableColumn<TimelineItems, Integer> idCol;

    @FXML
    private TableColumn<TimelineItems, String> exerciseCol;

    @FXML
    private TableColumn<TimelineItems, String> dateCol;

    @FXML
    private TableColumn<TimelineItems, String> timeCol;

    // Members Table
    @FXML
    private TableView<MemberItems> memberTable;

    @FXML
    private TableColumn<MemberItems, Integer> idColMem;

    @FXML
    private TableColumn<MemberItems, String> nameColMem;

    @FXML
    private TableColumn<MemberItems, Integer> exerciseIdColMem;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        exerciseCol.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        idColMem.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColMem.setCellValueFactory(new PropertyValueFactory<>("name"));
        exerciseIdColMem.setCellValueFactory(new PropertyValueFactory<>("exerciseId"));

        refreshTimelineTable(null);
    }

    public void openSendMessageAllWindow(Event event)
    {
        SceneManager sceneManager = new SceneManager();
        sceneManager.openWindow("coach/sendMessageToAllMembers/SendMessagesAllMembers.fxml", "Send Message");
    }
    public void openSendMessageWindow(Event event)
    {
        SceneManager sceneManager = new SceneManager();
        sceneManager.openWindow("coach/sendMessageToMember/SendMessageToMember.fxml", "Send Message");
    }
    public void openNewTimelineWindow(Event event)
    {
        SceneManager sceneManager = new SceneManager();
        sceneManager.openWindow("coach/AddTimeline/AddTimeline.fxml", "Add New Timeline");
    }

    @FXML
    public void logout(Event event)
    {
        SceneManager sceneManager = new SceneManager();
        sceneManager.changeScene("Login.fxml", "Health Club Management System");
        User.id = 0;
        User.username = "";
        User.password = "";
    }

    @FXML
    public void refreshTimelineTable(Event event)
    {
        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();
        try {
            Statement statement = con.createStatement();
            String query = "Select * From exercises Where coachId = " + User.id;
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<TimelineItems> data = FXCollections.observableArrayList();

            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String exercise = resultSet.getString("exercise");
                Date date = resultSet.getDate("date");
                Time time = resultSet.getTime("time");
                System.out.println(id);
                System.out.println(exercise);
                System.out.println(date.toString());
                data.add(new TimelineItems(id, exercise, date.toString(), time.toString()));
                System.out.println(id);
            }

            coachTable.setItems(data);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        refreshMembersTable(null);
    }

    @FXML
    public void refreshMembersTable(Event event)
    {
        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();
        try {
            Statement statement = con.createStatement();
            String query = "Select * From members Where coachId = " + User.id;
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<MemberItems> data = FXCollections.observableArrayList();

            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String exercise = resultSet.getString("name");
                int exerciseId = resultSet.getInt("exercise");
                System.out.println(id);
                System.out.println(exercise);
                System.out.println(exerciseId);
                data.add(new MemberItems(id, exercise, exerciseId));
                System.out.println(id);
            }

            memberTable.setItems(data);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

