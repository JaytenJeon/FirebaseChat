package com.example.firebasechat;

import java.util.List;

public class FriendHeader extends BaseRecyclerViewItem {
    private List<Friend> childList;
    public FriendHeader(int type, String name) {
        super(type, name);
        childList = null;
    }

    public List<Friend> getChildList() {
        return childList;
    }

    public void setChildList(List<Friend> childList) {
        this.childList = childList;
    }


}
