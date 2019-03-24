package com.example.firebasechat;

import android.net.Uri;

import com.google.firebase.Timestamp;

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

    public Message() {
    }

    public Message(String name, String uid, Uri profileImg, String content){
        this.name = name;
        this.uid = uid;
        this.profileImg = profileImg;
        this.content = content;
        this.timestamp = new Timestamp(Calendar.getInstance().getTime());
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

    public String getTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(timestamp.toDate());
    }

    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("a h:mm", Locale.KOREA);
        return format.format(timestamp.toDate());
    }
}
