package com.example.firebasechat.view.friend.list;

import com.example.firebasechat.adapter.FriendRecyclerViewAdapterContract;
import com.example.firebasechat.data.User;
import com.example.firebasechat.util.FirebaseHelper;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class FriendListPresenter implements FriendListContract.Presenter {
    private FriendListContract.View mView;
    private FirebaseHelper mFirebaseHelper = FirebaseHelper.getInstance();
    private FriendRecyclerViewAdapterContract.Model mAdapterModel;
    private FriendRecyclerViewAdapterContract.View mAdapterView;
    public FriendListPresenter(FriendListContract.View view) {
        mView = view;
    }

    @Override
    public void setFriendRecyclerViewAdapterModel(FriendRecyclerViewAdapterContract.Model model) {
        mAdapterModel = model;
    }

    @Override
    public void setFriendRecyclerViewAdapterView(FriendRecyclerViewAdapterContract.View view) {
        mAdapterView = view;
    }

    @Override
    public void loadFriends() {
        mFirebaseHelper.getFriendsQuery().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    mAdapterModel.reset();
                    List<User> users = queryDocumentSnapshots.toObjects(User.class);
                    mAdapterModel.addItems(users);
                    mAdapterView.notifyAdapter();
            }
        });
    }
}
