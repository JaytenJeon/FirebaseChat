package com.example.firebasechat;

public class FriendItemChild extends BaseFriendRecyclerViewItem {
    private String mStatusMessage;
    private String mPhoneNumber;
    private String mProfileImg;
    private String mCoverImg;
    private Friend mFriend;

    public FriendItemChild(int type, Friend friend) {
        super(type,friend.getName());
        this.mStatusMessage = friend.getStatusMessage();
        this.mPhoneNumber = friend.getPhoneNumber();
        this.mProfileImg = friend.getProfileImg();
        this.mCoverImg = friend.getCoverImg();

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

