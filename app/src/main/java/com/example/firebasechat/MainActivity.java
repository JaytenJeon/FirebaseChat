package com.example.firebasechat;

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

public class MainActivity extends AppCompatActivity
        implements FriendFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,
        MoreFragment.OnFragmentInteractionListener{
    public static User USER_PROFILE;

    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;

    private FriendFragment mFriendFragment = FriendFragment.newInstance("", "");
    private ChatFragment mChatFragment = ChatFragment.newInstance("", "");
    private MoreFragment mMoreFragment = MoreFragment.newInstance("", "");


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.menu_friend:
                    replaceFragment(mFriendFragment);
                    return true;
                case R.id.menu_chat:
                    replaceFragment(mChatFragment);
                    Log.d("!!!1", ""+DummyData.CHAT_ROOM_DATA.size());
                    return true;
                case R.id.menu_more:
                    replaceFragment(mMoreFragment);
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        USER_PROFILE = (User) intent.getSerializableExtra("userProfile");

        mToolbar = findViewById(R.id.toolbar);
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        setSupportActionBar(mToolbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mFriendFragment).commit();


    }

    @Override
    public void onFriendItemSelected(User user, int type) {
        Intent intent;

        switch (type){
            case FriendRecyclerViewAdapter.HEADER:
            case FriendRecyclerViewAdapter.OTHER:
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
    public void onChatItemSelected(ChatRoom chatRoom) {
        Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
        intent.putExtra("data", chatRoom);
        startActivity(intent);
        overridePendingTransition(R.anim.enter,R.anim.exit);
    }


    @Override
    public void onMoreItemSelected(Uri uri) {

    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment).commit();

    }
}
