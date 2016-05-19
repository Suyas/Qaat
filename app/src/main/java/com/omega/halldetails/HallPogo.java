package com.omega.halldetails;

import com.parse.ParseFile;
import com.parse.ParseGeoPoint;

public class HallPogo {

    ParseFile promoImage;
    String prmoTitle;
    String loation;
    Number capicity;
    Number bookingAmount;
    Number contact;
    String email;
    ParseGeoPoint geoLocation;


    public HallPogo(ParseFile promoImage) {
        super();
        this.promoImage = promoImage;
    }

    public ParseFile getPromoImage() {
        return promoImage;
    }

    public void setPromoImage(ParseFile promoImage) {
        this.promoImage = promoImage;
    }

    public String getPromolocation() {
        return loation;
    }

    public void setPromoLocation(String promoLocation) {
        this.loation = promoLocation;
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

}
