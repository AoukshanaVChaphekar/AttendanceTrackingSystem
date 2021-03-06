package com.example.attendancetrackingsystem.Models;

public class Admin {
    String username;
    String userMail;
    String id;
    String AdminPassword;

    public Admin(String username, String userMail, String id, String adminPassword) {
        this.username = username;
        this.userMail = userMail;
        this.id = id;
        AdminPassword = adminPassword;
    }
    public Admin(){}

    public Admin(String username, String userMail,  String adminPassword) {
        this.username = username;
        this.userMail = userMail;
        AdminPassword = adminPassword;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminPassword() {
        return AdminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        AdminPassword = adminPassword;
    }
}
