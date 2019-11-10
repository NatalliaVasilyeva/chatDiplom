package com.bsu.nvasilyeva.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="groupmember")
public class GroupMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "groupId")
	private GGroup groupId;

	@ManyToOne
	@JoinColumn(name = "toId")
	private User toId;

	private boolean enable;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date createdAt = new Date();

	private boolean isLeader=false;

	public boolean getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
	}

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
		GroupMember that = (GroupMember) o;
		return id == that.id &&
				enable == that.enable &&
				isLeader == that.isLeader &&
				Objects.equals(groupId, that.groupId) &&
				Objects.equals(toId, that.toId) &&
				Objects.equals(createdAt, that.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, groupId, toId, enable, createdAt, isLeader);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("GroupMember{");
		sb.append("id=").append(id);
		sb.append(", groupId=").append(groupId);
		sb.append(", toId=").append(toId);
		sb.append(", enable=").append(enable);
		sb.append(", createdAt=").append(createdAt);
		sb.append(", isLeader=").append(isLeader);
		sb.append('}');
		return sb.toString();
	}
}
