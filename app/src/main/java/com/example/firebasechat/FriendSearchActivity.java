package com.example.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FriendSearchActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText mEditEmail;
    private LinearLayout mLayoutFriend;
    private ImageView mImageProfile;
    private TextView mTextName;
    private Button mButtonAddFriend;
    private LinearLayout mLayoutFriendNotFound;
    private ImageButton mButtonCancelEdit;

    private User mFriend;
    private User mMyProfile = MainActivity.USER_PROFILE;

    private FirebaseFirestore mFirebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(R.string.title_search_friend);
        mButtonAddFriend = findViewById(R.id.button_friend_add);
        mButtonCancelEdit = findViewById(R.id.button_cancel_edit);
        mTextName = findViewById(R.id.text_name);
        mImageProfile = findViewById(R.id.image_profile);
        mImageProfile.setClipToOutline(true);
        mLayoutFriend = findViewById(R.id.layout_friend);
        mEditEmail = findViewById(R.id.edit_email);
        mLayoutFriendNotFound = findViewById(R.id.layout_friend_not_found);
        mFirebaseFirestore = FirebaseFirestore.getInstance();


        mButtonCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditEmail.setText("");
            }
        });

        mEditEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEditEmail.getText().toString().trim().length()>0){
                    mButtonCancelEdit.setVisibility(View.VISIBLE);
                }else{
                    mButtonCancelEdit.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_SEARCH:
                        mFirebaseFirestore.collection("users")
                                .whereEqualTo("email",v.getText().toString()).limit(1)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                if(task.isSuccessful()){
                                    List<User> users = task.getResult().toObjects(User.class);
                                    if(users.size()>0){
                                        User friend = users.get(0);
                                        mFriend = friend;
                                        mTextName.setText(friend.getName());
                                        mLayoutFriend.setVisibility(View.VISIBLE);
                                        mLayoutFriendNotFound.setVisibility(View.GONE);
                                        if(friend.getUid().equals(mMyProfile.getUid())){
                                            mButtonAddFriend.setEnabled(false);
                                            mButtonAddFriend.setText(R.string.message_cant_add_friend);
                                        }else{
                                            mButtonAddFriend.setEnabled(true);
                                            mButtonAddFriend.setText(R.string.label_button_friend_add);

                                        }
                                    }else{
                                        mLayoutFriendNotFound.setVisibility(View.VISIBLE);
                                        mLayoutFriend.setVisibility(View.GONE);

                                    }

                                }
                            }
                        });
                        return true;
                }
                return false;
            }
        });

        mButtonAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseFirestore.collection("users").document(mFriend.getUid())
                        .update("friends."+mMyProfile.getUid(), true)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
