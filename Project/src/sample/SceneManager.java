package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Extra.AlertBoxController;

import java.io.IOException;

public class SceneManager {

    public void changeScene(String fxmlFile, String title)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Main.window.setTitle(title);
            Main.window.setScene(new Scene(root));
            Main.window.show();
        } catch (IOException e) {
            openAlertBox("Extra/AlertBox.fxml", "Error", "Error happened, Please Try again");
            e.printStackTrace();
        }
    }

    public void openAlertBox(String fxmlFile, String title, String message)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            AlertBoxController alertBox = loader.getController();
            alertBox.setTitleAndMessage(title, message);

            Stage newWindow = new Stage();
            newWindow.setTitle(title);
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.setResizable(false);

            newWindow.setScene(new Scene(root));
            newWindow.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
