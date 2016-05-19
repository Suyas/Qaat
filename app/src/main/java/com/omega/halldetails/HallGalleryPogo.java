package com.omega.halldetails;

import com.parse.ParseFile;

public class HallGalleryPogo {

    ParseFile promoImage;


    public HallGalleryPogo(ParseFile promoImage) {


        this.promoImage = promoImage;

    }

    public ParseFile getPromoImage() {
        return promoImage;
    }

    public void setPromoImage(ParseFile promoImage) {
        this.promoImage = promoImage;
    }


}
