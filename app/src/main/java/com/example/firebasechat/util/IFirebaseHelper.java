package com.example.firebasechat.util;


import com.example.firebasechat.data.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.iid.InstanceIdResult;

public interface IFirebaseHelper {
    FirebaseFirestore getFirestore();
    FirebaseUser getFirebaseUser();
    FirebaseAuth getFirebaseAuth();
    Task<InstanceIdResult> getFirebaseInstanceId();
    String getCurrentUserId();
    CollectionReference getCollectionReference(String collection);
    DocumentReference getDocumentReference(CollectionReference collectionReference, String document);
    CollectionReference getUserCollectionReference();
    DocumentReference getCurrentUserDocumentReference();
    DocumentReference getCurrentUserFcmIdDocumentReference();
    void setCurrentUser(User user);
    User getCurrentUser();
    Query getFriendsQuery();
}
