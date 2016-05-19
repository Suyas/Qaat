package com.omega.halldetails;

import com.parse.ParseGeoPoint;

public class HallDetailsPOJO {
    String hallName;
    Number bookingAmount;
    Number capicity;

    /**
     * @return the bookingAmount
     */
    public Number getBookingAmount() {
        return bookingAmount;
    }

    /**
     * @param bookingAmount the bookingAmount to set
     */
    public void setBookingAmount(Number bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    /**
     * @return the loation
     */
    public String getLoation() {
        return loation;
    }

    /**
     * @param loation the loation to set
     */
    public void setLoation(String loation) {
        this.loation = loation;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @param geoLocation the geoLocation to set
     */
    public void setGeoLocation(ParseGeoPoint geoLocation) {
        this.geoLocation = geoLocation;
    }

    Number contact;
    String email;
    String loation;
    String desc;
    ParseGeoPoint geoLocation;

    // ParseFile promoImage;

    public HallDetailsPOJO(String desc, String location, Number capacity,
                           Number contact, Number bookAmt, String email,
                           ParseGeoPoint parseGeoPoint, String hallName) {
        super();

        this.hallName = hallName;
        this.bookingAmount = bookAmt;
        this.capicity = capacity;
        this.contact = contact;
        this.email = email;
        this.loation = location;
        this.desc = desc;

        this.geoLocation = parseGeoPoint;

    }

    public Number getCapicity() {
        return capicity;
    }

    public void setCapicity(Number capicity) {
        this.capicity = capicity;
    }

    public Number getBookingAmt() {
        return bookingAmount;
    }

    public void setBookingAmt(Number bookingAmt) {
        this.bookingAmount = bookingAmt;
    }

    public Number getContact() {
        return contact;
    }

    public void setContact(Number contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ParseGeoPoint getGeoLocation() {
        return geoLocation;
    }

    public void setgeoLocation(ParseGeoPoint d) {
        this.geoLocation = d;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

}
