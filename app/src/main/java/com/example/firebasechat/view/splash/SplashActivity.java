package com.example.firebasechat.view.splash;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.firebasechat.view.main.MainActivity;
import com.example.firebasechat.R;
import com.example.firebasechat.view.auth.SignUpActivity;
import com.example.firebasechat.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity implements SplashContract.View{

    private SplashPresenter mPresenter;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(this);
        mPresenter.onCreate();

    }

    @Override
    public void showMainActivity(User user) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("userProfile", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void showSignUpActivity() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }
}
