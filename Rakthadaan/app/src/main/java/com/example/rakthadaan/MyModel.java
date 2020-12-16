package com.example.rakthadaan;

public class MyModel {
    String fname,lname,mail,mobile,age,date,gender,bloodgroup,image,address;
    int rating;

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

    public int getRating() {
        return rating;
    }

    public MyModel() {
    }

    public MyModel(String fname, String lname, String mail, String mobile, String age, String date, String gender, String bloodgroup, String image, String address, int rating) {
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
        this.rating = rating;
    }

}
