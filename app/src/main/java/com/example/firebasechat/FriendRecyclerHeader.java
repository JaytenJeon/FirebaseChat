package com.example.firebasechat;

import java.util.List;

public class FriendRecyclerHeader extends User {
    private List<User> childList;

    public FriendRecyclerHeader(String name) {
        super(name,null,null,null,null,null);
    }

    public List<User> getChildList() {
        return childList;
    }

    public void setChildList(List<User> childList) {
        this.childList = childList;
    }


}
