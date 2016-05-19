package com.omega.halldetails;

import java.util.Date;

/**
 * Created by Dell on 05-10-2015.
 */
public class BookedDatesPOJO {

    Date bookedDate;
    String fromTime, toTime;

    public BookedDatesPOJO(Date bookedDate, String fromTime, String toTime) {
        this.bookedDate = bookedDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public Date getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(Date bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
