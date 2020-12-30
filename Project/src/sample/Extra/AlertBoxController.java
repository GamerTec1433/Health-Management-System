package sample.Extra;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlertBoxController {

    @FXML
    private Label headerAlert;

    @FXML
    private Label logAlert;

    public void setTitleAndMessage(String title, String message) {
        headerAlert.setText(title);
        logAlert.setText(message);
    }

    public void closeWindow(Event event)
    {
        Stage stage = (Stage) headerAlert.getScene().getWindow();
        stage.close();
    }
}
