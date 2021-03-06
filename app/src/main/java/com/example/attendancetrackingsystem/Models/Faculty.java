package com.example.attendancetrackingsystem.Models;

public class Faculty {
    String id;
    String fname;
    String lname;
    String password;
    String email;
    String mobNo;
    String fid;

    public Faculty(String id, String fname, String lname, String password, String email, String mobNo,String fid) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.email = email;
        this.mobNo = mobNo;
        this.fid=fid;
    }

    public Faculty(String fid,String mobNo) {

        this.mobNo = mobNo;
        this.fid=fid;
    }
    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Faculty(String fid, String fname, String lname, String password, String email, String mobNo) {
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.email = email;
        this.mobNo = mobNo;
        this.fid=fid;
    }

    public Faculty() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }
}
