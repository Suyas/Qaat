package com.omega.weddingapp.admin;

import java.util.Date;

/**
 * Created by Dell on 07-10-2015.
 */
public class ConfirmPOJO {

    String userName, phone, hallName, from, to, objectId;
    Date date;

    public ConfirmPOJO(String userName, String phone, String hallName, String from, String to, String objectId, Date date) {
        this.userName = userName;
        this.hallName = hallName;
        this.from = from;
        this.to = to;
        this.objectId = objectId;
        this.date = date;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
