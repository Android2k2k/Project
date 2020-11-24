package com.example.bloodproject;

public class Pojo {
    String ilink,dname,pcode,bgroup;

    public Pojo() {
    }


    public String getIlink() {
        return ilink;
    }

    public void setIlink(String ilink) {
        this.ilink = ilink;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getBgroup() {
        return bgroup;
    }

    public void setBgroup(String bgroup) {
        this.bgroup = bgroup;
    }

    public Pojo(String ilink, String dname, String pcode, String bgroup) {
        this.ilink = ilink;
        this.dname = dname;
        this.pcode = pcode;
        this.bgroup = bgroup;
    }
}
