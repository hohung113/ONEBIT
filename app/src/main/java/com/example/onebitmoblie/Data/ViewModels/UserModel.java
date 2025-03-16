package com.example.onebitmoblie.Data.ViewModels;

public class UserModel {
    public String id;
    public String userName;
    public String fullName;
    public String email;
    public String passwordHash;
    public String role;
    public boolean isDeleted;

    public UserModel() {
        // Firebase requires an empty constructor
    }

    public String getId() { return id; }
    public String getUserName() { return userName; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
    public boolean isDeleted() { return isDeleted; }
}