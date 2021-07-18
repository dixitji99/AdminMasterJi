package com.masterji.admin;

public class FeedbackData {
    /*String description,email,imageuri;

    public FeedbackData() {
    }
    public FeedbackData(String description, String email, String imageuri) {
        this.description = description;
        this.email = email;
        this.imageuri = imageuri;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getImageuri() {
        return imageuri;
    }
    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }*/
    String ftext,key;
    public FeedbackData() {
    }

    public FeedbackData(String ftext, String key) {
        this.ftext = ftext;
        this.key = key;
    }

    public String getFtext() {
        return ftext;
    }

    public void setFtext(String ftext) {
        this.ftext = ftext;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
