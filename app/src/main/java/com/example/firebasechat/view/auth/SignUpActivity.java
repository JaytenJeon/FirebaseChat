package com.example.firebasechat.view.auth;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.firebasechat.view.main.MainActivity;
import com.example.firebasechat.R;
import com.example.firebasechat.data.User;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View{
    public static int RC_SIGN_IN = 100;
    private SignUpPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mPresenter = new SignUpPresenter(this);
        mPresenter.onCreate();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onResult(requestCode, resultCode, data);
    }

    @Override
    public void showAuthUI() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                //                                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                                //                                    new AuthUI.IdpConfig.TwitterBuilder().build(),
                                //                                new AuthUI.IdpConfig.GitHubBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build()
                                //                                    new AuthUI.IdpConfig.PhoneBuilder().build(),
//                                new AuthUI.IdpConfig.AnonymousBuilder().build()
                        ))
                        .setTheme(R.style.LoginTheme)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void showToast(int message) {
        Toast.makeText(getApplicationContext(), getText(message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showMainActivity() {
        startActivity( new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
