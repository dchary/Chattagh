package com.wantaghstem.chattagh.techday.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.wantaghstem.chattagh.MessageService;
import com.wantaghstem.chattagh.MessagingActivity;
import com.wantaghstem.chattagh.R;
import com.wantaghstem.chattagh.techday.ChattaghActivity;
import com.wantaghstem.chattagh.techday.ListUserArrayAdapter;
import com.wantaghstem.chattagh.techday.TimelinePost;
import com.wantaghstem.chattagh.techday.TimelinePostAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel on 1/5/2016.
 */
public class TimeLineFragment extends Fragment {

    private View rootView;

    private Button logoutButton;

    private TextView username;
    private TextView circle;

    private SwipeRefreshLayout swipeContainer;

    private ListView timeline;

    private TimelinePostAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Creates the initial login screen.
        rootView = inflater.inflate(R.layout.timeline, container, false);

        username = (TextView)rootView.findViewById(R.id.username);
        circle = (TextView)rootView.findViewById(R.id.circle);
        swipeContainer = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeToRefresh);

        timeline = (ListView) rootView.findViewById(R.id.timeline);

        mAdapter = new TimelinePostAdapter(getContext(),R.layout.timeline_item,new ArrayList<TimelinePost>());

        timeline.setAdapter(mAdapter);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                updateMessageList();


            }
        });

        username.setText(ParseUser.getCurrentUser().getUsername());
        circle.setText(ParseUser.getCurrentUser().getUsername().substring(0,1).toUpperCase());

        logoutButton = (Button) rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().stopService(new Intent(getContext(), MessageService.class));
                ParseUser.logOut();

                getFragmentManager().popBackStack();
                //Intent intent = new Intent(getContext(), LoginActivity.class);
                //startActivity(intent);
            }
        });

        saveMessageToTimeline();

        return rootView;
    }


    private void updateMessageList(){
        ParseQuery<TimelinePost> query = ParseQuery.getQuery(TimelinePost.class);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<TimelinePost>() {
            @Override
            public void done(List<TimelinePost> list, ParseException e) {
                if(list != null){
                    mAdapter.clear();
                    mAdapter.addAll(list);
                }
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void saveMessageToTimeline(){

        final TimelinePost t = new TimelinePost();
        t.setMessageText("Oh god it is working!");
        t.setSender(ParseUser.getCurrentUser().getUsername());
        t.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                updateMessageList();
            }
        });

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        updateMessageList();
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }
}
