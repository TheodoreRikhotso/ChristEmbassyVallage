package com.example.admin.christembassyvallage.model;

/**
 * Created by Admin on 11-Dec-17.
 */

public class Announcement {
    String title,Desrciption,time,startDate,endDate,place,postedDate,postedTime;
    int color;


    public Announcement() {
    }
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesrciption() {
        return Desrciption;
    }

    public void setDesrciption(String desrciption) {
        Desrciption = desrciption;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }
}
