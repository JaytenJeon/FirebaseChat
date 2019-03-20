package com.example.firebasechat;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity {
    private static int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                //                                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                                //                                    new AuthUI.IdpConfig.TwitterBuilder().build(),
                                //                                new AuthUI.IdpConfig.GitHubBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                //                                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                                new AuthUI.IdpConfig.AnonymousBuilder().build()
                        ))
                        .setTheme(R.style.LoginTheme)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                Friend user = new Friend(firebaseUser.getDisplayName(), null, firebaseUser.getPhoneNumber(), firebaseUser.getEmail(), firebaseUser.getPhotoUrl(), null);
                // ...
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    finish();
                }


            }
        }
    }
}
