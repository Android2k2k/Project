package com.example.rakthadaan;

public class MyModel {
    String fname;
    String lname;
    String mail;
    String mobile;
    String age;
    String date;
    String gender;
    String bloodgroup;
    String image;
    String address;
    String pin;
    String comments;
    String rating;
    String id;

    public String getRating() {
        return rating;
    }

    public String getId() {
        return id;
    }

    public MyModel(String fname, String lname, String mail, String mobile, String age, String date, String gender, String bloodgroup, String image, String address, String pin, String comments, String id, String rating) {
        this.fname = fname;
        this.lname = lname;
        this.mail = mail;
        this.mobile = mobile;
        this.age = age;
        this.date = date;
        this.gender = gender;
        this.bloodgroup = bloodgroup;
        this.image = image;
        this.address = address;
        this.pin = pin;
        this.comments = comments;
        this.id = id;
        this.rating = rating;
    }


    public String getComments() {
        return comments;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getMail() {
        return mail;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAge() {
        return age;
    }

    public String getDate() {
        return date;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getPin() {
        return pin;
    }

    public MyModel() {
    }

}
