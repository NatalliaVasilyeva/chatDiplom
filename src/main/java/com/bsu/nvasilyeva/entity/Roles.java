package com.bsu.nvasilyeva.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name="roles",uniqueConstraints = @UniqueConstraint(columnNames = { "role", "user_id" }))
public class Roles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 2, max = 10, message = "Role  length should between2-10 character")
	private String role;

	@ManyToOne
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
		Roles roles = (Roles) o;
		return id == roles.id &&
				Objects.equals(role, roles.role) &&
				Objects.equals(user, roles.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, role, user);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Roles{");
		sb.append("id=").append(id);
		sb.append(", role='").append(role).append('\'');
		sb.append(", user=").append(user);
		sb.append('}');
		return sb.toString();
	}
}
