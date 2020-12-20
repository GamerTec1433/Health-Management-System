package sample;

public class User {
    public static int id;
    public static String username;
    public static String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        User.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        User.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        User.password = password;
    }
}
