package com.example.rakthadaan;

public class MyModel2 {
    String bbname,bbpname,bbmobile,bbaddress,bbpin,bbpwd;

    public MyModel2(String bbname, String bbpname, String bbmobile, String bbaddress, String bbpin, String bbpwd) {
        this.bbname = bbname;
        this.bbpname = bbpname;
        this.bbmobile = bbmobile;
        this.bbaddress = bbaddress;
        this.bbpin = bbpin;
        this.bbpwd = bbpwd;
    }

    public MyModel2() {
    }

    public String getBbname() {
        return bbname;
    }

    public String getBbpname() {
        return bbpname;
    }

    public String getBbmobile() {
        return bbmobile;
    }

    public String getBbaddress() {
        return bbaddress;
    }

    public String getBbpin() {
        return bbpin;
    }

    public String getBbpwd() {
        return bbpwd;
    }
}
