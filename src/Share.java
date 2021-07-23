import java.time.LocalDate;

public class Share {
    private final User user;
    private final LocalDate shareDate;

    public Share(User user, LocalDate shareDate) {
        this.user = user;
        this.shareDate = shareDate;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getShareDate() {
        return shareDate;
    }
}
