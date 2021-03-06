package com.bsu.nvasilyeva.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "gGroup")
@DynamicUpdate
public class GGroup {

    @Id
    @GenericGenerator(name = "sequence_user_id", strategy = "com.bsu.nvasilyeva.service.CustomGroupIdGenrator")
    @GeneratedValue(generator = "sequence_user_id")
    @Size(max = 20)
    private String id;

    @Size(min = 2, max = 50, message = "Name  length should between 2-50 character")
    private String name;

    @Size(min = 2, max = 100, message = "Image Path length should between 2-100 character")
    private String profileImagePath = "defaultProfile.png";

    @Size(min = 2, max = 100, message = "Status  length should between 2-100 character")
    private String status;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt = new Date();


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        GGroup gGroup = (GGroup) o;
        return Objects.equals(id, gGroup.id) &&
                Objects.equals(name, gGroup.name) &&
                Objects.equals(profileImagePath, gGroup.profileImagePath) &&
                Objects.equals(status, gGroup.status) &&
                Objects.equals(createdAt, gGroup.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, profileImagePath, status, createdAt);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GGroup{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", profileImagePath='").append(profileImagePath).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
