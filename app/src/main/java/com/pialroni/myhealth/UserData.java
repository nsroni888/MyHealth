package com.pialroni.myhealth;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "User_Data")
public class UserData implements Serializable {
    public UserData() {
    }

    @PrimaryKey
    private long di;
    private String date;
    private String timestamp;
    private int Systolic;
    private int Diastolic;
    private int heartRate;
    private String Comment;

    public UserData(String date, String timestamp, int systolic, int diastolic, int heartRate, String comment) {
        this.date = date;
        this.timestamp = timestamp;
        Systolic = systolic;
        Diastolic = diastolic;
        this.heartRate = heartRate;
        Comment = comment;
    }


    public long getDi() {
        return di;
    }

    public void setDi(long di) {
        this.di = di;
    }

    public String getDate() {
        return date;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setSystolic(int systolic) {
        Systolic = systolic;
    }

    public void setDiastolic(int diastolic) {
        Diastolic = diastolic;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getSystolic() {
        return Systolic;
    }


    public int getDiastolic() {
        return Diastolic;
    }


    public int getHeartRate() {
        return heartRate;
    }


    public String getComment() {
        return Comment;
    }

}
