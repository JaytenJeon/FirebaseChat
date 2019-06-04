package com.example.firebasechat.view.splash;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.firebasechat.data.User;
import com.example.firebasechat.util.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mView;
    private FirebaseHelper mFirebaseHelper = FirebaseHelper.getInstance();
    public SplashPresenter(SplashContract.View view) {
        mView = view;
    }

    @Override
    public void onCreate() {
        if(mFirebaseHelper.getFirebaseUser() == null){
            mView.showSignUpActivity();
            return;
        }
        loadCurrentUser();

    }

    @Override
    public void loadCurrentUser() {
        mFirebaseHelper.getCurrentUserDocumentReference()
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        User user = task.getResult().toObject(User.class);
                        mFirebaseHelper.setCurrentUser(user);
                        mView.showMainActivity();
                    }
                });
    }
}
