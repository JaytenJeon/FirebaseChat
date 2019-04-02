package com.example.firebasechat;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FriendDetailActivity extends AppCompatActivity {
    private TextView mTextName;
    private TextView mTextStatusMessage;
    private ImageView mImageProfile;
    private ImageView mImageCover;
    private ImageButton mButtonEditProfile;
    private TextView mLabelEditProfile;
    private TextView mLabelChat;
    private ImageButton mButtonClose;
    private TextView mTextPhoneNumber;
    private ImageButton mButtonChat;
    private float previousY;
    private int originHeight;
    private ConstraintLayout profileLayout;

    private FirebaseFirestore mFirebaseFirestore;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float dy = y- previousY;
                if(dy > 0){
                    profileLayout.getLayoutParams().height = (int) (profileLayout.getMeasuredHeight()  + dy);
                    profileLayout.requestLayout();
                }else if (profileLayout.getMeasuredHeight() + dy> originHeight ){

                    profileLayout.getLayoutParams().height = (int) (profileLayout.getMeasuredHeight() + dy);
                    profileLayout.requestLayout();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(profileLayout.getMeasuredHeight()> originHeight){
                    finish();
                }
                break;
        }
        previousY = event.getY();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_friend_detail);

        Intent intent = getIntent();
        User friend = (User) intent.getSerializableExtra("data");
        int type = (int) intent.getIntExtra("type",-1);
        mTextName = findViewById(R.id.text_name);
        mTextStatusMessage = findViewById(R.id.profileStatusMessage);
        mImageProfile = findViewById(R.id.image_profile);
        mTextName.setText(friend.getName());
        mTextStatusMessage.setText(friend.getStatusMessage());
        mTextPhoneNumber = findViewById(R.id.text_phone_number);
        mTextPhoneNumber.setText(friend.getPhoneNumber());
        mImageCover = findViewById(R.id.image_cover);
        mButtonChat = findViewById(R.id.button_chat);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String friendUid = friend.getUid();
                int userCount = myUid.equals(friendUid) ? 1 : 2;
                mFirebaseFirestore.collection("chatRooms")
                        .whereEqualTo("users."+myUid, true)
                        .whereEqualTo("users."+friendUid, true)
                        .whereEqualTo("userCount", userCount).limit(1)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot document = task.getResult();
                            List<ChatRoom> chatRooms =  document.toObjects(ChatRoom.class);
                            ChatRoom chatRoom;
                            if(chatRooms.size()>0){
                                chatRoom = chatRooms.get(0);
                            }else{
                                String myName = MainActivity.USER_PROFILE.getName();
                                String friendName = friend.getName();
                                String id = myUid +"_"+friendUid;
                                ArrayList<String> name = new ArrayList<>();
                                name.add(friendName);
                                name.add(myName);
                                HashMap<String, Boolean> users = new HashMap<>();
                                users.put(myUid, true);
                                users.put(friendUid, true);
                                chatRoom = new ChatRoom(name, users, id, null,null,null,userCount);

                            }
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                            intent.putExtra("data", chatRoom);
                            stackBuilder.addNextIntentWithParentStack(intent);
                            Intent parentInent = stackBuilder.editIntentAt(0);
                            parentInent.putExtra("menu",R.id.menu_chat);
                            stackBuilder.startActivities();
                            finish();
                        }

                    }
                });
            }
        });

        if(friend.getProfileImg() != null){
//            mImageProfile.setImageURI(friend.getProfileImg());
        }
        if(friend.getCoverImg() != null){
//            mImageCover.setImageURI(friend.getCoverImg());

        }

        profileLayout = findViewById(R.id.profileLayout);
        mImageProfile.setClipToOutline(true);
        mButtonEditProfile = findViewById(R.id.button_edit_profile);
        mLabelEditProfile = findViewById(R.id.label_edit_profile);
        mLabelChat = findViewById(R.id.label_chat);
        mButtonClose = findViewById(R.id.closeButton);

        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switch (type){
            case FriendRecyclerViewAdapter.MY_PROFILE:
                break;
            default:
                mLabelChat.setText(R.string.label_chat);
                mButtonEditProfile.setVisibility(View.GONE);
                mLabelEditProfile.setVisibility(View.GONE);
                break;
        }


        profileLayout.post(new Runnable() {
            @Override
            public void run() {
                originHeight = profileLayout.getMeasuredHeight();
            }
        });


    }
}
