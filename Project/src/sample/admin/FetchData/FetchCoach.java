package sample.admin.FetchData;
public class FetchCoach {
    private int Id ;
    private int Age;
    private String Name;
    private String Password;
    public FetchCoach(int id, String name, String password, int age){
        this.Id = id;
        this.Name = name;
        this.Password = password;
        this.Age = age;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
