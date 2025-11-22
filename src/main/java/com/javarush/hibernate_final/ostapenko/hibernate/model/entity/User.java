package com.javarush.hibernate_final.ostapenko.hibernate.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS", schema = "myapp")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "display_name",unique = true,length = 32)
    private String displayName;
    @Column(unique = true, length = 128)
    private String email;
    @Column(name = "first_name", length = 32)
    private String firstName;
    @Column(name = "last_name", length = 32)
    private String lastName;
    @Column(length = 128)
    private String password;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
