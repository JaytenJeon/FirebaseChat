package com.example.firebasechat;


import java.io.Serializable;

abstract class BaseFriendRecyclerViewItem implements Serializable {
    public static final int MY_PROFILE = 0;
    public static final int HEADER = 1;
    public static final int CHILD = 2;
    public static final int OTHER = 3;

    private int mType;
    private String mName;

    public BaseFriendRecyclerViewItem(int type, String name) {
        mType = type;
        mName = name;
    }

    public int getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }
}