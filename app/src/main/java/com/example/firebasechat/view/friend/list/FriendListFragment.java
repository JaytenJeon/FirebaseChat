package com.example.firebasechat.view.friend.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasechat.adapter.FriendRecyclerViewAdapter;
import com.example.firebasechat.R;
import com.example.firebasechat.data.User;
import com.example.firebasechat.view.friend.search.FriendSearchActivity;
import com.example.firebasechat.view.main.MainActivity;
import com.example.firebasechat.view.splash.SplashActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendListFragment extends Fragment implements FriendListContract.View {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FriendListPresenter mPresenter;
    private FriendRecyclerViewAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public FriendListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FriendListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendListFragment newInstance() {
        FriendListFragment fragment = new FriendListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_friend, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_log_out:
                AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(getContext(), SplashActivity.class));
                            }
                        });
                getActivity().finish();
                return true;
            case R.id.action_friend_add:
                startActivity(new Intent(getContext(), FriendSearchActivity.class));
        }
        return false;

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_friend);
        mAdapter = new FriendRecyclerViewAdapter(mListener);
        mPresenter = new FriendListPresenter(this);
        mPresenter.setFriendRecyclerViewAdapterModel(mAdapter);
        mPresenter.setFriendRecyclerViewAdapterView(mAdapter);
        mPresenter.loadFriends();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("!@#!@32", "Friend List on Resume");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("!@#!@32", "Friend List on Create View");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        if(view instanceof RecyclerView){
           setView(view);
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(User user) {
//        if (mListener != null) {
//            mListener.onFriendItemSelected(user);
//        }
//    }

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

    @Override
    public void setView(View view) {
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        Log.d("!@#!@32", "Friend List on Create");

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
        // TODO: Update argument type and mTextName

        void onFriendItemSelected(User item, int type);
    }
}
