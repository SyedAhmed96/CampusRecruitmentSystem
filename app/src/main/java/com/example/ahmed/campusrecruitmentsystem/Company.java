package com.example.ahmed.campusrecruitmentsystem;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahmed on 3/10/2018.
 */

public class Company implements Parcelable {
    String name;
    String password;
    String email;
    String address;
    String cID;


    public Company() {
    }

    public Company(String name, String password, String email, String address, String cID) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.cID = cID;
    }


    protected Company(Parcel in) {
        name = in.readString();
        password = in.readString();
        email = in.readString();
        address = in.readString();
        cID = in.readString();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getcID() {return cID;}

    public void setcID(String cID) {this.cID = cID;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeString(email);
        parcel.writeString(address);
        parcel.writeString(cID);
    }
}
