package sample.admin.AddAdminWindow;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddAdminWindow implements Initializable {
    String jsonPathFile = "jsonFile.json";

    public TextField addNameTextF;
    public TextField addAgeTextF;
    public TextField addWightTextF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAgeTextF.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}(\\d{0,4})?")) {
                addAgeTextF.setText(oldValue);
            }
        });
    }

    public void addMemberBtn(Event event)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/mydatabase", "root", "blood1433");
            System.out.println("Connection Succ!");

            String sql = "INSERT INTO members (name, age, weight) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, addNameTextF.getText());
            preparedStatement.setInt(2, Integer.parseInt(addAgeTextF.getText()));
            preparedStatement.setInt(3, Integer.parseInt(addWightTextF.getText()));

            preparedStatement.execute();
            preparedStatement.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }

    }

    /*public void addMemberBtn(Event event)
    {

        //System.out.println(newMemberJSON);

        JSONObject currentObj = readData();
        JSONArray jsArray;

        if (currentObj != null)
        {
            jsArray = (JSONArray) currentObj.get("members");
        }
        else
        {
            currentObj = new JSONObject();
            currentObj.put("id", 0);
            jsArray = new JSONArray();
        }

        JSONObject newMember = new JSONObject();
        newMember.put("id", Integer.parseInt(currentObj.get("id").toString()) + 1);
        newMember.put("name", addNameTextF.getText());
        newMember.put("age", addAgeTextF.getText());
        newMember.put("wight", addWightTextF.getText());

        currentObj.put("id", Integer.parseInt(currentObj.get("id").toString()) + 1);
        jsArray.add(newMember);
        currentObj.put("members", jsArray);
        saveData(currentObj.toString());
    }

    public JSONObject readData()
    {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(jsonPathFile)) {
            Object obj = jsonParser.parse(reader);

            return (JSONObject) obj;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveData(String dataInString)
    {
        try {
            FileWriter fileWriter = new FileWriter(jsonPathFile);
            fileWriter.write(dataInString);
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Data Inserted!");

        } catch (IOException e) {
            System.out.println("Data Failed!");
            e.printStackTrace();
        }
    }*/
}
