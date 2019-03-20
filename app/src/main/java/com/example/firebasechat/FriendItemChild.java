package com.example.firebasechat;

import android.net.Uri;

public class FriendItemChild extends BaseFriendRecyclerViewItem {
    private String mStatusMessage;
    private String mPhoneNumber;
    private Uri mProfileImg;
    private Uri mCoverImg;
    private User mFriend;

    public FriendItemChild(int type, User friend) {
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

    public Uri getProfileImg() {
        return mProfileImg;
    }

    public Uri getCoverImg() {
        return mCoverImg;
    }
}

