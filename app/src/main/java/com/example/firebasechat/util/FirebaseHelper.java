package com.example.firebasechat.util;

import com.example.firebasechat.data.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class FirebaseHelper implements IFirebaseHelper {

    private static FirebaseHelper INSTANCE = new FirebaseHelper();
    private User mCurrentUser = null;
    private FirebaseHelper() {
    }

    public static FirebaseHelper getInstance() {
        return INSTANCE;
    }

    @Override
    public FirebaseFirestore getFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Override
    public FirebaseUser getFirebaseUser() {
        return getFirebaseAuth().getCurrentUser();
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Override
    public Task<InstanceIdResult> getFirebaseInstanceId() {
        return FirebaseInstanceId.getInstance().getInstanceId();
    }

    @Override
    public String getCurrentUserId() {
        return getFirebaseUser().getUid();
    }

    @Override
    public CollectionReference getCollectionReference(String collection) {
        return getFirestore().collection(collection);
    }

    @Override
    public DocumentReference getDocumentReference(CollectionReference collectionReference, String document) {
        return collectionReference.document(document);
    }

    @Override
    public CollectionReference getUserCollectionReference() {
        return getCollectionReference("users");
    }

    @Override
    public DocumentReference getCurrentUserDocumentReference() {
        return getDocumentReference(getUserCollectionReference(), getCurrentUserId());
    }

    @Override
    public DocumentReference getCurrentUserFcmIdDocumentReference() {
        return getDocumentReference(getCollectionReference("fcmIds"), getCurrentUserId());
    }

    @Override
    public void setCurrentUser(User user) {
        mCurrentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return mCurrentUser;
    }
}
