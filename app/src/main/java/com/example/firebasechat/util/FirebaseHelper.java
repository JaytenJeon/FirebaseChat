package com.example.firebasechat.util;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseHelper implements IFirebaseHelper {

    public static FirebaseHelper INSTANCE = new FirebaseHelper();
    private static String USER_COLLECTION = "users";

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
        return getCollectionReference(USER_COLLECTION);
    }

    @Override
    public DocumentReference getCurrentUserDocumentReference() {
        return getDocumentReference(getUserCollectionReference(), getCurrentUserId());
    }
}
