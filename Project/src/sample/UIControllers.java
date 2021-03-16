package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class UIControllers {

    public enum ButtonsMemberActive {
        home,
        timeline,
        profile,
        message
    }

    public enum ButtonsCoachActive {
        home,
        timeline,
        members,
        profile,
        message,
        addTimeLine
    }

    public static void activePage(JFXButton button, ArrayList<JFXButton> jfxButtons, AnchorPane page, ArrayList<AnchorPane> anchors, String textColor1, String textColor2, double imageBrightness1, double imageBrightness2) {
        for (AnchorPane a : anchors)
        {
            if (a != page)
                a.setVisible(false);
            else
                a.setVisible(true);
        }

        changeStyleBackground(button, ProjectColors.MAIN_COLOR, textColor1, imageBrightness1);
        for (JFXButton b : jfxButtons)
            if (b != button)
                changeStyleBackground(b, ProjectColors.TRANSPARENT, textColor2, imageBrightness2);
    }

    public static void changeStyleBackground(JFXButton button, String backgroundColor, String textColor, double imageBrightness) {
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: " + textColor + "; -fx-background-radius: 0");

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(imageBrightness);
        ObservableList<Node> observableList = button.getChildrenUnmodifiable();
        for (Node n : observableList)
        {
            if (n instanceof ImageView)
            {
                n.setEffect(colorAdjust);
            }
        }
    }

    public static void setTextFieldNumbers(JFXTextField textField) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}(\\d{0,4})?")) {
                textField.setText(oldValue);
            }
        });
    }
}
