package pl.edziennik.infrastructure.authentication.security;

import java.util.Date;

public class LoggedUser {

    private Date expirationDate;
    private String userId;

    public LoggedUser(String userId, Date expirationDate) {
        this.expirationDate = expirationDate;
        this.userId = userId;
    }

    public LoggedUser() {
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getUserId() {
        return userId;
    }
}
