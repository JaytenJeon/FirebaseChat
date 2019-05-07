package com.example.firebasechat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class ChatRoomRecyclerViewAdapder extends FirestoreAdapter<RecyclerView.ViewHolder> {
    private final ChatRoomListFragment.OnFragmentInteractionListener mListener;
    private ArrayList<User> mUsers = new ArrayList<>();
    public ChatRoomRecyclerViewAdapder(Query query, ChatRoomListFragment.OnFragmentInteractionListener listener, ArrayList<User> users) {
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
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.mItem = item;
        Log.d("!!!!!", mUsers.size() + mUsers.get(0).getName());
        holder.mTextName.setText(mUsers.get(i).getName());
        holder.mTextLatestMessage.setText(item.getLatestMessage());
        holder.mImageProfile.setClipToOutline(true);
        holder.mTextDate.setText(item.generateTimeText());
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
