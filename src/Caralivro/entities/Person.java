package Caralivro.entities;

import java.time.LocalDate;
import java.util.ArrayList;

public class Person extends User {
    private String name;
    private String gender;
    private LocalDate birth;

    public Person(int id, String email, String password, String name, String gender, LocalDate birth) {
        super(id, email, password);
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }

    public Person(int id, String email, String password, String name) {
        super(id, email, password);
        this.name = name;
        this.gender = null;
        this.birth = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public ArrayList<User> getFriendList() {
        return super.getFriendList();
    }

    @Override
    public String getType() {
        return "Person";
    }
}
