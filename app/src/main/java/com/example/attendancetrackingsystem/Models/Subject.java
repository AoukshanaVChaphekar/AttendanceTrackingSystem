package com.example.attendancetrackingsystem.Models;

import java.util.ArrayList;

public class Subject {

    String name;
    String id;
    String code;
    ArrayList<String> fid;

    public Subject(String name, String id, String code,ArrayList<String> fid) {
        this.name = name;
        this.id = id;
        this.code = code;
        this.fid=fid;
    }

    public ArrayList<String> getFid() {
        return fid;
    }

    public Subject(String code, ArrayList<String> fid) {
        this.code = code;
        this.fid = fid;
    }

    public Subject(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void setFid(ArrayList<String> fid) {
        this.fid = fid;
    }


    public Subject(String name, String code, ArrayList<String> fid) {
        this.name = name;
        this.code = code;
        this.fid=fid;
    }

    public Subject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
