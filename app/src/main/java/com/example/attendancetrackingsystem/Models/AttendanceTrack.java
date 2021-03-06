package com.example.attendancetrackingsystem.Models;

public class AttendanceTrack {
    String userId;
    int presentCount;
    int absentCount;
    int totalCount;

    public AttendanceTrack(String userId, int presentCount, int absentCount, int totalCount) {
        this.userId = userId;
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.totalCount = totalCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
