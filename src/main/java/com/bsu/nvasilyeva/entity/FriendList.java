package com.bsu.nvasilyeva.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "friendList")
public class FriendList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fromId")
    private User fromId;

    @ManyToOne
    @JoinColumn(name = "toId")
    private User toId;

    private boolean enable;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
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
        FriendList that = (FriendList) o;
        return id == that.id &&
                enable == that.enable &&
                Objects.equals(fromId, that.fromId) &&
                Objects.equals(toId, that.toId) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromId, toId, enable, createdAt);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FriendList{");
        sb.append("id=").append(id);
        sb.append(", fromId=").append(fromId);
        sb.append(", toId=").append(toId);
        sb.append(", enable=").append(enable);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
