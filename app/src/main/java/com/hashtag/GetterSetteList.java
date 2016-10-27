package com.hashtag;

/**
 * Created by Deepak on 10/27/2016.
 */

public class GetterSetteList {
    private String status_id;
    private String ImageURL;
    private String mStatus;
    private String Statustime;
    private String User_id;
    private String Username;

    public String setStatusID() {
        return status_id;
    }

    public void setStatusID(String status_id) {
        this.status_id = status_id;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatustime(String statustime) {
        Statustime = statustime;
    }

    public String getStatustime() {
        return Statustime;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

}
