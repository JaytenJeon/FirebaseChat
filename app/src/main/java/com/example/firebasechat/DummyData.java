package com.example.firebasechat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyData {
    public static final List<BaseRecyclerViewItem> FRIEND_DATA = new ArrayList<>();

    private static String[] lastNames = {"김", "남", "문", "박", "이", "정", "최", "한"};
    private static String[] firstNames = {
            "도윤", "도현", "민준", "서아", "서연", "서윤", "서준", "수아", "시우", "예준",
            "유준", "주원", "지안", "지우", "지유", "지호", "하린", "하윤", "하은", "하준"};
    private static String[] messages = {
            "",
            "바로 지금이지. 그 떄가 따로 있는 것은 아니다.",
            "우리가 쓰는 것중 가장 비싼 것은 시간이다.",
            "계획 없는 목표는 한낱 꿈에 불과하다.",
            "늦게 시작하는 것을 두려워 말고, 하다 중단 하는 것을 두려워 하라."
    };

    static {
        addHeader("친구");
        Random random = new Random();
        for(String lastName: lastNames){
            for(String firstName: firstNames){
                String name = lastName+firstName;
                int idx = random.nextInt(messages.length);
                String message = messages[idx];
                addFriend(name, message);
            }
        }
    }

    private static void addHeader(String name){
        FRIEND_DATA.add(makeHeader(name));
    }

    private static FriendHeader makeHeader(String name){
        FriendHeader header = new FriendHeader(1,name);
        return header;
    }

    private static void addFriend(String name, String message){
        FRIEND_DATA.add(makeFriend(name, message));
    }

    private static Friend makeFriend(String name, String message){
        Friend friend = new Friend(BaseRecyclerViewItem.CHILD, name, message, "", "","");
        return friend;
    }
}
