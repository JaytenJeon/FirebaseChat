package com.example.firebasechat.view.auth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.example.firebasechat.R;
import com.example.firebasechat.data.User;
import com.example.firebasechat.util.Converter;
import com.example.firebasechat.util.FirebaseHelper;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class SignUpPresenter implements SignUpContract.Presenter{
    private SignUpContract.View mView;
    private FirebaseHelper mFirebaseHelper = FirebaseHelper.getInstance();
    public SignUpPresenter(SignUpContract.View view) {
        mView = view;
    }

    @Override
    public void onCreate() {
        mView.showAuthUI();
    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SignUpActivity.RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Sign in failed
            if (response == null) {
                // User pressed back button
                mView.finishActivity();
            }

            // Successfully signed in
            mFirebaseHelper.getCurrentUserDocumentReference()
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    mView.showToast(R.string.message_login_success);
                                    mView.showMainActivity(document.toObject(User.class));
                                    return;
                                }
                                User user = Converter.firebaseUserToUser(mFirebaseHelper.getFirebaseUser());
                                mFirebaseHelper.getCurrentUserDocumentReference()
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mView.showToast(R.string.message_sign_up_success);
                                                mView.showMainActivity(user);
                                            }
                                        });
                            }
                        }
                    });
        }
    }
}
