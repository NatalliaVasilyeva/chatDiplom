package com.bsu.nvasilyeva.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "message")
public class Message {
    public final static String Text = "TEXT";
    public static final String Media = "Media";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    private String path;

    private String type;

    @OneToMany(mappedBy = "fromId")
    private List<PersonalMessageTransaction> fromMessages = new ArrayList<>();

    @OneToMany(mappedBy = "toId")
    private List<PersonalMessageTransaction> toMessages = new ArrayList<>();

    @OneToMany(mappedBy = "groupId")
    private List<GroupMessageTransaction> groupMessageTransactions = new ArrayList<>();

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PersonalMessageTransaction> getFromMessages() {
        return fromMessages;
    }

    public void setFromMessages(List<PersonalMessageTransaction> fromMessages) {
        this.fromMessages = fromMessages;
    }

    public List<PersonalMessageTransaction> getToMessages() {
        return toMessages;
    }

    public void setToMessages(List<PersonalMessageTransaction> toMessages) {
        this.toMessages = toMessages;
    }

    public List<GroupMessageTransaction> getGroupMessageTransactions() {
        return groupMessageTransactions;
    }

    public void setGroupMessageTransactions(List<GroupMessageTransaction> groupMessageTransactions) {
        this.groupMessageTransactions = groupMessageTransactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return id == message1.id &&
                Objects.equals(message, message1.message) &&
                Objects.equals(path, message1.path) &&
                Objects.equals(type, message1.type) &&
                Objects.equals(fromMessages, message1.fromMessages) &&
                Objects.equals(toMessages, message1.toMessages) &&
                Objects.equals(groupMessageTransactions, message1.groupMessageTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, path, type, fromMessages, toMessages, groupMessageTransactions);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("id=").append(id);
        sb.append(", message='").append(message).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", fromMessages=").append(fromMessages);
        sb.append(", toMessages=").append(toMessages);
        sb.append(", groupMessageTransactions=").append(groupMessageTransactions);
        sb.append('}');
        return sb.toString();
    }
}
