package com.omega.weddingapp.city;

public class CityRowItem {
    private int imageId;
    private String title;
    private String description;

    public CityRowItem(String title, String desc) {
        this.title = title;
        this.description = desc;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
