package com.example.firebasechat.view.main;

import android.support.v4.app.Fragment;

public interface MainContract {
    interface View{
        void setView();
        void replaceFragment(Fragment fragment);
    }

    interface Presenter{
        void onCreate();
        void loadFirebaseInstanceId();
    }
}
