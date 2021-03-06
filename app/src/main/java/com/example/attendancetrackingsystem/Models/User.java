package com.example.attendancetrackingsystem.Models;

import android.icu.lang.UScript;

import java.util.List;

public class User {
    String userid;
    String userMail;
    String id;
    String UserPassword;
    String fname;
    String lname;
    String phnNo;
    List<String> subjects;

    boolean attendance;

    public User(String userid, String userMail, String id, String userPassword, String fname, String lname, String phnNo, List<String> subjects,boolean attendance) {
        this.userid = userid;
        this.userMail = userMail;
        this.id = id;
        UserPassword = userPassword;
        this.fname = fname;
        this.lname = lname;
        this.phnNo = phnNo;
        this.subjects = subjects;
        this.attendance=attendance;
    }
    public User(){}

    public User(String userid, boolean attendance) {
        this.userid = userid;
        this.attendance = attendance;
    }

    public User(String uId) {
        this.userid=uId;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public User(String userid, String phnNo) {
        this.userid = userid;
        this.phnNo = phnNo;
    }

    public User(String userid, List<String> subjects) {
        this.userid = userid;
        this.subjects = subjects;
    }
    public User(String userid, String userMail,  String userPassword, String fname, String lname, String phnNo, List<String> subjects) {
        this.userid = userid;
        this.userMail = userMail;
        UserPassword = userPassword;
        this.fname = fname;
        this.lname = lname;
        this.phnNo = phnNo;
        this.subjects = subjects;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhnNo() {
        return phnNo;
    }

    public void setPhnNo(String phnNo) {
        this.phnNo = phnNo;
    }

    public List<String > getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}