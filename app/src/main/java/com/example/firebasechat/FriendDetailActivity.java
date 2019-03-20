package com.example.firebasechat;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendDetailActivity extends AppCompatActivity {
    private TextView mTextName;
    private TextView mTextStatusMessage;
    private ImageView mImageProfile;
    private ImageView mImageCover;
    private ImageButton mButtonEditProfile;
    private TextView mLabelEditProfile;
    private TextView mLabelChat;
    private ImageButton mButtonClose;
    private TextView textPhoneNumber;
    private float previousY;
    private int originHeight;
    private ConstraintLayout profileLayout;

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
        FriendItemChild friendItemChild = (FriendItemChild) intent.getSerializableExtra("data");
        mTextName = findViewById(R.id.text_name);
        mTextStatusMessage = findViewById(R.id.profileStatusMessage);
        mImageProfile = findViewById(R.id.image_profile);
        mTextName.setText(friendItemChild.getName());
        mTextStatusMessage.setText(friendItemChild.getStatusMessage());
        textPhoneNumber = findViewById(R.id.text_phone_number);
        textPhoneNumber.setText(friendItemChild.getPhoneNumber());
        mImageCover = findViewById(R.id.image_cover);



        if(friendItemChild.getProfileImg() != null){
            mImageProfile.setImageURI(friendItemChild.getProfileImg());
        }
        if(friendItemChild.getCoverImg() != null){
            mImageCover.setImageURI(friendItemChild.getCoverImg());

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

        switch (friendItemChild.getType()){
            case BaseFriendRecyclerViewItem.CHILD:
                mLabelChat.setText(R.string.label_chat);
                mButtonEditProfile.setVisibility(View.GONE);
                mLabelEditProfile.setVisibility(View.GONE);
        }


        profileLayout.post(new Runnable() {
            @Override
            public void run() {
                originHeight = profileLayout.getMeasuredHeight();
            }
        });


    }
}
