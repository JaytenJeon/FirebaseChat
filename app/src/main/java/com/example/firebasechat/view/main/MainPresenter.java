package com.example.firebasechat.view.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.firebasechat.util.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private FirebaseHelper mFirebaseHelper = FirebaseHelper.getInstance();

    public MainPresenter(MainContract.View view) {
        mView = view;
    }

    @Override
    public void onCreate() {
        mView.setView();
        loadFirebaseInstanceId();
    }

    @Override
    public void loadFirebaseInstanceId() {
        mFirebaseHelper.getFirebaseInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("!!!!", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        String uid = mFirebaseHelper.getCurrentUserId();
                        Map<String, Object> data = new HashMap<>();
                        data.put("uid", uid);
                        data.put("fcmId",token);
                        Log.d("!!!!!", uid+": "+token);
                        mFirebaseHelper.getCurrentUserFcmIdDocumentReference().set(data);
                    }
                });
    }
}
