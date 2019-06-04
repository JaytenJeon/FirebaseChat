package com.example.firebasechat.adapter;

import com.example.firebasechat.data.User;

import java.util.List;

public interface FriendRecyclerViewAdapterContract {
    interface View{
        void notifyAdapter();
    }

    interface Model{
        void addItems(List<User> users);
        void reset();
    }
}
