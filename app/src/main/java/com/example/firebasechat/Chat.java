package com.example.firebasechat;

import java.io.Serializable;
import java.util.List;

class Chat implements Serializable {
    private List<User> mUsers;
    private String mName;
    private List<Message> mMessages;
    public Chat(String name, List<Message> messages, List<User> users){
        this.mName = name;
        this.mMessages = messages;
        this.mUsers = users;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public String getName() {
        return mName;
    }

    public List<Message> getMessages() {
        return mMessages;
    }
}
