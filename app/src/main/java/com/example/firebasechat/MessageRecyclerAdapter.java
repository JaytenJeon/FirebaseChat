package com.example.firebasechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

import java.util.List;

class MessageRecyclerAdapter extends FirestoreAdapter<RecyclerView.ViewHolder> {

    public MessageRecyclerAdapter(Query query) {
        super(query);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i){
            case 0:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_message, viewGroup, false);
                return new MyMessageViewHolder(view);
            case 1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_your_message, viewGroup, false);
                return new MessageViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Message message = getSnapshot(i).toObject(Message.class);
        switch (getItemViewType(i)){
            case 0:
                MyMessageViewHolder myMessageViewHolder = (MyMessageViewHolder) viewHolder;
                myMessageViewHolder.mTextMessage.setText(message.getContent());
                myMessageViewHolder.mTextTime.setText(message.getTime());
                myMessageViewHolder.mTextMessage.requestLayout();
                myMessageViewHolder.mTextTime.requestLayout();
                myMessageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                });
                break;
            default:
                MessageViewHolder messageViewHolder = (MessageViewHolder) viewHolder;
                messageViewHolder.mImageProfile.setClipToOutline(true);
                messageViewHolder.mTextName.setText(message.getName());
                messageViewHolder.mTextMessage.setText(message.getContent());
                messageViewHolder.mTextMessage.requestLayout();
                messageViewHolder.mTextTime.setText(message.getTime());
                messageViewHolder.mTextTime.requestLayout();
                messageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        String messageUid = getSnapshot(position).toObject(Message.class).getFromUid();
        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return messageUid.equals(myUid) ?0:1;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private ImageView mImageProfile;
        private TextView mTextName;
        private TextView mTextMessage;
        private TextView mTextTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mImageProfile = itemView.findViewById(R.id.image_profile);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextMessage = itemView.findViewById(R.id.text_message);
            mTextTime = itemView.findViewById(R.id.text_time);
        }
    }

    public class MyMessageViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextMessage;
        private TextView mTextTime;
        private View mView;

        public MyMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mTextMessage = itemView.findViewById(R.id.text_message);
            mTextTime = itemView.findViewById(R.id.text_time);

        }
    }

}
