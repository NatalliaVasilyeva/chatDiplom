package com.bsu.nvasilyeva.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "verificationToken")
public class VerificationToken {

    public static final String FORGOTPASSWORD = "FORGOTPASSWORD";
    public static final String EMAILVERIFICATION = "EMAILVERIFICATION";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 10, max = 50, message = "Token  length should between 10-50 character")
    private String token;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    private String purpose;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date expiryDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return id == that.id &&
                Objects.equals(token, that.token) &&
                Objects.equals(user, that.user) &&
                Objects.equals(purpose, that.purpose) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, user, purpose, expiryDate);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VerificationToken{");
        sb.append("id=").append(id);
        sb.append(", token='").append(token).append('\'');
        sb.append(", user=").append(user);
        sb.append(", purpose='").append(purpose).append('\'');
        sb.append(", expiryDate=").append(expiryDate);
        sb.append('}');
        return sb.toString();
    }
}
