package com.irfanullah.videodune.Models;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    private int VIDEO_ID;

    @SerializedName("video_name")
    private String VIDEO_NAME;

    @SerializedName("hash_tag")
    private String HASH_TAG;

    @SerializedName("facebook_id")
    private String FACEBOOK_ID;

    @SerializedName("email")
    private String EMAIL;

    @SerializedName("user_id")
    private int USER_ID;

    @SerializedName("error")
    private boolean Error = false;

    @SerializedName("isAuthenticated")
    private boolean isAuthenticated = false;

    @SerializedName("isVideoUploaded")
    private boolean isVideoUploaded = false;

    @SerializedName("isSaved")
    private boolean isSaved = false;

    @SerializedName("video")
    private Video Video;

    @SerializedName("message")
    private String Message;


    private boolean isEmpty = false;

    public int getVIDEO_ID() {
        return VIDEO_ID;
    }

    public String getVIDEO_NAME() {
        return VIDEO_NAME;
    }

    public String getHASH_TAG() {
        return HASH_TAG;
    }

    public String getFACEBOOK_ID() {
        return FACEBOOK_ID;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public boolean getError() {
        return Error;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public boolean isVideoUploaded() {
        return isVideoUploaded;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public com.irfanullah.videodune.Models.Video getVideo() {
        return Video;
    }

    public String getMessage() {
        return Message;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
