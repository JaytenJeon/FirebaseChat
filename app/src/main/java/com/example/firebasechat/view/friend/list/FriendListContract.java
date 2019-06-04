package com.example.firebasechat.view.friend.list;

import com.example.firebasechat.adapter.FriendRecyclerViewAdapterContract;


public interface FriendListContract {
    interface View{
        void setView(android.view.View view);

    }

    interface Presenter{
        void setFriendRecyclerViewAdapterModel(FriendRecyclerViewAdapterContract.Model model);
        void setFriendRecyclerViewAdapterView(FriendRecyclerViewAdapterContract.View view);
        void loadFriends();
    }
}
