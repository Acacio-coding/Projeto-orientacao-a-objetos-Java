package Caralivro.entities;

import java.time.LocalDate;

public class Share {
    private final User user;
    private final LocalDate shareDate;

    public Share(User user, LocalDate shareDate) {
        this.user = user;
        this.shareDate = shareDate;
    }

    public Share(User user) {
        this.user = user;
        this.shareDate = null;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getShareDate() {
        return shareDate;
    }
}
