package com.example.firebasechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                User user = new User(firebaseUser.getDisplayName(), null, firebaseUser.getPhoneNumber(), firebaseUser.getEmail(), firebaseUser.getPhotoUrl(), null);
                // ...
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(firebaseUser.getUid()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document= task.getResult();
                                    if(document.exists()){
                                        Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }else{
                                        db.collection("users")
                                                .document(firebaseUser.getUid())
                                                .set(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getApplicationContext(),"회원가입 성공", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                        finish();
                                                    }
                                                });
                                    }
                                }else Log.d("SIGN_UP","User document get fail");


                            }
                        });

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
