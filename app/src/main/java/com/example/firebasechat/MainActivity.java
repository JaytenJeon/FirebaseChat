package com.example.firebasechat;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity
        implements FriendFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,
        MoreFragment.OnFragmentInteractionListener{
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

        mToolbar = findViewById(R.id.toolbar);
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        setActionBar(mToolbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(mFriendFragment);

    }


    @Override
    public void onChatItemSelected(Uri uri) {

    }

    @Override
    public void onFriendItemSelected(Uri uri) {

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
