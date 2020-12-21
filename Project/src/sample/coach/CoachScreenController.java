package sample.coach;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.ConnectionUser;
import sample.SceneManager;
import sample.User;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CoachScreenController implements Initializable {

    private enum ButtonsActive{
        home,
        timeline,
        members,
        addTimeLine,
        message
    }

    ButtonsActive buttonsActive;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton timelineBtn;

    @FXML
    private JFXButton memberBtn;

    @FXML
    private JFXButton addTimelineBtn;

    @FXML
    private JFXButton messageBtn;

    @FXML
    private JFXButton sendMemberBtn;

    @FXML
    private JFXButton sendAllMemberBtn;

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
    private JFXTextField timelineText;

    // Date
    @FXML
    private DatePicker timelineDate;

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
        initializeTables();
        initializeButtons();

        setTextFieldNumbers(memberIDText);
        setTextFieldTime(timelineText);
    }

    ArrayList<AnchorPane> anchors;
    ArrayList<JFXButton> jfxButtons;
    private void initializeButtons()
    {
        anchors = new ArrayList<>();
        anchors.add(homePage);
        anchors.add(timelinePage);
        anchors.add(membersPage);
        anchors.add(messagesPage);
        anchors.add(addTimelinePage);

        jfxButtons = new ArrayList<>();
        jfxButtons.add(homeBtn);
        jfxButtons.add(timelineBtn);
        jfxButtons.add(memberBtn);
        jfxButtons.add(addTimelineBtn);
        jfxButtons.add(messageBtn);


        homeBtn.setOnAction(e->{
            activePage(homeBtn, homePage, ButtonsActive.home);
        });
        timelineBtn.setOnAction(e->{
            activePage(timelineBtn, timelinePage, ButtonsActive.timeline);
        });
        memberBtn.setOnAction(e->{
            activePage(memberBtn, membersPage, ButtonsActive.members);
        });
        addTimelineBtn.setOnAction(e->{
            activePage(addTimelineBtn, addTimelinePage, ButtonsActive.addTimeLine);
        });
        messageBtn.setOnAction(e->{
            activePage(messageBtn, messagesPage, ButtonsActive.message);
        });
        sendMemberBtn.setOnAction(e->{
            sendMessage(memberTextArea, memberIDText);
        });
        sendAllMemberBtn.setOnAction(e->{
            sendMessage(allMemberTextArea);
        });

        homeBtn.setOnMouseEntered(e->{
            if (buttonsActive == ButtonsActive.home) return;
            changeStyleBackground(homeBtn, "#660708");
        });
        timelineBtn.setOnMouseEntered(e->{
            if (buttonsActive == ButtonsActive.timeline) return;
            changeStyleBackground(timelineBtn, "#660708");
        });
        addTimelineBtn.setOnMouseEntered(e->{
            if (buttonsActive == ButtonsActive.addTimeLine) return;
            changeStyleBackground(addTimelineBtn, "#660708");
        });
        memberBtn.setOnMouseEntered(e->{
            if (buttonsActive == ButtonsActive.members) return;
            changeStyleBackground(memberBtn, "#660708");
        });
        messageBtn.setOnMouseEntered(e->{
            if (buttonsActive == ButtonsActive.message) return;
            changeStyleBackground(messageBtn, "#660708");
        });

        homeBtn.setOnMouseExited(e->{
            if (buttonsActive == ButtonsActive.home) return;
            changeStyleBackground(homeBtn, "");
        });
        timelineBtn.setOnMouseExited(e->{
            if (buttonsActive == ButtonsActive.timeline) return;
            changeStyleBackground(timelineBtn, "");
        });
        addTimelineBtn.setOnMouseEntered(e->{
            if (buttonsActive == ButtonsActive.addTimeLine) return;
            changeStyleBackground(addTimelineBtn, "");
        });
        memberBtn.setOnMouseExited(e->{
            if (buttonsActive == ButtonsActive.members) return;
            changeStyleBackground(memberBtn, "");
        });
        messageBtn.setOnMouseExited(e->{
            if (buttonsActive == ButtonsActive.message) return;
            changeStyleBackground(messageBtn, "");
        });


        //Show Home Page
        homeBtn.fire();
    }

    private void changeStyleBackground(JFXButton button, String color)
    {
        button.setStyle("-fx-background-color: " + color);
    }

    private void activePage(JFXButton button, AnchorPane page, ButtonsActive buttonsActive)
    {
        this.buttonsActive = buttonsActive;
        for (AnchorPane a : anchors)
        {
            if (a != page)
                a.setVisible(false);
            else
                a.setVisible(true);
        }

        changeStyleBackground(button, " #e5383b");
        for (JFXButton b : jfxButtons)
            if (b != button)
                changeStyleBackground(b, "");

    }

    private void setTextFieldTime(JFXTextField textField)
    {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}([\\:]\\d{0,2})?")) {
                textField.setText(oldValue);
            }
        });
    }

    private void setTextFieldNumbers(JFXTextField textField)
    {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}(\\d{0,4})?")) {
                textField.setText(oldValue);
            }
        });
    }

    @FXML
    public void sendMessage(JFXTextArea textArea)
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

    @FXML
    public void sendMessage(JFXTextArea textArea, JFXTextField idMemberTextField)
    {
        String str = textArea.getText();
        int id = Integer.parseInt(idMemberTextField.getText());

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

    private void initializeTables()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        exerciseCol.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        idColMem.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColMem.setCellValueFactory(new PropertyValueFactory<>("name"));
        exerciseIdColMem.setCellValueFactory(new PropertyValueFactory<>("exerciseId"));

        refreshTimelineTable(null);
    }

    public void addNewTimeline(Event event)
    {
        LocalDate date = timelineDate.getValue();
        String exerciseName = exerciseNameText.getText();
        String time = timelineText.getText() + ":00";

        ConnectionUser connectionUser = new ConnectionUser();
        Connection con = connectionUser.getConnection();

        try {
            String sql = "insert into exercises (coachID, exercise, date, time) values (?, ?, ?, ?);";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, User.id);
            preparedStatement.setString(2, exerciseName);
            preparedStatement.setDate(3, java.sql.Date.valueOf(date));
            preparedStatement.setTime(4, Time.valueOf(time));
            preparedStatement.execute();
            preparedStatement.close();

            System.out.println("Succ");
        } catch (SQLException throwables) {
            System.out.println("Fail");
            throwables.printStackTrace();
        }
    }

    /*
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
    */

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

