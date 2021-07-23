package Caralivro.entities;

import java.time.LocalDate;
import java.util.ArrayList;

public class Post {
    private final User author;
    private String title;
    private final LocalDate creationDate;
    private String content;
    private final ArrayList<Like> likes;
    private final ArrayList<Comment> comments;
    private final ArrayList<Share> shares;

    public Post(User author, String title, LocalDate creationDate, String content) {
        this.author = author;
        this.title = title;
        this.creationDate = creationDate;
        this.content = content;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.shares = new ArrayList<>();
    }

    public Post(User author, LocalDate creationDate) {
        this.author = author;
        this.title = null;
        this.creationDate = creationDate;
        this.content = null;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.shares = new ArrayList<>();
    }

    public User getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Share> getShares() {
        return shares;
    }
}
