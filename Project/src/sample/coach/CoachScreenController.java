package sample.coach;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML
    private  JFXButton updateProfileBtn;

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
    }

    ArrayList<AnchorPane> anchors;
    ArrayList<JFXButton> jfxButtons;
    private void initializeButtons()
    {
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
                System.out.println(UIControllers.ButtonsCoachActive.values()[finalI] + " " + buttonsCoachActive);
                UIControllers.changeStyleBackground(b, ProjectColors.SECONDARY_COLOR, ProjectColors.BLACK_COLOR, -.8);
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
            sendMessage(allMemberTextArea);
        });

        sendAllMemberBtn.setOnAction(e->{
            sendMessage(memberTextArea, memberIDText);
        });

        //Show Home Page
        homeBtn.fire();
    }

    @FXML
    public void sendMessage(JFXTextArea textArea)
    {
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
                isMessageSent = sendMessageSQL(str, con, isMessageSent, resultSet);
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
            String sql = "Select * From " + ConnectionUser.MEMBERS + " Where coachId = " + User.id;
            Statement statement = con.createStatement();
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next())
            {
                if (resultSet.getInt("id") == id)
                {
                    isMessageSent = sendMessageSQL(str, con, isMessageSent, resultSet);
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

    private boolean sendMessageSQL(String str, Connection con, boolean isMessageSent, ResultSet resultSet) throws SQLException {
        String sqlInsert = "Insert Into " + ConnectionUser.MESSAGES + " Values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
        preparedStatement.setInt(1, resultSet.getInt("id"));
        preparedStatement.setString(2, str);
        preparedStatement.setDate(3, Date.valueOf(LocalDate.now().toString()));
        preparedStatement.setInt(4, User.id);
        preparedStatement.setBoolean(5, false);
        preparedStatement.execute();
        preparedStatement.close();
        isMessageSent = true;
        return true;
    }



    public void addNewTimeline(Event event)
    {
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

        if (isAdded)
        {
            AlertBox.display("Timeline Added", "Timeline added");
        }
        else {
            AlertBox.display("Timeline Error", "Error In Adding Timeline\nPlease Try Again");
        }

        refreshTimelineTable(null);
    }



    private void initializeTables()
    {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        exerciseCol.setCellValueFactory(new PropertyValueFactory<>("exercise"));

        idColMem.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColMem.setCellValueFactory(new PropertyValueFactory<>("name"));
        exerciseIdColMem.setCellValueFactory(new PropertyValueFactory<>("report"));

        refreshTimelineTable(null);
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

        refreshMembersTable(null);
    }

    @FXML
    public void refreshMembersTable(Event event)
    {
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

                System.out.println(id);
                System.out.println(exercise);
                System.out.println(exerciseId);

                data.add(new MemberItems(id, exercise, exerciseId));
            }

            memberTable.setItems(data);

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

        if (!name.isEmpty() && !name.isBlank())
        {
            updateSql(ConnectionUser.COACHES, "Name", name, User.id);
            isNameEdited = true;
        }
        if (!password.isEmpty() && !password.isBlank())
        {
            updateSql(ConnectionUser.COACHES, "Password", password, User.id);
            isPassEdited = true;
        }
        if (!age.isEmpty() && !age.isBlank())
        {
            updateSql(ConnectionUser.COACHES, "Age", age, User.id);
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
            AlertBox.display("Edit Confirmed", edit);
        }

    }

    private void updateSql(String table, String column, String value, int userId)
    {
        try {
            ConnectionUser connectionUser = new ConnectionUser();
            Connection conn = connectionUser.getConnection();

            String sql = "update " + table + " set " + column + " = ? where Id = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

