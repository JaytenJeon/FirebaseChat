package com.example.firebasechat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebasechat.R;
import com.example.firebasechat.data.ChatRoom;
import com.example.firebasechat.data.User;
import com.example.firebasechat.view.chat.list.ChatRoomListFragment;
import com.example.firebasechat.view.main.MainActivity;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class ChatRoomRecyclerViewAdapder extends FirestoreAdapter<RecyclerView.ViewHolder> {
    private final ChatRoomListFragment.OnFragmentInteractionListener mListener;
    private HashMap<String, User> mUsers = new HashMap<>();
    public ChatRoomRecyclerViewAdapder(Query query, ChatRoomListFragment.OnFragmentInteractionListener listener, HashMap<String, User> users) {
        super(query);
        mUsers = users;
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
    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        super.onEvent(documentSnapshots, e);

    }

    @Override
    protected void onDocumentAdded(DocumentChange change) {
        super.onDocumentAdded(change);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ChatRoom item = getSnapshot(i).toObject(ChatRoom.class);

        String[] ids = item.getId().split("_");
        String toId = null;
        if(item.getUserCount() ==1){
            toId = MainActivity.USER_PROFILE.getUid();
        }else{
            for(String id: ids){
                if(!id.equals(MainActivity.USER_PROFILE.getUid())){
                    toId = id;
                }
            }

        }

        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.mItem = item;
        holder.mTextName.setText(mUsers.get(toId).getName());
        holder.mTextLatestMessage.setText(item.getLatestMessage());
        holder.mImageProfile.setClipToOutline(true);
        holder.mTextDate.setText(item.generateTimeText());
        String finalToId = toId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onChatItemSelected(holder.mItem, mUsers.get(finalToId).getName());
                }
            }
        });
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
