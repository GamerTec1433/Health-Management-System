package sample.coach;

import java.time.LocalDate;

public class MemberItems {
    private int id;
    private String name;
    private int exerciseId;

    public MemberItems()
    {
        this.id = 0;
        this.name = "";
        this.exerciseId = 0;
    }

    public MemberItems(int id, String name, int exerciseId) {
        this.id = id;
        this.name = name;
        this.exerciseId = exerciseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
}
