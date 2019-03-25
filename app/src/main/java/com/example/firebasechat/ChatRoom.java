package com.example.firebasechat;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ChatRoom implements Serializable {
    private ArrayList<String> name;
    private String id;
    private HashMap<String, Boolean> users;
    private Uri profileImg;
    private String latestTimestamp;
    private String latestMessage;
    private int userCount;

    public ChatRoom() {
    }

    public ChatRoom(ArrayList<String> name, HashMap<String, Boolean> users, String id,
                    Uri profileImg, String latestTimestamp,
                    String latestMessage, int userCount) {
        this.name = name;
        this.id = id;
        this.profileImg = profileImg;
        this.users = users;
        this.latestTimestamp = latestTimestamp;
        this.latestMessage = latestMessage;
        this.userCount = userCount;
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

    public Uri getProfileImg() {
        return profileImg;
    }

    public String getLatestTimestamp() {
        return latestTimestamp;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public List<String> getName() {
        return name;
    }

    public String generateChatRoomName() {
        if(getName().size()>1){
            String userName = MainActivity.USER_PROFILE.getName();
            getName().remove(name.indexOf(userName));
        }

        return getName().get(0);
    }

    public void setLatestTimestamp(String latestTimestamp) {
        this.latestTimestamp = latestTimestamp;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }
}
