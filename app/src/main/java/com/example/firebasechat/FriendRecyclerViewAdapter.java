package com.example.firebasechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class FriendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseRecyclerViewItem> mItems;
    private FriendFragment.OnFragmentInteractionListener mListener;
    public FriendRecyclerViewAdapter(List<BaseRecyclerViewItem> items, FriendFragment.OnFragmentInteractionListener listener) {
        mItems = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i){
            case BaseRecyclerViewItem.HEADER:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_friend_header, viewGroup, false);
                return new HeaderViewHolder(view);

            case BaseRecyclerViewItem.CHILD:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_friend_child, viewGroup, false);
                return new FriendViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BaseRecyclerViewItem item = mItems.get(i);
        switch (item.getType()){
            case BaseRecyclerViewItem.HEADER:
                final HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
                headerViewHolder.mHeader = (FriendHeader) item;
                String headerName = headerViewHolder.mHeader.getName();
                if(headerName.equals("친구")){
                    headerName = headerViewHolder.mHeader.getName() + " "+ (mItems.size() -mItems.indexOf(item)-1);
                }
                headerViewHolder.mTextViewHeaderName.setText(headerName);
                headerViewHolder.mButtonExpand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(headerViewHolder.mHeader.getChildList() == null){
                            headerViewHolder.mHeader.setChildList(new ArrayList<Friend>());
                            int count = 0;
                            int pos = mItems.indexOf(headerViewHolder.mHeader);
                            while (mItems.size() > pos+1 && mItems.get(pos+1) instanceof Friend){
                                headerViewHolder.mHeader.getChildList().add((Friend) mItems.remove(pos+1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos+1, count);
                        }else{
                            int pos = mItems.indexOf(headerViewHolder.mHeader);
                            int index = pos+1;
                            for(Friend friend: headerViewHolder.mHeader.getChildList()){
                                mItems.add(index,friend);
                                index++;
                            }
                            notifyItemRangeInserted(pos+1, index - (pos+1));
                            headerViewHolder.mHeader.setChildList(null);
                        }
                    }
                });
                break;

            case BaseRecyclerViewItem.CHILD:
                final FriendViewHolder friendViewHolder = (FriendViewHolder) viewHolder;
                friendViewHolder.mFriend = (Friend) item;
                friendViewHolder.mTextViewName.setText(friendViewHolder.mFriend.getName());

                friendViewHolder.mTextViewStatusMessage.setText(friendViewHolder.mFriend.getStatusMessage());
                if(friendViewHolder.mTextViewStatusMessage.getText().length()>0){
                    friendViewHolder.mTextViewStatusMessage.setVisibility(View.VISIBLE);
                }else{
                    friendViewHolder.mTextViewStatusMessage.setVisibility(View.GONE);
                }

                friendViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onFriendItemSelected(friendViewHolder.mFriend);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }


    private class FriendViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;

        private View mView;
        private ImageView mImageViewProfile;
        private  TextView mTextViewName;
        private TextView mTextViewStatusMessage;
        private ImageButton mButtonAdd;
        private Friend mFriend;

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
        private FriendHeader mHeader;
        private TextView mTextViewHeaderName;
        private ToggleButton mButtonExpand;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewHeaderName = itemView.findViewById(R.id.text_header_name);
            mButtonExpand = itemView.findViewById(R.id.button_expand);
        }
    }
}
