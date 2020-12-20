package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

public class Main extends Application {

    public static Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        window = primaryStage;
        window.setTitle("Health Club Management System");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.show();

        Date date = new Date();
        date.setTime(100000);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
