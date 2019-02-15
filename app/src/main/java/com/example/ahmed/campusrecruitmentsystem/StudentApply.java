package com.example.ahmed.campusrecruitmentsystem;

/**
 * Created by Ahmed on 3/19/2018.
 */

public class StudentApply {
    String sID;
    String email;
    String status;

    public StudentApply() {
    }

    public StudentApply(String sID, String email, String status) {
        this.sID = sID;
        this.email = email;
        this.status = status;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
