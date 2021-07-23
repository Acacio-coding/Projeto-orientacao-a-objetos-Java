package Caralivro.entities;

import java.time.LocalDate;

public class Comment {
    private final User user;
    private final LocalDate commentDate;
    private String content;

    public Comment(User user, LocalDate commentDate, String content) {
        this.user = user;
        this.commentDate = commentDate;
        this.content = content;
    }

    public Comment(User user) {
        this.user = user;
        this.commentDate = null;
        this.content = null;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
