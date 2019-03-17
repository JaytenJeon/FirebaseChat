package com.example.firebasechat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Message implements Serializable {
    private Friend mUser;
    private String mContent;
    private Date mDate;
    public Message(Friend user, String content){
        this.mUser = user;
        this.mContent = content;
        this.mDate = Calendar.getInstance().getTime();
    }

    public Friend getUser() {
        return mUser;
    }

    public String getContent() {
        return mContent;
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(mDate);
    }
}
