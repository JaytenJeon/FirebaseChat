package com.example.firebasechat;

import android.net.Uri;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Message implements Serializable {
    private String name;
    private String uid;
    private Uri profileImg;
    private String content;
    private Timestamp timestamp;
    private String time;
    private String date;
    public Message() {
    }

    public Message(String name, String uid, Uri profileImg, String content){
        this.name = name;
        this.uid = uid;
        this.profileImg = profileImg;
        this.content = content;
        this.timestamp = new Timestamp(Calendar.getInstance().getTime());
        this.time = generateTime(this.timestamp);
        this.date = generateDate(this.timestamp);
    }

    public String getUid() {
        return uid;
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
        SimpleDateFormat format = new SimpleDateFormat("a h:m", Locale.KOREA);
        return  format.format(timestamp.toDate());
    }

    public String generateDate(Timestamp timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy. m. d.", Locale.KOREA);
        return format.format(timestamp.toDate());
    }
}
