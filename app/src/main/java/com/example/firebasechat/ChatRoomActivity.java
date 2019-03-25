    package com.example.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

    public class ChatRoomActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private EditText editText;
        private ImageButton buttonSend;
        private MessageRecyclerAdapter mAdapter;
        private FirebaseFirestore mFirestore;
        private FirebaseUser mUser;
        private Query mQuery;
//
//        @Override
//        public boolean dispatchTouchEvent(MotionEvent ev) {
//            View v = (View)getCurrentFocus();
//
//            if (ev.getAction() == MotionEvent.ACTION_DOWN && v != null) {
//                if ( v.getParent() instanceof LinearLayout) {
//
//                    Rect outRect = new Rect();
//                    v.getGlobalVisibleRect(outRect);
//                    if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
//                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    }
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }



        @Override
        public void onBackPressed() {
            super.onBackPressed();
            overridePendingTransition(R.anim.back_in,R.anim.back_out);

        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case android.R.id.home:
                    finish();
                    overridePendingTransition(R.anim.back_in,R.anim.back_out);
                    return true;

            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat_room);

            Intent intent = getIntent();
            final ChatRoom chatRoom = (ChatRoom) intent.getSerializableExtra("data");
            getSupportActionBar().setTitle(chatRoom.generateChatRoomName());
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorChatRoomBackground)));
            getSupportActionBar().setElevation(5);
            recyclerView = findViewById(R.id.recycler_message);
            LinearLayoutManager ll = new LinearLayoutManager(this);
//            ll.setStackFromEnd(true);
//            ll.setReverseLayout(true);
            recyclerView.setLayoutManager(ll);

            mFirestore = FirebaseFirestore.getInstance();
            mUser = FirebaseAuth.getInstance().getCurrentUser();
            mQuery = mFirestore.collection("chatRooms")
                    .document(chatRoom.getId()).collection("messages")
                    .orderBy("timestamp");


            if(mAdapter == null){
                mAdapter = new MessageRecyclerAdapter(mQuery);
                Toast.makeText(getApplicationContext(), ""+mAdapter.getItemCount(),Toast.LENGTH_SHORT).show();
                mAdapter.startListening();
            }

            recyclerView.setAdapter(mAdapter);
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.scrollToPosition(mAdapter.getItemCount()-1);

                }
            },0);
            recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    recyclerView.scrollToPosition(mAdapter.getItemCount()-1);

                }
            });


            buttonSend = findViewById(R.id.button_send);
            buttonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    chatRoom.getMessages().add(new Message(chatRoom.getUsers().get(0), editText.getText().toString().trim()));
                    User user = MainActivity.USER_PROFILE;
                    String content = editText.getText().toString().trim();
                    mFirestore.collection("chatRooms")
                            .document(chatRoom.getId()).collection("messages")
                            .add(new Message(user.getName(), mUser.getUid(), null,content))
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    recyclerView.scrollToPosition(mAdapter.getItemCount()-1);
                                    editText.setText("");

                                }
                            });
//                    mAdapter.notifyDataSetChanged();
                }
            });
            editText = findViewById(R.id.edit_input);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(editText.getText().toString().trim().length()>0){
                        buttonSend.setVisibility(View.VISIBLE);
                    }else{
                        buttonSend.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }
