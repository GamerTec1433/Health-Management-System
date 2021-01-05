package sample.admin.FetchData;
import java.util.Date;
public class FetchMember {
    private int Id ;
    private String Name;
    private String Password;
    private int Age;
    private String Report;
    private Date SubDate;
    private int CoachId;
    public FetchMember(int id, String name, String password, int age, String report, Date subDate, int coachId){
        this.Id = id;
        this.Name = name;
        this.Password = password;
        this.Age = age;
        this.Report = report;
        this.SubDate = subDate;
        this.CoachId = coachId;
    }
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public Date getSubDate() {
        return SubDate;
    }

    public void setSubDate(Date subDate) {
        SubDate = subDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getReport() {
        return Report;
    }

    public void setReport(String report) {
        Report = report;
    }

    public int getCoachId() {
        return CoachId;
    }

    public void setCoachId(int coachId) {
        CoachId = coachId;
    }
}