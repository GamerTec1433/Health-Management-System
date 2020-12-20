package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    public void changeScene(String fxmlFile, String title)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));

            Main.window.setTitle(title);
            Main.window.setScene(new Scene(root));
            Main.window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openWindow(String fxmlFile, String title)
    {
        try {
            Stage newWindow = new Stage();
            newWindow.setTitle(title);
            newWindow.initModality(Modality.APPLICATION_MODAL);

            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            newWindow.setScene(new Scene(root));
            newWindow.setResizable(false);
            newWindow.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
