package sample.admin.Member;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.ConnectionUser;
import sample.SceneManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import sample.admin.FetchData.FetchMember;

import java.sql.*;
public class Member implements Initializable {
    SceneManager sceneManger = new SceneManager();
    public enum ButtonsActive {
        add,
        search,
        list,
        update,
        delete,
        report
    }
    ButtonsActive buttonsActive;
    ArrayList<AnchorPane> anchors = new ArrayList<AnchorPane>();
    ArrayList<JFXButton> anchorsButtons = new ArrayList<JFXButton>();
    /** Member*/
    @FXML
    private AnchorPane memberAdd;
    @FXML
    private AnchorPane memberSearch;
    @FXML
    private AnchorPane memberList;
    @FXML
    private AnchorPane memberUpdate;
    @FXML
    private AnchorPane memberDelete;
    @FXML
    private AnchorPane memberReport;

    /**Buttons*/
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXButton listButton;
    @FXML
    private JFXButton updateButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXButton reportButton;

    /** modifying  */
    @FXML
    private JFXButton memberBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchors.add(memberAdd);
        anchors.add(memberSearch);
        anchors.add(memberList);
        anchors.add(memberUpdate);
        anchors.add(memberDelete);
        anchors.add(memberReport);

        anchorsButtons.add(addButton);
        anchorsButtons.add(searchButton);
        anchorsButtons.add(listButton);
        anchorsButtons.add(updateButton);
        anchorsButtons.add(deleteButton);
        anchorsButtons.add(reportButton);

        updateTable();
        updateTable1();
        updateTable2();
        /** For loop*/
        int i = 0;
        for (JFXButton b : anchorsButtons)
        {
            int finalI = i;
            b.setOnAction(e->{
                activePage(b, anchorsButtons, anchors.get(finalI), anchors);
                buttonsActive = ButtonsActive.values()[finalI];
            });
            i++;
        }
    }

    public void activePage(JFXButton button, ArrayList<JFXButton> jfxButtons, AnchorPane page, ArrayList<AnchorPane> anchors)
    {
        for (AnchorPane a : anchors)
        {
            if (a != page)
                a.setVisible(false);
            else
                a.setVisible(true);
        }
    }
    public void backPage()
    {
        memberBack.setOnAction(e->actions());
    }
    public void actions()
    {
        SceneManager sceneManger = new SceneManager();
        sceneManger.changeScene("admin/Admin/Admin.fxml","admin");
    }

    /**    Adding Member    */
    AddMember addMember = new AddMember();
    @FXML
    private JFXTextField passwordField;
    @FXML
    private JFXTextField coachidField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField ageField;
    public void OnClickSubmit(ActionEvent actionEvent){
        ConnectionUser addUser = new ConnectionUser();
        Connection con = addUser.getConnection();
        String name = nameField.getText();
        String password = passwordField.getText();
        String age = ageField.getText();
        String coachid = coachidField.getText();
        PreparedStatement stmt = null;
        try {
            addMember.addUser(con, name, password, age, coachid);

            addMember.addBilling(con, password);

            //AlertBox.display("Checking","Added Successfully!");
           sceneManger.openAlertBox("Extra/AlertBox.fxml","Add Member","Added Successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Add Member","Failed Adding!");
        }
        nameField.setText("");
        passwordField.setText("");
        ageField.setText("");
        coachidField.setText("");

    }
    /** Search Member*/
    SearchMember searchMember = new SearchMember();
    @FXML
    private TableView<FetchMember> tableView;
    @FXML
    private TableColumn<FetchMember, Integer> idColumn;
    @FXML
    private TableColumn<FetchMember, String> nameColumn;
    @FXML
    private TableColumn<FetchMember, String> reportColumn;
    @FXML
    private TableColumn<FetchMember, Integer> coachIdColumn;
    @FXML
    private TableColumn<FetchMember, String> passwordColumn;
    @FXML
    private TableColumn<FetchMember, Integer> ageColumn;
    @FXML
    private TableColumn<FetchMember, Date> subDateColumn;
    @FXML
    private JFXTextField searchById;
    public  void OnClickSearch() {
        tableView.getItems().clear();
        ObservableList<FetchMember> data = FXCollections.observableArrayList();
        ConnectionUser addUser = new ConnectionUser();
        Connection con = addUser.getConnection();
        Statement stmt = null;
        String ids = searchById.getText();
        try {
            stmt = con.createStatement();
            String dbop = "select * from usersdata where Id =" + ids;
            stmt.execute(dbop);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            int id = rs.getInt("Id");
            String name = rs.getString("Name");
            String password = rs.getString("Password");
            int age = rs.getInt("Age");
            String report = rs.getString("Report");
            Date subDate = rs.getDate("SubDate");
            int coachId = rs.getInt("CoachId");
            data.add(new FetchMember(id,name,password,age,report,subDate,coachId));
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Search Member","Didn't exist!");
        }
        tableView.setItems(data);
        searchById.setText("");
    }
    public void updateTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<FetchMember, Integer>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<FetchMember, String>("Name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<FetchMember, String>("Password"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<FetchMember, Integer>("Age"));
        reportColumn.setCellValueFactory(new PropertyValueFactory<FetchMember, String>("Report"));
        subDateColumn.setCellValueFactory(new PropertyValueFactory<FetchMember, Date>("SubDate"));
        coachIdColumn.setCellValueFactory(new PropertyValueFactory<FetchMember, Integer>("CoachId"));
        tableView.setItems(searchMember.getData());
    }
    /** ListMember */
    ListMember listMember = new ListMember();
    @FXML
    private TableView<FetchMember> tableView1;
    @FXML
    private TableColumn<FetchMember, Integer> idColumn1;
    @FXML
    private TableColumn<FetchMember, String> nameColumn1;
    @FXML
    private TableColumn<FetchMember, String> reportColumn1;
    @FXML
    private TableColumn<FetchMember, Integer> coachIdColumn1;
    @FXML
    private TableColumn<FetchMember, String> passwordColumn1;
    @FXML
    private TableColumn<FetchMember, Integer> ageColumn1;
    @FXML
    private TableColumn<FetchMember, Date> subDateColumn1;
    public void updateTable1(){
        idColumn1.setCellValueFactory(new PropertyValueFactory<FetchMember, Integer>("Id"));
        nameColumn1.setCellValueFactory(new PropertyValueFactory<FetchMember, String>("Name"));
        passwordColumn1.setCellValueFactory(new PropertyValueFactory<FetchMember, String>("Password"));
        ageColumn1.setCellValueFactory(new PropertyValueFactory<FetchMember, Integer>("Age"));
        reportColumn1.setCellValueFactory(new PropertyValueFactory<FetchMember, String>("Report"));
        subDateColumn1.setCellValueFactory(new PropertyValueFactory<FetchMember, Date>("SubDate"));
        coachIdColumn1.setCellValueFactory(new PropertyValueFactory<FetchMember, Integer>("CoachId"));
        tableView1.setItems(listMember.getData());
    }
    public void OnClickRefresh(){
        updateTable();
        updateTable1();
        updateTable2();
    }
    /**Update Member*/
    UpdateMember updateMember = new UpdateMember();
    @FXML
    private JFXTextField newName;
    @FXML
    private JFXTextField newBilling;
    @FXML
    private JFXTextField newCoachId;
    @FXML
    private JFXTextField newReport;
    @FXML
    private JFXTextField idWillUpdate;
    @FXML
    private JFXTextField newPassword;
    @FXML
    private JFXTextField newAge;
    public void OnClickUpdate(ActionEvent actionEvent){
        ConnectionUser addUser = new ConnectionUser();
        Connection con = addUser.getConnection();
        String name = newName.getText();
        String password = newPassword.getText();
        int id = Integer.parseInt(idWillUpdate.getText());
        String age = newAge.getText();
        String billing = newBilling.getText();
        String coachid = newCoachId.getText();
        String report = newReport.getText();
        if(!name.isEmpty() || !name.isBlank()){
            updateMember.update("Name",name,id,con);
        }
        if(!report.isEmpty() || !report.isBlank()){
            updateMember.update("Report",report,id,con);
        }
        if(!billing.isEmpty() || !billing.isBlank()){
            updateMember.updateBilling(Integer.parseInt(billing) ,id ,con);
        }
        if(!coachid.isEmpty() || !coachid.isBlank()){
            updateMember.update("CoachID",Integer.parseInt(coachid),id,con);
        }
        if(!password.isEmpty() || !password.isBlank()){
            updateMember.update("Password",password,id,con);
        }
        if(!age.isEmpty() || !age.isBlank()){
            updateMember.update("Age",Integer.parseInt(age),id,con);
        }
         newName.setText("");
         newBilling.setText("");
         newCoachId.setText("");
         newReport.setText("");
         newAge.setText("");
         idWillUpdate.setText("");
         newPassword.setText("");

    }

    /**Delete Member*/
    DeleteMember deleteMember = new DeleteMember();
    @FXML
    private JFXTextField idField;
    @FXML
    private TableView<FetchMember> tableView2;
    @FXML
    private TableColumn<FetchMember, Integer> idColumn2;
    @FXML
    private TableColumn<FetchMember, String> nameColumn2;
    @FXML
    private TableColumn<FetchMember, String> reportColumn2;
    @FXML
    private TableColumn<FetchMember, Integer> coachIdColumn2;
    @FXML
    private TableColumn<FetchMember, String> passwordColumn2;
    @FXML
    private TableColumn<FetchMember, Integer> ageColumn2;
    @FXML
    private TableColumn<FetchMember, Date> subDateColumn2;
    public void updateTable2(){
        idColumn2.setCellValueFactory(new PropertyValueFactory<FetchMember, Integer>("Id"));
        nameColumn2.setCellValueFactory(new PropertyValueFactory<FetchMember, String>("Name"));
        passwordColumn2.setCellValueFactory(new PropertyValueFactory<FetchMember, String>("Password"));
        ageColumn2.setCellValueFactory(new PropertyValueFactory<FetchMember, Integer>("Age"));
        reportColumn2.setCellValueFactory(new PropertyValueFactory<FetchMember, String>("Report"));
        subDateColumn2.setCellValueFactory(new PropertyValueFactory<FetchMember, Date>("SubDate"));
        coachIdColumn2.setCellValueFactory(new PropertyValueFactory<FetchMember, Integer>("CoachId"));
        tableView2.setItems(deleteMember.getData());
    }
    public void OnClickDelete(ActionEvent actionEvent) {
        ConnectionUser delUser = new ConnectionUser();
        Connection con = delUser.getConnection();
        String Id = idField.getText();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String dbop = "delete from usersdata where Id = " + Id ;
            stmt.execute(dbop);
            stmt.close();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Delete Member","Deleted Successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Delete Member","Failed Deleting!");
        }
        idField.setText("");
    }
    /**ReportMember*/
    @FXML
    private JFXTextField idFieldReport;
    @FXML
    private JFXTextField reportField;
    public void OnClickSubmitt(ActionEvent actionEvent){
        ConnectionUser addUser = new ConnectionUser();
        Connection con = addUser.getConnection();
        String id = idFieldReport.getText();
        String report = reportField.getText();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String dbop = "update usersdata set Report = '" + report +"' where Id = "+ id;
            stmt.execute(dbop);
            stmt.close();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Search Member","Report Added Successfully To User!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Search Member","Failed To Add Report To user!");
        }
        idFieldReport.setText("");
        reportField.setText("");
    }
}