package com.example.firebasechat.util;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public interface IFirebaseHelper {
    FirebaseFirestore getFirestore();
    FirebaseUser getFirebaseUser();
    FirebaseAuth getFirebaseAuth();
    String getCurrentUserId();
    CollectionReference getCollectionReference(String collection);
    DocumentReference getDocumentReference(CollectionReference collectionReference, String document);
    CollectionReference getUserCollectionReference();
    DocumentReference getCurrentUserDocumentReference();
}
