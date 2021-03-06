package com.example.firebasechat.data;

import android.net.Uri;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String statusMessage;
    private String phoneNumber;
    private String email;
    private String uid;
    private Uri profileImg;
    private Uri coverImg;

    public User() {
    }

    public User(String name, String statusMessage, String phoneNumber, String email,
                Uri profileImg, Uri coverImg, String uid) {
        this.name = name;
        this.statusMessage = statusMessage;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.profileImg = profileImg;
        this.coverImg = coverImg;
        this.uid = uid;
        
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Uri getProfileImg() {
        return profileImg;
    }

    public Uri getCoverImg() {
        return coverImg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
