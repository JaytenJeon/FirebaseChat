package com.example.firebasechat.view.main;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
    }
}
