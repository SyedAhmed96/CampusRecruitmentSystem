package com.example.ahmed.campusrecruitmentsystem;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahmed on 3/15/2018.
 */

public class Job implements Parcelable {
    String jobTitle;
    String postedBy;
    String skills;
    String jID;
    String cID;


    public Job(){

    }


    public Job(String jobTitle, String postedBy, String skills, String jID, String cID) {
        this.jobTitle = jobTitle;
        this.postedBy = postedBy;
        this.skills = skills;
        this.jID = jID;
        this.cID = cID;
    }

    protected Job(Parcel in) {
        jobTitle = in.readString();
        postedBy = in.readString();
        skills = in.readString();
        jID = in.readString();
        cID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jobTitle);
        dest.writeString(postedBy);
        dest.writeString(skills);
        dest.writeString(jID);
        dest.writeString(cID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getjID() {
        return jID;
    }

    public void setjID(String jID) {
        this.jID = jID;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }
}
