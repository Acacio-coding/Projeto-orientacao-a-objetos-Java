import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private final int id;
    private String email;
    private String password;
    private String name;
    private String gender;
    private LocalDate birth;
    private final ArrayList<Friend> friendList;

    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = null;
        this.gender = null;
        this.birth = null;
        this.friendList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getGender () {
        return gender;
    }

    public void setGender (String gender) {
        this.gender = gender;
    }

    public LocalDate getBirth () {
        return birth;
    }

    public void setBirth (LocalDate birth) {
        this.birth = birth;
    }

    public ArrayList<Friend> getFriendList () {
        return friendList;
    }
}