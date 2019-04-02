package com.example.firebasechat;

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

class ChatRoom implements Serializable {
    private ArrayList<String> name;
    private String id;
    private HashMap<String, Boolean> users;
    private Uri profileImg;
    private String latestMessage;
    private long seconds;
    private int userCount;

    public ChatRoom() {
    }

    public ChatRoom(ArrayList<String> name, HashMap<String, Boolean> users, String id,
                    Uri profileImg, String latestMessage, int userCount, long seconds) {
        this.name = name;
        this.id = id;
        this.profileImg = profileImg;
        this.users = users;

        this.latestMessage = latestMessage;
        this.userCount = userCount;
        this.seconds = seconds;
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

    public List<String> getName() {
        return name;
    }

    public String generateChatRoomName() {
        String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Set<String> names= new HashSet<>(getName());
        if(names.size()>1){
            for(String name: getName()){
                if(!name.equals(userName)){
                    return name;
                }
            }
        }else{
            return userName;
        }
        return null;
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
