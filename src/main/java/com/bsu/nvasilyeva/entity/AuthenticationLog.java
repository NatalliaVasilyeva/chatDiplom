package com.bsu.nvasilyeva.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "authenticationlog")
public class AuthenticationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date loginTime = new Date();;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date logoutTime = new Date();;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date createdAt = new Date();;

	@ManyToOne
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AuthenticationLog that = (AuthenticationLog) o;
		return id == that.id &&
				Objects.equals(loginTime, that.loginTime) &&
				Objects.equals(logoutTime, that.logoutTime) &&
				Objects.equals(createdAt, that.createdAt) &&
				Objects.equals(user, that.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, loginTime, logoutTime, createdAt, user);
	}

	@Override
	public String toString() {
		return "AuthenticationLog [id=" + id + ", loginTime=" + loginTime + ", logoutTime=" + logoutTime
				+ ", createdAt =" + createdAt + "]";
	}

}
