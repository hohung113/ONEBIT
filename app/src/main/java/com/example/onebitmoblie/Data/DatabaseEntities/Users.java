package com.example.onebitmoblie.Data.DatabaseEntities;

import com.example.onebitmoblie.Data.BaseEntity;
import com.example.onebitmoblie.Data.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public class Users extends BaseEntity {
//    private String id;
    private String userName;
    private String fullName;
    private String passwordHash;
    private int age;
    private String email;
    private String currentJob;
    private Role role;
//    private boolean isDeleted;
//    private String createdAt;
//    private String modifiedAt;
//    private String modifiedBy;

//    public Users(String userName, String fullName, String passwordHash, int age, String email, String currentJob, Role role) {
//        super();
//        this.userName = userName;
//        this.fullName = fullName;
//        this.passwordHash = passwordHash;
//        this.age = age;
//        this.email = email;
//        this.currentJob = currentJob;
//        this.role = role;
//    }

    public Users(String id, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy, String userName, String fullName, String passwordHash, int age, String email, String currentJob, Role role) {
        super(id, isDeleted, createdAt, modifiedAt, modifiedBy);
        this.userName = userName;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
        this.age = age;
        this.email = email;
        this.currentJob = currentJob;
        this.role = role;
    }

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

    //    public Users(String id, String userName, String fullName, String passwordHash, int age, String email, String currentJob, Role role, boolean isDeleted, String createdAt, String modifiedAt, String modifiedBy) {
//        this.id = id;
//        this.userName = userName;
//        this.fullName = fullName;
//        this.passwordHash = passwordHash;
//        this.age = age;
//        this.email = email;
//        this.currentJob = currentJob;
//        this.role = role;
//        this.isDeleted = isDeleted;
//        this.createdAt = createdAt;
//        this.modifiedAt = modifiedAt;
//        this.modifiedBy = modifiedBy;
//    }
//
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getModifiedAt() {
//        return modifiedAt;
//    }
//
//    public void setModifiedAt(String modifiedAt) {
//        this.modifiedAt = modifiedAt;
//    }
//
//    public String getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public void setModifiedBy(String modifiedBy) {
//        this.modifiedBy = modifiedBy;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getPasswordHash() {
//        return passwordHash;
//    }
//
//    public void setPasswordHash(String passwordHash) {
//        this.passwordHash = passwordHash;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCurrentJob() {
//        return currentJob;
//    }
//
//    public void setCurrentJob(String currentJob) {
//        this.currentJob = currentJob;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public boolean isDeleted() {
//        return isDeleted;
//    }
//
//    public void setDeleted(boolean deleted) {
//        isDeleted = deleted;
//    }


}