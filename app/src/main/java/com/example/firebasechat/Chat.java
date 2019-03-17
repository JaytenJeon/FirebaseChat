package com.example.firebasechat;

import java.util.ArrayList;
import java.util.List;

class Chat {
    private List<Friend> mUsers;
    private String mName;
    private List<Message> mMessages;
    public Chat(String name, List<Message> messages, List<Friend> users){
        this.mName = name;
        this.mMessages = messages;
        this.mUsers = users;
    }

    public List<Friend> getUsers() {
        return mUsers;
    }

    public String getName() {
        return mName;
    }

    public List<Message> getMessages() {
        return mMessages;
    }
}
