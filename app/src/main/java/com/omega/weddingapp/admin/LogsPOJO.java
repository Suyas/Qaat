package com.omega.weddingapp.admin;

import java.util.Date;

public class LogsPOJO {

    String name, email, phone, hallName, objectId, enquiryToken, endTime, startTime;
    Date from, to;

    public LogsPOJO(String name, String email, String phone, Date from,
                    Date to, String hallName, String objectId, String endTime, String startTime, String enquiryToken) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.from = from;
        this.to = to;
        this.hallName = hallName;
        this.objectId = objectId;
        this.enquiryToken = enquiryToken;
        this.endTime = endTime;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getEnquiryToken() {
        return enquiryToken;
    }

    public void setEnquiryToken(String enquiryToken) {
        this.enquiryToken = enquiryToken;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
