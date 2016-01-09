package com.wantaghstem.chattagh.techday.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import com.wantaghstem.chattagh.R;

import com.wantaghstem.chattagh.techday.TimelinePost;
import com.wantaghstem.chattagh.techday.TimelinePostAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel on 1/5/2016.
 */
public class TimeLineFragment extends Fragment {

    private boolean sortByTimeNotVotes = true;
    private View rootView;
    private Button newButton;
    private Button hotButton;
    private ImageButton newMessage;
    private LinearLayout newButtonHolder;
    private LinearLayout hotButtonHolder;
    private TextView username;
    private TextView circle;
    private ListView timeline;
    private SwipeRefreshLayout swipeContainer;
    private TimelinePostAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Creates the initial login screen.
        rootView = inflater.inflate(R.layout.timeline, container, false);

        username = (TextView)rootView.findViewById(R.id.username);
        circle = (TextView)rootView.findViewById(R.id.circle);
        swipeContainer = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeToRefresh);

        timeline = (ListView) rootView.findViewById(R.id.timeline);
        newButton = (Button)rootView.findViewById(R.id.newButton);
        hotButton = (Button)rootView.findViewById(R.id.hotButton);

        newMessage = (ImageButton)rootView.findViewById(R.id.addMessage);

        newButtonHolder = (LinearLayout)rootView.findViewById(R.id.newButtonHolder);
        hotButtonHolder = (LinearLayout)rootView.findViewById(R.id.hotButtonHolder);

        newButtonHolder.setBackgroundColor(getResources().getColor(R.color.chattagh_blue_selected));
        hotButtonHolder.setBackgroundColor(getResources().getColor(R.color.chattag_blue_deselected));

        mAdapter = new TimelinePostAdapter(getContext(),R.layout.timeline_item_new,new ArrayList<TimelinePost>());

        timeline.setEmptyView(rootView.findViewById(R.id.emptyview));
        timeline.setAdapter(mAdapter);

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a popup!
                final Dialog postMessageDialog = new Dialog(getActivity());
                pushDialogToBottom(postMessageDialog);
                postMessageDialog.setContentView(R.layout.newmessagelayout);
                postMessageDialog.setTitle("Post Message");
                postMessageDialog.setCancelable(true);
                postMessageDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                postMessageDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final EditText newMessage = (EditText) postMessageDialog.findViewById(R.id.newMessage);
                Button cancel = (Button) postMessageDialog.findViewById(R.id.cancel);
                Button post = (Button) postMessageDialog.findViewById(R.id.post);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postMessageDialog.dismiss();
                    }
                });

                post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String textToPost = newMessage.getText().toString();
                        saveMessageToTimeline(textToPost);
                        newButton.callOnClick();
                        postMessageDialog.dismiss();
                    }
                });

                postMessageDialog.show();
            }
        });

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
        circle.setText(ParseUser.getCurrentUser().getUsername().substring(0, 1).toUpperCase());

        /*
        logoutButton = (Button) rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                getFragmentManager().popBackStack();
                //Intent intent = new Intent(getContext(), LoginActivity.class);
                //startActivity(intent);
            }
        });
        */

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newButtonHolder.setBackgroundColor(getResources().getColor(R.color.chattagh_blue_selected));
                hotButtonHolder.setBackgroundColor(getResources().getColor(R.color.chattag_blue_deselected));

                sortByTimeNotVotes = true;

                updateMessageList();
            }
        });

        hotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotButtonHolder.setBackgroundColor(getResources().getColor(R.color.chattagh_blue_selected));
                newButtonHolder.setBackgroundColor(getResources().getColor(R.color.chattag_blue_deselected));

                sortByTimeNotVotes = false;

                updateMessageList();
            }
        });

        //ADD YOUR CODE HERE
        ImageButton viewUsers = (ImageButton)rootView.findViewById(R.id.viewUsers);
        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUsersFragment();
            }
        });

        return rootView;
    }

    private void goToUsersFragment(){

        UsersFragment usersFragment = new UsersFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.fade_in,R.animator.fade_out
                ,R.animator.fade_in,R.animator.fade_out);
        fragmentTransaction.replace(R.id.container, usersFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void updateMessageList(){
        ParseQuery<TimelinePost> query = ParseQuery.getQuery(TimelinePost.class);
        query.whereEqualTo("commentReference", null);
        if (sortByTimeNotVotes) {
            query.orderByDescending("createdAt");
        }else{
            query.orderByDescending("votecount");
        }
        query.findInBackground(new FindCallback<TimelinePost>() {
            @Override
            public void done(List<TimelinePost> list, ParseException e) {
                if (list != null) {
                    mAdapter.clear();
                    mAdapter.addAll(list);
                }
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void saveMessageToTimeline(String message){

        final TimelinePost t = new TimelinePost();
        t.setMessageText(message);
        t.setSender(ParseUser.getCurrentUser().getUsername());
        t.setVoteCount(0);
        t.setCommentCount(0);
        t.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                updateMessageList();
            }
        });

    }

    public static void pushDialogToBottom(Dialog dialog){
        //Disables dim
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //Lock to bottom
        Window window34 = dialog.getWindow();
        WindowManager.LayoutParams wlp34 = window34.getAttributes();
        wlp34.gravity = Gravity.BOTTOM;
        wlp34.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window34.setAttributes(wlp34);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogstyled);

    }

    @Override
    public void onResume() {
        updateMessageList();
        super.onResume();
    }

}
