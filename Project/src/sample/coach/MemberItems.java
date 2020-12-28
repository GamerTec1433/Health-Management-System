package sample.coach;

public class MemberItems {
    private int id;
    private String name;
    private String report;

    public MemberItems()
    {
        this.id = 0;
        this.name = "";
        this.report = "";
    }

    public MemberItems(int id, String name, String report) {
        this.id = id;
        this.name = name;
        this.report = report;
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

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
