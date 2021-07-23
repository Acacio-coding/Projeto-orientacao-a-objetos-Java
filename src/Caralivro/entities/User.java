package Caralivro.entities;

import java.util.ArrayList;

public abstract class User {
    private final int id;
    private String email;
    private String password;
    private final ArrayList<User> friendList;

    public User(int id, String email, String password) {
        super();
        this.id = id;
        this.email = email;
        this.password = password;
        this.friendList = new ArrayList<>();
    }

    public User(int id, String password) {
        super();
        this.id = id;
        this.email = null;
        this.password = password;
        this.friendList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<User> getFriendList() {
        return friendList;
    }

    public abstract String getType();
}
