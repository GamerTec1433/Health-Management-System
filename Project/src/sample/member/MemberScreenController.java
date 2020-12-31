package sample.member;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.*;
import sample.coach.TimelineItems;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MemberScreenController implements Initializable {

    UIControllers.ButtonsMemberActive buttonsActive;

    // Buttons
    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton timelineBtn;

    @FXML
    private JFXButton profileBtn;

    @FXML
    private JFXButton messageBtn;

    @FXML
    private JFXButton updateProfileBtn;

    @FXML
    private Label notificationMess;

    // TextField

    @FXML
    private JFXTextField nameTextfield;

    @FXML
    private JFXTextField passwordTextfield;

    @FXML
    private JFXTextField ageTextfield;

    // Labels Home

    @FXML
    private Label homeMessText;

    @FXML
    private Label homeExerText;

    @FXML
    private Label homeDaysText;

    // Labels Profile

    @FXML
    private Label nameProfileText;

    @FXML
    private Label ageProfileText;

    @FXML
    private Label sDateProfileText;

    @FXML
    private Label eDateProfileText;

    @FXML
    private Label coachIdNameProfileText;


    // Anchors

    @FXML
    private AnchorPane homePage;

    @FXML
    private AnchorPane profilePage;

    @FXML
    private AnchorPane timelinePage;

    @FXML
    private AnchorPane messagesPage;

    // Tables

    @FXML
    private TableView<TimelineItems> coachTable;

    @FXML
    private TableColumn<TimelineItems, Integer> idCol;

    @FXML
    private TableColumn<TimelineItems, String> exerciseCol;

    @FXML
    private TableView<MessageItems> messagesTable;

    @FXML
    private TableColumn<MessageItems, String> messageCol;

    @FXML
    private TableColumn<MessageItems, Date> DateCol;

    ArrayList<AnchorPane> anchors;
    ArrayList<JFXButton> jfxButtons;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTables();
        intializeButtons();

        UIControllers.setTextFieldNumbers(ageTextfield);

        refreshHomeTexts();

        nameProfileText.setText(SQLQueries.getString("Name", ConnectionUser.MEMBERS, User.id));
        ageProfileText.setText(Integer.toString(SQLQueries.getInt("Age", ConnectionUser.MEMBERS, User.id)));
        sDateProfileText.setText(SQLQueries.getDate("Sdate", ConnectionUser.BILLING, User.id, false).toString());
        eDateProfileText.setText(SQLQueries.getDate("SubDate", ConnectionUser.MEMBERS, User.id, true).toString());
    }

    private void intializeButtons() {
        anchors = new ArrayList<>();
        anchors.add(homePage);
        anchors.add(timelinePage);
        anchors.add(profilePage);
        anchors.add(messagesPage);

        jfxButtons = new ArrayList<>();
        jfxButtons.add(homeBtn);
        jfxButtons.add(timelineBtn);
        jfxButtons.add(profileBtn);
        jfxButtons.add(messageBtn);

        int i = 0;
        for (JFXButton b : jfxButtons)
        {
            int finalI = i;
            b.setOnAction(e->{
                UIControllers.activePage(b, jfxButtons, anchors.get(finalI), anchors, ProjectColors.WHITE_COLOR, ProjectColors.WHITE_COLOR, 0, 0);
                buttonsActive = UIControllers.ButtonsMemberActive.values()[finalI];

                if (b == messageBtn)
                {
                    checkReadedMessages();
                }
            });
            b.setOnMouseEntered(e->{
                if (buttonsActive == UIControllers.ButtonsMemberActive.values()[finalI]) return;
                UIControllers.changeStyleBackground(b, ProjectColors.SECONDARY_COLOR, ProjectColors.WHITE_COLOR, 0);
            });
            b.setOnMouseExited(e->{
                if (buttonsActive == UIControllers.ButtonsMemberActive.values()[finalI]) return;
                UIControllers.changeStyleBackground(b, ProjectColors.TRANSPARENT, ProjectColors.WHITE_COLOR, 0);
            });
            i++;
        }

        //Show Home Page
        homeBtn.fire();
        checkNewMessages();
    }

    private void initializeTables()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        exerciseCol.setCellValueFactory(new PropertyValueFactory<>("exercise"));

        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        refreshTimelineTable(null);
    }


    private void refreshHomeTexts()
    {
        homeMessText.setText(SQLQueries.getCount("Message", ConnectionUser.MESSAGES, User.id));

        homeExerText.setText(SQLQueries.getCount("exercise", ConnectionUser.EXERCISE));

        homeDaysText.setText(SQLQueries.getDateDiff("SubDate", ConnectionUser.MEMBERS, User.id));

        int coachId = SQLQueries.getInt("CoachId", ConnectionUser.MEMBERS, User.id);
        String coachName = SQLQueries.getString("Name", ConnectionUser.COACHES, coachId);
        coachIdNameProfileText.setText(coachId + " / " + coachName);
    }

    private void checkReadedMessages()
    {
        SQLQueries.updateSql(ConnectionUser.MESSAGES, "IsReaded", "1", "UserId", Integer.toString(User.id), "IsReaded", "0");
        checkNewMessages();
    }

    private void checkNewMessages()
    {
        String countNewMessages = SQLQueries.getCount("IsReaded", ConnectionUser.MESSAGES,
                "UserId", Integer.toString(User.id), "IsReaded", "0");
        if (countNewMessages.compareTo("0") == 0)
        {
            notificationMess.setVisible(false);
        }
        else
        {
            notificationMess.setVisible(true);
        }
        notificationMess.setText(countNewMessages);
    }

    @FXML
    public void refreshTimelineTable(Event event)
    {
        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();
        try {
            Statement statement = con.createStatement();
            String query = "Select * From " + ConnectionUser.EXERCISE;
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<TimelineItems> data = FXCollections.observableArrayList();

            while (resultSet.next())
            {
                int id = resultSet.getInt("Id");
                String exercise = resultSet.getString("exercise");
                System.out.println(id);
                System.out.println(exercise);
                data.add(new TimelineItems(id, exercise));
            }

            coachTable.setItems(data);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        refreshMessagesTable(null);
        refreshHomeTexts();
    }

    @FXML
    public void refreshMessagesTable(Event event)
    {
        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();
        try {
            Statement statement = con.createStatement();
            String query = "Select * From " + ConnectionUser.MESSAGES + " Where UserId = " + User.id + " Order By Date DESC";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<MessageItems> data = FXCollections.observableArrayList();

            while (resultSet.next())
            {
                // ID | Name | Report
                String message = resultSet.getString("Message");
                Date date = resultSet.getDate("Date");

                System.out.println(message);
                System.out.println(date);

                data.add(new MessageItems(message, date));
            }

            messagesTable.setItems(data);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void applyEditProfile(Event event)
    {
        boolean isNameEdited, isPassEdited, isAgeEdited;
        isNameEdited = isPassEdited = isAgeEdited = false;
        String name = nameTextfield.getText();

        String password = passwordTextfield.getText();

        String age = ageTextfield.getText();

        if (!name.isEmpty())
        {
            SQLQueries.updateSql(ConnectionUser.MEMBERS, "Name", name, User.id);
            isNameEdited = true;
        }
        if (!password.isEmpty())
        {
            SQLQueries.updateSql(ConnectionUser.MEMBERS, "Password", password, User.id);
            isPassEdited = true;
        }
        if (!age.isEmpty())
        {
            SQLQueries.updateSql(ConnectionUser.MEMBERS, "Age", age, User.id);
            isAgeEdited = true;
        }

        if (isNameEdited || isPassEdited || isAgeEdited)
        {
            String edit = "";
            if (isNameEdited)
                edit += "Name Has Been Updated!\n";
            if (isPassEdited)
                edit += "Password Has Been Updated!\n";
            if (isAgeEdited)
                edit += "Age Has Been Updated!\n";
            SceneManager sceneManager = new SceneManager();
            sceneManager.openAlertBox("Extra/AlertBox.fxml", "Edit Confirmed", edit);
        }

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
}
