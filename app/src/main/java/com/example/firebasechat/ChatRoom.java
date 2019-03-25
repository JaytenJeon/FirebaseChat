package com.example.firebasechat;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

class ChatRoom implements Serializable {
    private ArrayList<String>  name;
    private String id;
    private Uri profileImg;
    private String latestTimestamp;
    private String latestMessage;

    public ChatRoom() {
    }

    public ChatRoom(ArrayList<String> name, String id, Uri profileImg, String latestTimestamp, String latestMessage) {
        this.name = name;
        this.id = id;
        this.profileImg = profileImg;
        this.latestTimestamp = latestTimestamp;
        this.latestMessage = latestMessage;
    }

    public String getId() {
        return id;
    }

    public Uri getProfileImg() {
        return profileImg;
    }

    public String getDate() {
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

}
