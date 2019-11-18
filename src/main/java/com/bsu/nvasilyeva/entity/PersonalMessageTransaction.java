package com.bsu.nvasilyeva.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "personalMessageTransaction")
public class PersonalMessageTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fromId")
    private User fromId;

    @ManyToOne
    @JoinColumn(name = "toId")
    private User toId;

    @ManyToOne
    private Message message;

    private boolean isReaded = false;

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean isReaded) {
        this.isReaded = isReaded;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt = new Date();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFromId() {
        return fromId;
    }

    public void setFromId(User fromId) {
        this.fromId = fromId;
    }

    public User getToId() {
        return toId;
    }

    public void setToId(User toId) {
        this.toId = toId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
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
        PersonalMessageTransaction that = (PersonalMessageTransaction) o;
        return id == that.id &&
                isReaded == that.isReaded &&
                Objects.equals(fromId, that.fromId) &&
                Objects.equals(toId, that.toId) &&
                Objects.equals(message, that.message) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromId, toId, message, isReaded, createdAt);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PersonalMessageTransaction{");
        sb.append("id=").append(id);
        sb.append(", fromId=").append(fromId);
        sb.append(", toId=").append(toId);
        sb.append(", message=").append(message);
        sb.append(", isReaded=").append(isReaded);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
