package com.example.firebasechat;

import java.util.List;

public class FriendItemHeader extends BaseFriendRecyclerViewItem {
    private List<FriendItemChild> childList;
    public FriendItemHeader(int type, String name) {
        super(type, name);
        childList = null;
    }

    public List<FriendItemChild> getChildList() {
        return childList;
    }

    public void setChildList(List<FriendItemChild> childList) {
        this.childList = childList;
    }


}
