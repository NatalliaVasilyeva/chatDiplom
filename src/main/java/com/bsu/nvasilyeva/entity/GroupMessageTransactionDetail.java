package com.bsu.nvasilyeva.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "groupmessagetransactiondetail")
public class GroupMessageTransactionDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "groupMessageTransactionId")
	private GroupMessageTransaction groupMessageTransaction;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User userId;

	private boolean isReaded;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date createdAt=new Date();

	public GroupMessageTransaction getGroupMessageTransaction() {
		return groupMessageTransaction;
	}

	public void setGroupMessageTransaction(GroupMessageTransaction groupMessageTransaction) {
		this.groupMessageTransaction = groupMessageTransaction;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public boolean isReaded() {
		return isReaded;
	}

	public void setReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		GroupMessageTransactionDetail that = (GroupMessageTransactionDetail) o;
		return id == that.id &&
				isReaded == that.isReaded &&
				Objects.equals(groupMessageTransaction, that.groupMessageTransaction) &&
				Objects.equals(userId, that.userId) &&
				Objects.equals(createdAt, that.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, groupMessageTransaction, userId, isReaded, createdAt);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("GroupMessageTransactionDetail{");
		sb.append("id=").append(id);
		sb.append(", groupMessageTransaction=").append(groupMessageTransaction);
		sb.append(", userId=").append(userId);
		sb.append(", isReaded=").append(isReaded);
		sb.append(", createdAt=").append(createdAt);
		sb.append('}');
		return sb.toString();
	}
}
