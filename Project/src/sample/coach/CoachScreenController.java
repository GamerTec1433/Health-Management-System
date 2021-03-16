package sample.coach;

import com.jfoenix.controls.*;
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
import javafx.scene.layout.VBox;
import sample.*;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static sample.SQLQueries.sendMessageSQL;

public class CoachScreenController implements Initializable {

    UIControllers.ButtonsCoachActive buttonsCoachActive;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton timelineBtn;

    @FXML
    private JFXButton memberBtn;

    @FXML
    private  JFXButton profileBtn;

    @FXML
    private JFXButton addTimelineBtn;

    @FXML
    private JFXButton messageBtn;

    @FXML
    private JFXButton sendMemberBtn;

    @FXML
    private JFXButton sendAllMemberBtn;

    // Labels
    @FXML
    private Label idProfileText;

    @FXML
    private Label nameProfileText;

    @FXML
    private Label ageProfileText;

    @FXML
    private Label homeMemText;

    @FXML
    private Label homeExerText;

    @FXML
    private Label homePlusText;

    // Radio
    @FXML
    private JFXRadioButton sendMessOneRadio;

    @FXML
    private JFXRadioButton sendMessAllRadio;

    // VBox
    @FXML
    private VBox sendOneVbox;

    @FXML
    private VBox sendAllVbox;

    // Anchors
    @FXML
    private AnchorPane homePage;

    @FXML
    private AnchorPane timelinePage;

    @FXML
    private AnchorPane addTimelinePage;

    @FXML
    private AnchorPane membersPage;

    @FXML
    private AnchorPane profilePage;

    @FXML
    private AnchorPane messagesPage;

    // TextFields And TextAreas
    @FXML
    private JFXTextField memberIDText;

    @FXML
    private JFXTextArea memberTextArea;

    @FXML
    private JFXTextArea allMemberTextArea;

    @FXML
    private JFXTextField exerciseNameText;

    @FXML
    private JFXTextField exerciseIDText;

    @FXML
    private JFXTextField nameTextfield;

    @FXML
    private JFXTextField passwordTextfield;

    @FXML
    private JFXTextField ageTextfield;


    // Timeline Table
    public TableView<TimelineItems> coachTable;
    @FXML
    private TableColumn<TimelineItems, Integer> idCol;

    @FXML
    private TableColumn<TimelineItems, String> exerciseCol;

    // Members Table
    @FXML
    private TableView<MemberItems> memberTable;

    @FXML
    private TableColumn<MemberItems, Integer> idColMem;

    @FXML
    private TableColumn<MemberItems, String> nameColMem;

    @FXML
    private TableColumn<MemberItems, String> exerciseIdColMem;


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        initializeTables();

        UIControllers.setTextFieldNumbers(memberIDText);

        initializeButtons();
        showProfile();
        showHomeAnalysis();
    }

    public void showHomeAnalysis() {
        homeMemText.setText(SQLQueries.getCount("Id", ConnectionUser.MEMBERS, "CoachId", Integer.toString(User.id)));

        homeExerText.setText(SQLQueries.getCount("Id", ConnectionUser.EXERCISE));

        System.out.println(Date.valueOf(LocalDate.now()).toString());
        homePlusText.setText(SQLQueries.getCount("Sdate", ConnectionUser.BILLING, "Sdate", Date.valueOf(LocalDate.now()).toString()));
    }

    ArrayList<AnchorPane> anchors;
    ArrayList<JFXButton> jfxButtons;
    private void initializeButtons() {
        anchors = new ArrayList<>();
            anchors.add(homePage);
            anchors.add(timelinePage);
            anchors.add(membersPage);
            anchors.add(profilePage);
            anchors.add(messagesPage);
            anchors.add(addTimelinePage);

        jfxButtons = new ArrayList<>();
            jfxButtons.add(homeBtn);
            jfxButtons.add(timelineBtn);
            jfxButtons.add(memberBtn);
            jfxButtons.add(profileBtn);
            jfxButtons.add(messageBtn);
            jfxButtons.add(addTimelineBtn);


        int i = 0;
        for (JFXButton b : jfxButtons)
        {
            int finalI = i;
            b.setOnAction(e->{
                UIControllers.activePage(b, jfxButtons, anchors.get(finalI), anchors, ProjectColors.WHITE_COLOR, ProjectColors.WHITE_COLOR, 0, 0);
                buttonsCoachActive = UIControllers.ButtonsCoachActive.values()[finalI];
            });
            b.setOnMouseEntered(e->{
                if (buttonsCoachActive == UIControllers.ButtonsCoachActive.values()[finalI]) return;
                UIControllers.changeStyleBackground(b, ProjectColors.SECONDARY_COLOR, ProjectColors.WHITE_COLOR, 0);
            });
            b.setOnMouseExited(e->{
                if (buttonsCoachActive == UIControllers.ButtonsCoachActive.values()[finalI]) return;
                UIControllers.changeStyleBackground(b, ProjectColors.TRANSPARENT, ProjectColors.WHITE_COLOR, 0);
            });
            i++;
        }


        sendMessOneRadio.setOnAction(e-> {
            sendOneVbox.setVisible(true);
            sendAllVbox.setVisible(false);
        });

        sendMessAllRadio.setOnAction(e->{
            sendOneVbox.setVisible(false);
            sendAllVbox.setVisible(true);
        });

        sendMemberBtn.setOnAction(e->{
            sendMessage(memberTextArea, memberIDText);
        });

        sendAllMemberBtn.setOnAction(e->{
            sendMessage(allMemberTextArea);
        });

        //Show Home Page
        homeBtn.fire();
    }

    @FXML
    public void sendMessage(JFXTextArea textArea) {
        String str = textArea.getText();

        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();

        boolean isMessageSent = false;
        try {
            String sql = "Select * From " + ConnectionUser.MEMBERS + " Where coachId = " + User.id;
            Statement statement = con.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next())
            {
                isMessageSent = SQLQueries.sendMessageSQL(str, con, isMessageSent, resultSet);
            }
            statement.cancel();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (isMessageSent)
        {
            SceneManager sceneManager = new SceneManager();
            sceneManager.openAlertBox("Extra/AlertBox.fxml", "Sending Message", "Message Successfully Sent!");
        }
        else
        {
            SceneManager sceneManager = new SceneManager();
            sceneManager.openAlertBox("Extra/AlertBox.fxml", "Sending Message", "Error in sending message, please try again!");
        }
    }

    @FXML
    public void sendMessage(JFXTextArea textArea, JFXTextField idMemberTextField) {
        String str = textArea.getText();
        int id = Integer.parseInt(idMemberTextField.getText());

        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();

        boolean isMessageSent = false;
        try {
            String sql = "Select * From " + ConnectionUser.MEMBERS + " Where coachId = " + User.id;
            Statement statement = con.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next())
            {
                if (resultSet.getInt("id") == id)
                {
                    isMessageSent = SQLQueries.sendMessageSQL(str, con, isMessageSent, resultSet);
                    break;
                }
            }
            statement.cancel();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (isMessageSent)
        {
            SceneManager sceneManager = new SceneManager();
            sceneManager.openAlertBox("Extra/AlertBox.fxml", "Sending Message", "Message Successfully Sent!");
        }
        else
        {
            SceneManager sceneManager = new SceneManager();
            sceneManager.openAlertBox("Extra/AlertBox.fxml", "Sending Message", "Error in sending message, please try again!");
        }
    }

    public void addNewTimeline(Event event) {
        boolean isAdded = false;
        String exerciseName = exerciseNameText.getText();

        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();

        try {
            String sql = "insert into " + ConnectionUser.EXERCISE + " (exercise) values (?);";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, exerciseName);
            preparedStatement.execute();
            preparedStatement.close();

            isAdded = true;
        } catch (SQLException throwables) {
            isAdded = false;
            throwables.printStackTrace();
        }

        SceneManager sceneManager = new SceneManager();
        if (isAdded)
        {
            sceneManager.openAlertBox("Extra/AlertBox.fxml", "Timeline Added", "Timeline added");
        }
        else {
            sceneManager.openAlertBox("Extra/AlertBox.fxml", "Timeline Error", "Error In Adding Timeline Please Try Again");
        }

        refreshTimelineTable(null);
    }
    public void deleteNewTimeline(Event event) {
        boolean isAdded = false;
        String exerciseId = exerciseIDText.getText();

        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();

        try {
            String sql = "delete from " + ConnectionUser.EXERCISE + " where Id = ?" ;
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, exerciseId);
            preparedStatement.execute();
            preparedStatement.close();

            isAdded = true;
        } catch (SQLException throwables) {
            isAdded = false;
            throwables.printStackTrace();
        }


        SceneManager sceneManager = new SceneManager();
        if (isAdded)
        {
            sceneManager.openAlertBox("Extra/AlertBox.fxml", "Timeline Deleted", "Timeline Deleted");
        }
        else {
            sceneManager.openAlertBox("Extra/AlertBox.fxml", "Timeline Error", "Error In Deleting Timeline Please Try Again");
        }

        refreshTimelineTable(null);
    }

    private void initializeTables() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        exerciseCol.setCellValueFactory(new PropertyValueFactory<>("exercise"));

        idColMem.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColMem.setCellValueFactory(new PropertyValueFactory<>("name"));
        exerciseIdColMem.setCellValueFactory(new PropertyValueFactory<>("report"));

        refreshTimelineTable(null);
        refreshMembersTable(null);
    }

    @FXML
    public void refreshTimelineTable(Event event) {
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
                data.add(new TimelineItems(id, exercise));
            }

            coachTable.setItems(data);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void refreshMembersTable(Event event) {
        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();
        try {
            Statement statement = con.createStatement();
            String query = "Select * From " + ConnectionUser.MEMBERS + " Where coachId = " + User.id;
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<MemberItems> data = FXCollections.observableArrayList();

            while (resultSet.next())
            {
                // ID | Name | Report
                int id = resultSet.getInt("Id");
                String exercise = resultSet.getString("Name");
                String exerciseId = resultSet.getString("Report");

                data.add(new MemberItems(id, exercise, exerciseId));
            }

            memberTable.setItems(data);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    private void applyEditProfile(Event event) {
        boolean isNameEdited, isPassEdited, isAgeEdited;
        isNameEdited = isPassEdited = isAgeEdited = false;
        String name = nameTextfield.getText();

        String password = passwordTextfield.getText();

        String age = ageTextfield.getText();

        if (!name.isEmpty())
        {
            SQLQueries.updateSql(ConnectionUser.COACHES, "Name", name, User.id);
            isNameEdited = true;
        }
        if (!password.isEmpty())
        {
            SQLQueries.updateSql(ConnectionUser.COACHES, "Password", password, User.id);
            isPassEdited = true;
        }
        if (!age.isEmpty())
        {
            SQLQueries.updateSql(ConnectionUser.COACHES, "Age", age, User.id);
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
        showProfile();
    }
    public void showProfile() {
        nameProfileText.setText(SQLQueries.getString("Name", ConnectionUser.COACHES, User.id));
        ageProfileText.setText(Integer.toString(SQLQueries.getInt("Age", ConnectionUser.COACHES, User.id)));
        idProfileText.setText(Integer.toString(SQLQueries.getInt("Id", ConnectionUser.COACHES, User.id)));
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

