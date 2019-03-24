package com.example.firebasechat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class FriendRecyclerViewAdapter extends FirestoreAdapter<RecyclerView.ViewHolder> {
    public static final int MY_PROFILE = 0;
    public static final int HEADER = 1;
    public static final int OTHER = 2;

    private FriendFragment.OnFragmentInteractionListener mListener;
    private List<User> mDefaultList;

    public FriendRecyclerViewAdapter(Query query,  FriendFragment.OnFragmentInteractionListener listener) {
        super(query);
        mListener = listener;
        mDefaultList = new ArrayList<>();
        mDefaultList.add(MainActivity.USER_PROFILE);
        mDefaultList.add(new FriendRecyclerHeader("친구"));

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i){
            case HEADER:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_friend_header, viewGroup, false);
                return new HeaderViewHolder(view);

            default:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_friend_child, viewGroup, false);
                return new FriendViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        User item;
        if(position<2){
            item = mDefaultList.get(position);
        }else{
            item = getSnapshot(position - 2).toObject(User.class);
        }
        switch (getItemViewType(position)){
            case HEADER:
                final HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
                headerViewHolder.mHeader = (FriendRecyclerHeader) item;
                String headerName = headerViewHolder.mHeader.getName();
                if(headerName.equals("친구")){
                    headerName = headerViewHolder.mHeader.getName() +" "+ (getItemCount() - 2);
                }
                headerViewHolder.mTextViewHeaderName.setText(headerName);
                headerViewHolder.mButtonExpand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if(headerViewHolder.mHeader.getChildList() == null){
//                            headerViewHolder.mHeader.setChildList(new ArrayList<User>());
//                            int count = 0;
//                            int pos = mItems.indexOf(headerViewHolder.mHeader);
//                            while (mItems.size() > pos+1 && mItems.get(pos+1) instanceof User){
//                                headerViewHolder.mHeader.getChildList().add((User) mItems.remove(pos+1));
//                                count++;
//                            }
//                            notifyItemRangeRemoved(pos+1, count);
//                        }else{
//                            int pos = mItems.indexOf(headerViewHolder.mHeader);
//                            int index = pos+1;
//                            for(User user : headerViewHolder.mHeader.getChildList()){
//                                mItems.add(index, user);
//                                index++;
//                            }
//                            notifyItemRangeInserted(pos+1, index - (pos+1));
//                            headerViewHolder.mHeader.setChildList(null);
//                        }
                    }
                });
                break;

            default:
                final FriendViewHolder friendViewHolder = (FriendViewHolder) viewHolder;
                friendViewHolder.mItem = (User) item;
                friendViewHolder.mTextViewName.setText(friendViewHolder.mItem.getName());

                friendViewHolder.mTextViewStatusMessage.setText(friendViewHolder.mItem.getStatusMessage());
                if(friendViewHolder.mTextViewStatusMessage.getText().length()>0){
                    friendViewHolder.mTextViewStatusMessage.setVisibility(View.VISIBLE);
                }else{
                    friendViewHolder.mTextViewStatusMessage.setVisibility(View.GONE);
                }

//                if(getItemViewType(position) == OTHER){
//                    friendViewHolder.mTextViewStatusMessage.setVisibility(View.VISIBLE);
//                    friendViewHolder.mTextViewStatusMessage.setBackgroundResource(R.drawable.ic_arrow_right);
//                    friendViewHolder.mTextViewStatusMessage.getBackground().setColorFilter(Color.parseColor("#AAAAAA"), PorterDuff.Mode.SRC_ATOP);
//                    friendViewHolder.mTextViewStatusMessage.getLayoutParams().height = (int) friendViewHolder.mTextViewName.getTextSize();
//
//                    friendViewHolder.mTextViewStatusMessage.requestLayout();
//                }
                if(getItemViewType(position) == MY_PROFILE){
                    float scale = friendViewHolder.mContext.getResources().getDisplayMetrics().density;

                    friendViewHolder.mImageViewProfile.getLayoutParams().height = (int)(scale * 44 * 1.5 + 0.5f);
                    friendViewHolder.mImageViewProfile.getLayoutParams().width = (int)(scale * 44 * 1.5 + 0.5f);
                    friendViewHolder.mView.requestLayout();
                    friendViewHolder.mTextViewName.setTextSize(22);
                }

                friendViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onFriendItemSelected(friendViewHolder.mItem, getItemViewType(position));
                    }
                });
                Context context = friendViewHolder.mContext;
                Uri profileURI = friendViewHolder.mItem.getProfileImg();
                if(profileURI != null){
//                    friendViewHolder.mImageViewProfile.setImageURI(profileURI);

                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + mDefaultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return MY_PROFILE;

            case 1:
                return HEADER;

            default:
                return position;
        }
    }


    private class FriendViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;

        private View mView;
        private ImageView mImageViewProfile;
        private TextView mTextViewName;
        private TextView mTextViewStatusMessage;
        private ImageButton mButtonAdd;
        private User mItem;

        public FriendViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mView = view;
            mImageViewProfile = view.findViewById(R.id.image_profile);
            mTextViewName = view.findViewById(R.id.text_name);
            mTextViewStatusMessage = view.findViewById(R.id.text_status_message);
            mButtonAdd = view.findViewById(R.id.button_add);

            mImageViewProfile.setClipToOutline(true);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        private FriendRecyclerHeader mHeader;
        private TextView mTextViewHeaderName;
        private ToggleButton mButtonExpand;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewHeaderName = itemView.findViewById(R.id.text_header_name);
            mButtonExpand = itemView.findViewById(R.id.button_expand);
        }
    }
}
