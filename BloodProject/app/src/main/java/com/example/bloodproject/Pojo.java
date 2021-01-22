package com.example.bloodproject;

public class Pojo {
    String fname,bloodgroup;

    public Pojo() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public Pojo(String fname, String bloodgroup) {
        this.fname = fname;
        this.bloodgroup = bloodgroup;
    }
}