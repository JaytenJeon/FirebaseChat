package com.example.firebasechat.view.splash;

import com.example.firebasechat.data.User;

public interface SplashContract {
    interface View{
        void showMainActivity();
        void showSignUpActivity();
    }

    interface Presenter{
        void onCreate();
        void loadCurrentUser();
    }
}
