package com.example.firebasechat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Message implements Serializable {
    private User mUser;
    private String mContent;
    private Date mDate;
    public Message(User user, String content){
        this.mUser = user;
        this.mContent = content;
        this.mDate = Calendar.getInstance().getTime();
    }

    public User getUser() {
        return mUser;
    }

    public String getContent() {
        return mContent;
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(mDate);
    }

    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("a h:mm", Locale.KOREA);
        return format.format(mDate);
    }
}
