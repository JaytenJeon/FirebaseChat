package com.example.firebasechat.data;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static java.lang.System.currentTimeMillis;

public class ChatRoom implements Serializable {
    private String id;
    private String name;
    private HashMap<String, Boolean> users;
    private Uri profileImg;
    private String latestMessage;
    private long seconds;
    private int userCount;

    public ChatRoom() {
    }

    public ChatRoom(HashMap<String, Boolean> users, String id, String name,
                    Uri profileImg, String latestMessage, int userCount, long seconds) {
        this.id = id;
        this.name = name;
        this.profileImg = profileImg;
        this.users = users;

        this.latestMessage = latestMessage;
        this.userCount = userCount;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public int getUserCount() {
        return userCount;
    }

    public HashMap<String, Boolean> getUsers() {
        return users;
    }

    public String getId() {
        return id;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public Uri getProfileImg() {
        return profileImg;
    }


    public String getLatestMessage() {
        return latestMessage;
    }


    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public String generateTimeText() {
        String timeText = null;
        long day = 1000*60*60*24;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long diff = currentTime - getSeconds();
        if(diff < day){
            SimpleDateFormat format = new SimpleDateFormat("a h:mm", Locale.KOREA);
            timeText = format.format(new Date(getSeconds()));
        }else if(diff < day*2){
            timeText = "어제";
        }else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy. m. d.", Locale.KOREA);
            timeText = format.format(new Date(getSeconds()));
        }

        return timeText;
    }
}
