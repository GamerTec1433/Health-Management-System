package sample.coach;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TimelineItems {
    private int id;
    private String exercise;
    private String date;
    private String time;

    public TimelineItems()
    {
        this.id = 0;
        this.exercise = "";
        this.date = LocalDate.now().toString();
        this.time = "00:00";
    }

    public TimelineItems(int id, String exercise, String date, String time) {
        this.id = id;
        this.exercise = exercise;
        this.date = date;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
