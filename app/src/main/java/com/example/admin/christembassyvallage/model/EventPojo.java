package com.example.admin.christembassyvallage.model;

/**
 * Created by Admin on 06-Nov-17.
 */

public class EventPojo   {

        private String id, title,description,startDate, enddate, location,time,guest;
    private int duration;
    private String day;
    private int imageResource;
    private String note;

        public EventPojo() {
        }


    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getGuest() {
            return guest;
        }

        public void setGuest(String guest) {
            this.guest = guest;
        }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}


