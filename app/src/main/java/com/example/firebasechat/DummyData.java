package com.example.firebasechat;


import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyData {
    public static final List<User> FRIEND_DATA = new ArrayList<>();
    public static final List<Chat> CHAT_DATA = new ArrayList<>();

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
        makeFriendData();
        makeChatData();
    }

    private static void makeChatData() {
        List<User> friends = FRIEND_DATA.subList(6,16);
        for(User friend: friends){
            User user = (User) friend;
            CHAT_DATA.add(makeChat(user));
        }
    }

    private static Chat makeChat(User user){

        List<User> users = new ArrayList<>();
        List<Message> messageList= new ArrayList<>();

        users.add((User) FRIEND_DATA.get(0));
        users.add(user);
        for(int i=0; i<20; i++){
            if(i%2==0){
                messageList.add(makeMessage(users.get(1)));
            }else{
                messageList.add(makeMessage(users.get(0)));
            }
        }

        Chat chat = new Chat(user.getName(), messageList, users);

        return  chat;
    }
    private static Message makeMessage(User user){
        Random random = new Random();
        String content = messages[random.nextInt(messages.length-1)+1];
        Message message = new Message(user, content);
        return message;
    }



    private static void makeFriendData(){
        addFriend("김도윤", "김도윤 1만원/ 남도현 2만원", "010-0000-0000", null);
        addHeader("추천 친구");
        addFriend("새로운 친구를 만나보세요!");

        addHeader("플러스 친구");
        addFriend("플러스친구");

        addHeader("친구");
        Random random = new Random();
        for(String lastName: lastNames){
            for(String firstName: firstNames){
                String name = lastName+firstName;
                int idx = random.nextInt(messages.length);
                String message = messages[idx];
                addFriend(name, message, "010-0000-0000", null);
            }
        }
    }


    private static void addHeader(String name){
        FRIEND_DATA.add(makeHeader(name));
    }

    private static FriendRecyclerHeader makeHeader(String name){
        FriendRecyclerHeader header = new FriendRecyclerHeader(name);
        return header;
    }

    private static void addFriend(String name){
        FRIEND_DATA.add(makeFriendItemChild(name));
    }

    private static void addFriend(String name, String message, String phoneNumber, Uri uri){
        FRIEND_DATA.add(makeFriendItemChild(name, message, phoneNumber, uri));
    }

    private static User makeFriendItemChild(String name){

        User user = new User(name, null, null, null, null, null);
        return user;
    }

    private static User makeFriendItemChild(String name, String message,
                                                       String phoneNumber, Uri uri){

        User user = new User(name, message, phoneNumber, null, uri, null);
        return user;
    }

}
