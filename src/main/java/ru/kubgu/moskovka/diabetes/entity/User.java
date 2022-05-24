package ru.kubgu.moskovka.diabetes.entity;

import jakarta.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static jakarta.persistence.TemporalType.DATE;

public class User {
    private String name;
    private String surname;
    private String login;
    private String gender;
    @Temporal(DATE)
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE,pattern="yyyy-MM-dd")
    private Date birthDate;
    private String password;

    public User() {
    }

    public User(String name, String surname, String login, String gender, Date birthDate, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.gender = gender;
        this.birthDate = birthDate;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
