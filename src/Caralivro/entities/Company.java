package Caralivro.entities;

import java.time.LocalDate;
import java.util.ArrayList;

public class Company extends User {
    private String name;
    private LocalDate creationDate;
    private String classification;

    public Company(int id, String email, String password, String name, LocalDate creationDate, String classification) {
        super(id, email, password);
        this.name = name;
        this.creationDate = creationDate;
        this.classification = classification;
    }

    public Company(int id, String email, String password, String name) {
        super(id, email, password);
        this.name = name;
        this.creationDate = null;
        this.classification = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
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
        return "Company";
    }
}
