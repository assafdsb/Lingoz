package com.xplete.lingoz.models;

public class LocaleModel {

    private int code;
    private String caption;
    private boolean user_preference;
    private boolean isAvailable;

    public LocaleModel(int code, String caption, int user_preference, int isAvailable) {
        this.code = code;
        this.caption = caption;
        this.user_preference = (user_preference == 1);
        this.isAvailable = (isAvailable == 1);
    }

    // setters

    public void setUser_preference(boolean user_preference) {
        this.user_preference = user_preference;
    }


    // getters

    public int getCode() {
        return code;
    }

    public String getCaption() {
        return caption;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isUser_preference() {
        return user_preference;
    }
}
