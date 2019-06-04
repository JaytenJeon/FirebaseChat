package com.example.firebasechat.view.main;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.firebasechat.util.FirebaseHelper;
import com.example.firebasechat.view.chat.ChatRoomActivity;
import com.example.firebasechat.view.chat.list.ChatRoomListFragment;
import com.example.firebasechat.view.friend.detail.FriendDetailActivity;
import com.example.firebasechat.view.friend.list.FriendListFragment;
import com.example.firebasechat.adapter.FriendRecyclerViewAdapter;
import com.example.firebasechat.R;
import com.example.firebasechat.data.ChatRoom;
import com.example.firebasechat.data.DummyData;
import com.example.firebasechat.data.User;
import com.example.firebasechat.view.more.MoreFragment;


public class MainActivity extends AppCompatActivity
        implements MainContract.View,
        FriendListFragment.OnFragmentInteractionListener,
        ChatRoomListFragment.OnFragmentInteractionListener,
        MoreFragment.OnFragmentInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener{

    public static User USER_PROFILE;
    public static int MENU_ID = R.id.menu_friend;
    private FirebaseHelper mFirebaseHelper = FirebaseHelper.getInstance();
    private MainPresenter mPresenter;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private FriendListFragment mFriendListFragment = FriendListFragment.newInstance("", "");
    private ChatRoomListFragment mChatRoomListFragment = ChatRoomListFragment.newInstance("", "");
    private MoreFragment mMoreFragment = MoreFragment.newInstance("", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        mPresenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MENU_ID == R.id.menu_chat){
            mBottomNavigationView.getMenu().getItem(1).setChecked(true);
            replaceFragment(mChatRoomListFragment);
            MENU_ID = R.id.menu_friend;
        }

    }


    @Override
    public void setView() {
        mToolbar = findViewById(R.id.toolbar);
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        USER_PROFILE = mFirebaseHelper.getCurrentUser();
        Intent intent = getIntent();
        MENU_ID = intent.getIntExtra("menu", R.id.menu_friend);
        setSupportActionBar(mToolbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        if(MENU_ID == R.id.menu_chat){
            mBottomNavigationView.getMenu().getItem(1).setChecked(true);
            replaceFragment(mChatRoomListFragment);
        }else {
            replaceFragment(mFriendListFragment);
        }


    }

    @Override
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment).commit();

    }

    @Override
    public void onFriendItemSelected(User user, int type) {
        Intent intent;
        switch (type){
            case FriendRecyclerViewAdapter.HEADER:
//            case FriendRecyclerViewAdapter.OTHER:
                break;

            default:
                intent = new Intent(getApplicationContext(), FriendDetailActivity.class);
                intent.putExtra("data", user);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onChatItemSelected(ChatRoom chatRoom, String name) {
        Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
        intent.putExtra("data", chatRoom);
        intent.putExtra("name",name);
        startActivity(intent);
        overridePendingTransition(R.anim.enter,R.anim.exit);
    }


    @Override
    public void onMoreItemSelected(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_friend:
                replaceFragment(mFriendListFragment);
                return true;
            case R.id.menu_chat:
                replaceFragment(mChatRoomListFragment);
                Log.d("!!!1", ""+ DummyData.CHAT_ROOM_DATA.size());
                return true;
            case R.id.menu_more:
                replaceFragment(mMoreFragment);
                return true;
        }
        return false;
    }

}
