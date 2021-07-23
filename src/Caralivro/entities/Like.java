package Caralivro.entities;

import java.time.LocalDate;

public class Like {
    private final User user;
    private final LocalDate likeDate;

    public Like(User user, LocalDate likeDate) {
        this.user = user;
        this.likeDate = likeDate;
    }

    public Like(User user) {
        this.user = user;
        this.likeDate = null;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getLikeDate() {
        return likeDate;
    }
}
