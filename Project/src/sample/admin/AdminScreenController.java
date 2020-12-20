package sample.admin;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import java.beans.EventHandler;
import java.io.IOException;

public class AdminScreenController {
    public Button addBtn;
    public Button deleteBtn;
    public Button updateBtn;
    public Button listBtn;
    public Button manageBilBtn;
    public Button reportBtn;
    public Button changeMemCouchBtn;
    public Button searchBtn;

    @FXML
    public void checkButtonsClick(Event event)
    {
        if (event.getSource() == addBtn)
        {
            openWindow("AddAdminWindow/AddAdminWindow.fxml");
        }
    }

    @FXML
    public void openWindow(String fxmlFile)
    {
        try {
            Stage newWindow = new Stage();
            newWindow.setTitle("Add New Member");
            newWindow.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            newWindow.setScene(new Scene(root));
            newWindow.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
