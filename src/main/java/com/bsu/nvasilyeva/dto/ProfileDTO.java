package com.bsu.nvasilyeva.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.GregorianCalendar;

public class ProfileDTO {

    @Size(min = 2, max = 50, message = "Name length should between 2-50 character")
    private String name;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(columnDefinition = "datetime default current_timestamp")
    private Date birthdate = new GregorianCalendar(0, 0, 0).getTime();

    @Size(min = 2, max = 10, message = "Gender length should between 2-10 character")
    private String gender;

    @Size(min = 2, max = 100, message = "Status length should between 2-100 character")
    private String status;

    private int district;

    private int street;

    private int village;

    private MultipartFile file;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public int getStreet() {
        return street;
    }

    public void setStreet(int street) {
        this.street = street;
    }

    public int getVillage() {
        return village;
    }

    public void setVillage(int village) {
        this.village = village;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ProfileDTO [name=" + name + ", birthdate=" + birthdate + ", gender=" + gender + ", status=" + status
                + ", district=" + district + ", street=" + street + ", village=" + village + ", email=" + email + "]";
    }

}
