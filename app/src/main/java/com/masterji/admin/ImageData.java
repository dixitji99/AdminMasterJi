package com.masterji.admin;

public class ImageData {
    /*String imageuri,imagename;
    public ImageData() {
    }

    public ImageData(String imageuri, String imagename) {
        this.imageuri = imageuri;
        this.imagename = imagename;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getImageuri() {
        return imageuri;
    }
    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }*/
    String title,url;

    public ImageData() {
    }

    public ImageData(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}