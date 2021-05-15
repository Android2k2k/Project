package com.example.rakthadaan.models;

public class Pojo {
    String fname, pin, image, bloodgroup,mobile;
    String id;

    public Pojo(String fname, String pin, String image, String bloodgroup, String mobile, String id) {
        this.fname = fname;
        this.pin = pin;
        this.image = image;
        this.bloodgroup = bloodgroup;
        this.mobile = mobile;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getFname() {
        return fname;
    }

    public String getPin() {
        return pin;
    }

    public String getImage() {
        return image;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public Pojo() {
    }

}

