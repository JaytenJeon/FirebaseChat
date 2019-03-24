package com.example.firebasechat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatRoomRecyclerViewAdapder extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ChatRoom> mChatRoomList;
    private final ChatFragment.OnFragmentInteractionListener mListener;

    public ChatRoomRecyclerViewAdapder(List<ChatRoom> chatRoomList, ChatFragment.OnFragmentInteractionListener listener) {
        mChatRoomList = chatRoomList;
        mListener = listener;
    }


    @NonNull


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_chat, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ChatRoom item = mChatRoomList.get(i);
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.mItem = item;
        holder.mTextName.setText(item.getName());
        holder.mTextLatestMessage.setText(item.getMessages().get(0).getContent());
        holder.mImageProfile.setClipToOutline(true);
        holder.mTextDate.setText(item.getMessages().get(0).getDate());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onChatItemSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChatRoomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final ImageView mImageProfile;
        private final TextView mTextName;
        private final TextView mTextLatestMessage;
        private final TextView mTextDate;
        private ChatRoom mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextName = view.findViewById(R.id.text_name);
            mTextLatestMessage = view.findViewById(R.id.text_latest_message);
            mImageProfile = view.findViewById(R.id.image_profile);
            mTextDate = view.findViewById(R.id.text_date);
        }

    }
}