package com.example.firebasechat;

import android.net.Uri;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Message implements Serializable {
    private String name;
    private String fromUid;
    private String toUid;
    private DocumentReference userRef;
    private Uri profileImg;
    private String content;
    private Timestamp timestamp;
    private String time;
    private String date;
    public Message() {
    }

    public Message(String name, String fromUid, String toUid, Uri profileImg, String content,
                   DocumentReference userRef){
        this.name = name;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.profileImg = profileImg;
        this.content = content;
        this.userRef = userRef;
        this.timestamp = Timestamp.now();
        this.time = generateTime(this.timestamp);
        this.date = generateDate(this.timestamp);
    }

    public DocumentReference getUserRef() {
        return userRef;
    }

    public String getFromUid() {
        return fromUid;
    }

    public String getToUid() {
        return toUid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Uri getProfileImg() {
        return profileImg;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String generateTime(Timestamp timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("a h:mm", Locale.KOREA);
        return  format.format(timestamp.toDate());
    }

    public String generateDate(Timestamp timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy. m. d.", Locale.KOREA);
        return format.format(timestamp.toDate());
    }
}
