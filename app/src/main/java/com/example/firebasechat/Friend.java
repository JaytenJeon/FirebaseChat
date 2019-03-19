package com.example.firebasechat;

public class Friend {
    private String mName;
    private String mStatusMessage;
    private String mPhoneNumber;
    private String mEmail;
    private String mProfileImg;
    private String mCoverImg;

    public Friend() {
    }

    public Friend(String name, String statusMessage, String phoneNumber, String email, String profileImg, String coverImg) {
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

    public String getProfileImg() {
        return mProfileImg;
    }

    public String getCoverImg() {
        return mCoverImg;
    }
}
