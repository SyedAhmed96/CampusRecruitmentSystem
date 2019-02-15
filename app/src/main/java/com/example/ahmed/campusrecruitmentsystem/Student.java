package com.example.ahmed.campusrecruitmentsystem;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahmed on 3/10/2018.
 */

public class Student implements Parcelable {
    String email;
    String name;
    String password;
    String CGPA;
    String photoURL;
    String sID;


    public Student() {
    }

    //Constructor Usually Called on SignUp
    public Student(String email, String name, String password,String sID) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.sID = sID;
    }

    public Student(String email, String name, String password,String CGPA,String sID) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.CGPA = CGPA;
        this.sID = sID;
    }

    public Student(String email, String name, String password,String CGPA,String photoURL,String sID) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.CGPA = CGPA;
        this.photoURL = photoURL;
        this.sID = sID;
    }

    protected Student(Parcel in) {
        email = in.readString();
        name = in.readString();
        password = in.readString();
        CGPA = in.readString();
        photoURL = in.readString();
        sID = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getGPA() {
        return CGPA;
    }

    public void setGPA(String CGPA) {
        this.CGPA = CGPA;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeString(CGPA);
        parcel.writeString(photoURL);
        parcel.writeString(sID);
    }
}
