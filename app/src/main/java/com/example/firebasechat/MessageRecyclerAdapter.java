package com.example.firebasechat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

class MessageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Message> mMessages;

    public MessageRecyclerAdapter(List<Message> messages) {
        mMessages = messages;
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
        Message message = mMessages.get(i);
        switch (getItemViewType(i)){
            case 0:
                MyMessageViewHolder myMessageViewHolder = (MyMessageViewHolder) viewHolder;
                myMessageViewHolder.mTextMessage.setText(message.getContent());
                myMessageViewHolder.mTextTime.setText(message.getTime());
                myMessageViewHolder.mTextMessage.requestLayout();
                myMessageViewHolder.mTextTime.requestLayout();
                break;
            default:
                MessageViewHolder messageViewHolder = (MessageViewHolder) viewHolder;
                messageViewHolder.mImageProfile.setClipToOutline(true);
                messageViewHolder.mTextName.setText(message.getUser().getName());
                messageViewHolder.mTextMessage.setText(message.getContent());
                messageViewHolder.mTextMessage.requestLayout();
                messageViewHolder.mTextTime.setText(message.getTime());
                messageViewHolder.mTextTime.requestLayout();
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getUser().getEmail() == FirebaseAuth.getInstance().getCurrentUser().getEmail() ?0:1;
    }

    @Override
    public int getItemCount() {
        return  mMessages.size();
    }
    public class MessageViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageProfile;
        private TextView mTextName;
        private TextView mTextMessage;
        private TextView mTextTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageProfile = itemView.findViewById(R.id.image_profile);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextMessage = itemView.findViewById(R.id.text_message);
            mTextTime = itemView.findViewById(R.id.text_time);
        }
    }

    public class MyMessageViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextMessage;
        private TextView mTextTime;

        public MyMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextMessage = itemView.findViewById(R.id.text_message);
            mTextTime = itemView.findViewById(R.id.text_time);

        }
    }

}
