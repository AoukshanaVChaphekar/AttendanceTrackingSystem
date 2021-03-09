package com.example.attendancetrackingsystem.Models;

public class UserAttendanceTrack {


    String date;
    String code;
    String faculty;
    boolean status;

    public UserAttendanceTrack(String date, String code, String faculty, boolean status) {
        this.date = date;
        this.code = code;
        this.faculty = faculty;
        this.status = status;
    }
    public UserAttendanceTrack(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
