package com.example.firebasechat.view.auth;

import android.content.Intent;

import com.example.firebasechat.data.User;

public interface SignUpContract {
    interface View{
        void showAuthUI();
        void showToast(int message);
        void finishActivity();
        void showMainActivity(User user);
    }

    interface Presenter{
        void onCreate();
        void onResult(int requestCode, int resultCode, Intent data);
    }
}
