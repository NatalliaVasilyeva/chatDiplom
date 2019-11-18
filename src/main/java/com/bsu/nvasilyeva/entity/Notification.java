package com.bsu.nvasilyeva.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "notification")
public class Notification {
    public static final String SENDREQUEST = "SENDREQUEST";
    public static final String ACCEPTREQUEST = "ACCEPTREQUEST";
    public static final String REJECTREQUEST = "REJECTREQUEST";
    public static final String UNFRIEND = "UNFRIEND";
    public static final String MEMBEROFGROUP = "MEMBEROFGROUP";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 5, max = 100, message = "message  length should between 5-100 character")
    private String message;

    @Size(max = 20, message = "purpose size less than 20 character")
    private String purpose;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a", timezone = "Europe/Minsk")
    private Date createdAt = new Date();

    private boolean isReaded = false;

    @ManyToOne
    @JsonIgnore
    private User user;


    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean isReaded) {
        this.isReaded = isReaded;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return id == that.id &&
                isReaded == that.isReaded &&
                Objects.equals(message, that.message) &&
                Objects.equals(purpose, that.purpose) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, purpose, createdAt, isReaded, user);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Notification{");
        sb.append("id=").append(id);
        sb.append(", message='").append(message).append('\'');
        sb.append(", purpose='").append(purpose).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", isReaded=").append(isReaded);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
