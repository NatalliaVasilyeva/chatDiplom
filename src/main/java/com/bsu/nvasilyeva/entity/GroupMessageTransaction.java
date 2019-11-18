package com.bsu.nvasilyeva.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groupMessageTransaction")
public class GroupMessageTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private GGroup groupId;

    @ManyToOne
    @JoinColumn(name = "fromId")
    private User fromId;

    @ManyToOne
    private Message message;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt = new Date();

    @JsonIgnore
    @OneToMany(mappedBy = "fromId")
    private List<GroupMessageTransaction> groupMessageTransactions = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GGroup getGroupId() {
        return groupId;
    }

    public void setGroupId(GGroup groupId) {
        this.groupId = groupId;
    }

    public List<GroupMessageTransaction> getGroupMessageTransactions() {
        return groupMessageTransactions;
    }

    public void setGroupMessageTransactions(List<GroupMessageTransaction> groupMessageTransactions) {
        this.groupMessageTransactions = groupMessageTransactions;
    }

    public User getFromId() {
        return fromId;
    }

    public void setFromId(User fromId) {
        this.fromId = fromId;
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
        GroupMessageTransaction that = (GroupMessageTransaction) o;
        return id == that.id &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(fromId, that.fromId) &&
                Objects.equals(message, that.message) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(groupMessageTransactions, that.groupMessageTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, fromId, message, createdAt, groupMessageTransactions);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GroupMessageTransaction{");
        sb.append("id=").append(id);
        sb.append(", groupId=").append(groupId);
        sb.append(", fromId=").append(fromId);
        sb.append(", message=").append(message);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", groupMessageTransactions=").append(groupMessageTransactions);
        sb.append('}');
        return sb.toString();
    }
}
