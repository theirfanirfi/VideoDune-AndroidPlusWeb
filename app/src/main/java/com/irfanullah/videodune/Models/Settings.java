package com.irfanullah.videodune.Models;

import com.google.gson.annotations.SerializedName;

public class Settings {

    @SerializedName("id")
    private int ID;
    @SerializedName("user_id")
    private int USER_ID;
    @SerializedName("username")
    private String USERNAME;
    @SerializedName("address")
    private String ADDRESS;
    @SerializedName("hashtag")
    private String HASHTAG;
    @SerializedName("isAuthenticated")
    private boolean AUTHENTICATED = false;
    @SerializedName("error")
    private boolean ERROR = false;
    @SerializedName("isSaved")
    private boolean SAVED = false;
    @SerializedName("settings")
    private Settings SETTINGS;
    @SerializedName("message")
    private String MESSAGE;

    public String getMESSAGE() {
        return MESSAGE;
    }

    public int getID() {
        return ID;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getHASHTAG() {
        return HASHTAG;
    }

    public boolean isAUTHENTICATED() {
        return AUTHENTICATED;
    }

    public boolean isERROR() {
        return ERROR;
    }

    public boolean isSAVED() {
        return SAVED;
    }

    public Settings getSETTINGS() {
        return SETTINGS;
    }
}
