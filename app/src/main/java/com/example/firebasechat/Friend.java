package com.example.firebasechat;

import android.net.Uri;

public class Friend {
    private String mName;
    private String mStatusMessage;
    private String mPhoneNumber;
    private String mEmail;
    private Uri mProfileImg;
    private Uri mCoverImg;

    public Friend() {
    }

    public Friend(String name, String statusMessage, String phoneNumber, String email, Uri profileImg, Uri coverImg) {
        mName = name;
        mStatusMessage = statusMessage;
        mPhoneNumber = phoneNumber;
        mEmail = email;
        mProfileImg = profileImg;
        mCoverImg = coverImg;
    }

    public String getName() {
        return mName;
    }

    public String getStatusMessage() {
        return mStatusMessage;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public Uri getProfileImg() {
        return mProfileImg;
    }

    public Uri getCoverImg() {
        return mCoverImg;
    }
}
