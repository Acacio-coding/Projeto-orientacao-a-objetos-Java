import java.time.LocalDate;

public class Like {
    private final User user;
    private final LocalDate likeDate;

    public Like(User user, LocalDate likeDate) {
        this.user = user;
        this.likeDate = likeDate;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getLikeDate() {
        return likeDate;
    }
}
