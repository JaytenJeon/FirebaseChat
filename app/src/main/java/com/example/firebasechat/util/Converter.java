package com.example.firebasechat.util;

import com.example.firebasechat.data.User;
import com.google.firebase.auth.FirebaseUser;

public class Converter{

    public static User firebaseUserToUser(FirebaseUser oldValue) {
        return new User(oldValue.getDisplayName(),
                null,
                oldValue.getPhoneNumber(),
                oldValue.getEmail(),
                oldValue.getPhotoUrl(),
                null,
                oldValue.getUid()
        );
    }
}
