package com.irfanullah.videodune.Models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private int USER_ID;
    @SerializedName("name")
    private String USERNAME;
    @SerializedName("password")
    private String PASSWORD;
    @SerializedName("token")
    private String TOKEN;
    @SerializedName("email")
    private String EMAIL;
    @SerializedName("created_at")
    private String CREATED_AT;
    @SerializedName("updated_at")
    private String UPDATED_AT;

    @SerializedName("error")
    private boolean ERROR = false;
    @SerializedName("isEmpty")
    private boolean IS_EMPTY = false;
    @SerializedName("message")
    private String MESSAGE;
    @SerializedName("misMatch")
    private boolean MISMATCH = false;
    @SerializedName("isTaken")
    private boolean IS_TAKEN = false;
    @SerializedName("isCreatedError")
    private boolean IS_CREATED_ERROR = false;
    @SerializedName("isCreated")
    private boolean IS_CREATED = false;
    @SerializedName("user")
    private User USER;

    @SerializedName("isLoggedIn")
    private boolean isLoggedIn = false;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public boolean isERROR() {
        return ERROR;
    }

    public boolean isIS_EMPTY() {
        return IS_EMPTY;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public boolean isMISMATCH() {
        return MISMATCH;
    }

    public boolean isIS_TAKEN() {
        return IS_TAKEN;
    }

    public boolean isIS_CREATED_ERROR() {
        return IS_CREATED_ERROR;
    }

    public boolean isIS_CREATED() {
        return IS_CREATED;
    }

    public User getUSER() {
        return USER;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public String getUPDATED_AT() {
        return UPDATED_AT;
    }
}
