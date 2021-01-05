package sample.admin.Coach;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.ConnectionUser;
import sample.SceneManager;
import sample.admin.FetchData.FetchCoach;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class Coach implements Initializable {
    SceneManager sceneManger = new SceneManager();
        public enum ButtonsActive {
            add,
            search,
            list,
            update,
            delete
        }
        ButtonsActive buttonsActives;
        ArrayList<AnchorPane> anchorss = new ArrayList<AnchorPane>();
        ArrayList<JFXButton> anchorsButtonss = new ArrayList<JFXButton>();
        /** Coach*/
        @FXML
        private AnchorPane coachAdd;
        @FXML
        private AnchorPane coachSearch;
        @FXML
        private AnchorPane coachList;
        @FXML
        private AnchorPane coachUpdate;
        @FXML
        private AnchorPane coachDelete;

        /**Buttons*/
        @FXML
        private JFXButton addButtonCoach;
        @FXML
        private JFXButton searchButtonCoach;
        @FXML
        private JFXButton listButtonCoach;
        @FXML
        private JFXButton updateButtonCoach;
        @FXML
        private JFXButton deleteButtonCoach;

        @FXML
        private JFXButton coachBack;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            anchorss.add(coachAdd);
            anchorss.add(coachSearch);
            anchorss.add(coachList);
            anchorss.add(coachUpdate);
            anchorss.add(coachDelete);

            updateTable();
            updateTable1();
            updateTable2();

            anchorsButtonss.add(addButtonCoach);
            anchorsButtonss.add(searchButtonCoach);
            anchorsButtonss.add(listButtonCoach);
            anchorsButtonss.add(updateButtonCoach);
            anchorsButtonss.add(deleteButtonCoach);
            /** For loop*/
            int i = 0;
            for (JFXButton b : anchorsButtonss)
            {
                int finalI = i;
                b.setOnAction(e->{
                    activePage(b, anchorsButtonss, anchorss.get(finalI), anchorss);
                    buttonsActives = ButtonsActive.values()[finalI];
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
    public void backPages()
    {
        coachBack.setOnAction(e->actions());
    }
    public void actions()
    {
        SceneManager sceneManger = new SceneManager();
        sceneManger.changeScene("admin/Admin/Admin.fxml","admin");
    }
    /**Adding Coach*/
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField passwordField;
    @FXML
    private JFXTextField ageField;
    public void OnClickSubmit1(ActionEvent actionEvent){
        ConnectionUser addUser = new ConnectionUser();
        Connection con = addUser.getConnection();
        String name = nameField.getText();
        String password = passwordField.getText();
        String age = ageField.getText();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String dbop = "INSERT INTO coachdata (Name, Password, Age) VALUES('" + name + "','" + password + "','" + age + "')";
            stmt.execute(dbop);
            stmt.close();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Add Coach","Added Successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Add Coach","Failed Adding!");
        }
        nameField.setText("");
        passwordField.setText("");
        ageField.setText("");
    }

    /**Search Coach*/
    SearchCoach searchCoach = new SearchCoach();
    @FXML
    private TableView<FetchCoach> tableView;
    @FXML
    private TableColumn<FetchCoach, Integer> idColumn;
    @FXML
    private TableColumn<FetchCoach, String> nameColumn;
    @FXML
    private TableColumn<FetchCoach, String> passwordColumn;
    @FXML
    private JFXTextField searchById;
    @FXML
    private TableColumn<FetchCoach, Integer> ageColumn;
    public void OnClickSearch() {
        tableView.getItems().clear();
        ObservableList<FetchCoach> data = FXCollections.observableArrayList();
        ConnectionUser addUser = new ConnectionUser();
        Connection con = addUser.getConnection();
        Statement stmt = null;
        String ids = searchById.getText();
        try {
            stmt = con.createStatement();
            String dbop = "select * from coachdata where Id =" + ids;
            stmt.execute(dbop);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            String name = rs.getString("Name");
            String password = rs.getString("Password");
            int id = rs.getInt("Id");
            int age = rs.getInt("Age");
            data.add(new FetchCoach(id,name,password,age));
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Search Coach","Didn`t exist!");
        }
        tableView.setItems(data);
        searchById.setText("");
    }
    public void updateTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<FetchCoach, Integer>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<FetchCoach, String>("Name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<FetchCoach, String>("Password"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<FetchCoach, Integer>("Age"));
        tableView.setItems(searchCoach.getData());
    }
    /**List Coach */
    ListCoach listCoach = new ListCoach();
    @FXML
    private TableView<FetchCoach> tableView1;
    @FXML
    private TableColumn<FetchCoach, Integer> idColumn1;
    @FXML
    private TableColumn<FetchCoach, String> nameColumn1;
    @FXML
    private TableColumn<FetchCoach, String> passwordColumn1;
    @FXML
    private TableColumn<FetchCoach, Integer> ageColumn1;

    public void updateTable1(){
        idColumn1.setCellValueFactory(new PropertyValueFactory<FetchCoach, Integer>("Id"));
        nameColumn1.setCellValueFactory(new PropertyValueFactory<FetchCoach, String>("Name"));
        passwordColumn1.setCellValueFactory(new PropertyValueFactory<FetchCoach, String>("Password"));
        ageColumn1.setCellValueFactory(new PropertyValueFactory<FetchCoach, Integer>("Age"));
        tableView1.setItems(listCoach.getData());
    }
    public void OnClickRefresh(){
        updateTable();
        updateTable1();
        updateTable2();
    }
    /**Update Coach*/
    UpdataCoach updataCoach = new UpdataCoach();
    @FXML
    private JFXTextField newName;
    @FXML
    private JFXTextField newPassword;
    @FXML
    private JFXTextField idWillUpdate;
    @FXML
    private JFXTextField newAgeField;

    public void OnClickUpdate(ActionEvent actionEvent){
        ConnectionUser addUser = new ConnectionUser();
        Connection con = addUser.getConnection();
        String name = newName.getText();
        int id = Integer.parseInt(idWillUpdate.getText());
        String age = newAgeField.getText();
        String password = newPassword.getText();
        if(!name.isEmpty() || !name.isBlank()){
            updataCoach.update("Name",name,id,con);
        }
        if(!password.isEmpty() || !password.isBlank()){
            updataCoach.update("Password",password,id,con);
        }
        if(!age.isEmpty() || !age.isBlank()){
            updataCoach.update("Age",age,id,con);
        }
        newName.setText("");
        newPassword.setText("");
        idWillUpdate.setText("");
        newAgeField.setText("");
    }
    /**Delete Coach*/
    DeleteCoach deleteCoach = new DeleteCoach();
    @FXML
    private JFXTextField idField2;
    @FXML
    private TableView<FetchCoach> tableview2;
    @FXML
    private TableColumn<FetchCoach, Integer> idColumn2;
    @FXML
    private TableColumn<FetchCoach, String> nameColumn2;
    @FXML
    private TableColumn<FetchCoach, String> passwordColumn2;
    @FXML
    private TableColumn<FetchCoach, Integer> ageColumn2;
    public void updateTable2(){
        idColumn2.setCellValueFactory(new PropertyValueFactory<FetchCoach, Integer>("Id"));
        nameColumn2.setCellValueFactory(new PropertyValueFactory<FetchCoach, String>("Name"));
        passwordColumn2.setCellValueFactory(new PropertyValueFactory<FetchCoach, String>("Password"));
        ageColumn2.setCellValueFactory(new PropertyValueFactory<FetchCoach, Integer>("Age"));
        tableview2.setItems(deleteCoach.getData());
    }
    public void OnClickDelete(ActionEvent actionEvent) {
        ConnectionUser delUser = new ConnectionUser();
        Connection con = delUser.getConnection();
        String Id = idField2.getText();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String dbop = "delete from coachdata where Id = " + Id ;
            stmt.execute(dbop);
            stmt.close();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Delete Coach","Deleted Successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sceneManger.openAlertBox("Extra/AlertBox.fxml","Delete Coach","Failed Deleting!");
        }
        idField2.setText("");
    }
}