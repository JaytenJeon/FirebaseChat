package com.example.firebasechat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileEditActivity extends AppCompatActivity {
    private boolean canEdit = false;

    private ImageView mImageProfile;
    private ImageView mImageCover;
    private EditText mEditName;
    private EditText mEditStatusMessage;
    private TextView mTextStatusMessage;
    private EditText mEditEmail;
    private Toolbar mToolbar;

    private User mUser;
    private int mType;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), FriendDetailActivity.class);
                intent.putExtra("data", mUser);
                intent.putExtra("type", mType);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_edit_profile:



                mUser.setName(mEditName.getText().toString());
                mUser.setStatusMessage(mEditStatusMessage.getText().toString());
                MainActivity.USER_PROFILE = mUser;
                FirebaseFirestore.getInstance().collection("users")
                        .document(mUser.getUid())
                        .update("name", mEditName.getText().toString(), "statusMessage", mEditStatusMessage.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                UserProfileChangeRequest profileChangeRequest
                                        = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(mEditName.getText().toString())
                                        .build();
                                FirebaseAuth.getInstance().getCurrentUser()
                                        .updateProfile(profileChangeRequest)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Intent intent = new Intent();
                                                intent = new Intent(getApplicationContext(), FriendDetailActivity.class);
                                                MainActivity.USER_PROFILE = mUser;
                                                intent.putExtra("data", mUser);
                                                intent.putExtra("type", mType);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }
                        });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_profile_edit, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_edit_profile).setEnabled(canEdit);
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra("data");
        mType = intent.getIntExtra("type",0);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.label_edit_profile);
        mImageProfile = findViewById(R.id.image_profile);
        mImageProfile.setClipToOutline(true);
        mEditName = findViewById(R.id.edit_name);
        mEditName.setText(mUser.getName());
        mEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                canEdit = canEditProfile();
                invalidateOptionsMenu();
                if(s.toString().length()<0){
                    mEditName.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWarring)));
                }else{
                    mEditName.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditStatusMessage = findViewById(R.id.edit_status_message);
        mEditStatusMessage.setText(mUser.getStatusMessage());
        mEditStatusMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mTextStatusMessage.setText(s.toString());
                canEdit = canEditProfile();
                invalidateOptionsMenu();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTextStatusMessage = findViewById(R.id.text_status_message);
        mTextStatusMessage.setText(mEditStatusMessage.getText().toString());
        mEditEmail = findViewById(R.id.edit_email);
        mEditEmail.setText(mUser.getEmail());
    }

    private boolean canEditProfile(){
        boolean isNameChanged = ! mEditName.getText().toString().equals(mUser.getName());
        String beforeStatusMessage = mUser.getStatusMessage() == null? "" : mUser.getStatusMessage();
        String afterStatusMessage =  mEditStatusMessage.getText().toString();
        boolean isStatusMessageChanged = ! beforeStatusMessage.equals(afterStatusMessage);

        if(mEditName.getText().toString().length()>0&&(isNameChanged || isStatusMessageChanged)){
            return true;
        }else{
            return false;
        }

    }
}
