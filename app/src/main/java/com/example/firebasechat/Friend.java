package com.example.firebasechat;

public class Friend extends BaseRecyclerViewItem {
    private String mStatusMessage;
    private String mPhoneNumber;
    private String mProfileImg;
    private String mCoverImg;

    public Friend(int type, String name, String statusMessage, String phoneNumber, String profileImg, String coverImg) {
        super(type,name);
        this.mStatusMessage = statusMessage;
        this.mPhoneNumber = phoneNumber;
        this.mProfileImg = profileImg;
        this.mCoverImg = coverImg;

    }

    public String getStatusMessage() {
        return mStatusMessage;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getProfileImg() {
        return mProfileImg;
    }

    public String getCoverImg() {
        return mCoverImg;
    }
}

