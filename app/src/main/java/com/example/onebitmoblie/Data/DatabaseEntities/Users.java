package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;
import com.example.onebitmoblie.Data.Role;

public class Users extends BaseEntity {
    private String userName;
    private String fullName;
    private String passwordHash;
    private int age;
    private String email;
    private String currentJob;
    private Role role;

    public Users(String value, String s, String string, int i, String value1, String s1, Role role) {
    }

    // Getter và Setter
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}