package com.example.firebasechat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatRoomListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatRoomListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatRoomListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ChatRoomRecyclerViewAdapder mAdapter;
    private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;
    private HashMap<String, User> mUsers = new HashMap<>();
    private Query mQuery;

    public ChatRoomListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatRoomListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatRoomListFragment newInstance(String param1, String param2) {
        ChatRoomListFragment fragment = new ChatRoomListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().setTitle(R.string.title_chat);

        mFirestore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mQuery = mFirestore.collection("chatRooms")
                .whereEqualTo("users."+mUser.getUid(), true);


    }

    @Override
    public void onResume() {
        super.onResume();
//        if(mUsers.get(mUser.getUid()).getName() != MainActivity.USER_PROFILE.getName()){
//            View view = getView();
//            if(view instanceof RecyclerView){
//                RecyclerView recyclerView = (RecyclerView) view;
//                recyclerView.setAdapter(mAdapter);
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        if(view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            if(mAdapter == null){
                mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot document: task.getResult()){
                            HashMap<String, Boolean> users = (HashMap<String, Boolean>) document.get("users");
                            if(users.size()==1){
                                mUsers.put(MainActivity.USER_PROFILE.getUid(),  MainActivity.USER_PROFILE);
                            }
                            for(String uid: users.keySet()){
                                if(!uid.equals(MainActivity.USER_PROFILE.getUid())){
                                    Log.d("!!!!", "uid is :"+uid);
                                    FirebaseFirestore.getInstance().collection("users")
                                            .document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                            User user = documentSnapshot.toObject(User.class);
                                            mUsers.put(user.getUid(), user);
                                        }
                                    });
                                }

                            }
                            Log.d("!!!!!", mUsers.size()+", "+ users.size());

                        }

                        mAdapter = new ChatRoomRecyclerViewAdapder(mQuery, mListener, mUsers);
                        mAdapter.startListening();
                        recyclerView.setAdapter(mAdapter);

                    }
                });

            }else if(mUsers.get(mUser.getUid()) != null && mUsers.get(mUser.getUid()).getName() != MainActivity.USER_PROFILE.getName()){
                Log.d("!!!!", "User Profile is different" + mUsers.get(mUser.getUid()).getName());
                mUsers.put(mUser.getUid(), MainActivity.USER_PROFILE);
                mAdapter = new ChatRoomRecyclerViewAdapder(mQuery, mListener, mUsers);
                mAdapter.startListening();
                recyclerView.setAdapter(mAdapter);

            }
            recyclerView.setAdapter(mAdapter);

            final int initialTopPosition = recyclerView.getTop();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(recyclerView.getChildAt(0).getTop() < initialTopPosition){

                        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(5);
                    }else{
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);


                    }
                }
            });
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ChatRoom chatRoom, String name) {
        if (mListener != null) {
            mListener.onChatItemSelected(chatRoom, name);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onChatItemSelected(ChatRoom chatRoom, String name);
    }
}
