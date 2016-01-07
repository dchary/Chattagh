package com.wantaghstem.chattagh.techday.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.wantaghstem.chattagh.techday.TimelinePostCommentAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel on 1/5/2016.
 */
public class CommentsFragment extends Fragment {

    private ListView commentsTimeline;
    private TimelinePostCommentAdapter mAdapter;
    private SwipeRefreshLayout swipeContainer;
    private ImageButton newMessage;
    private TimelinePost timelinePost;
    private String objectId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.commentslayout, container, false);

        Bundle args = getArguments();
        objectId = args.getString("objectId");

        commentsTimeline = (ListView) rootView.findViewById(R.id.commentsTimeline);

         mAdapter = new TimelinePostCommentAdapter(getContext(),R.layout.timeline_item_comment,
                        new ArrayList<TimelinePost>());

        commentsTimeline.setEmptyView(rootView.findViewById(R.id.nocomments));
        commentsTimeline.setAdapter(mAdapter);


        newMessage = (ImageButton)rootView.findViewById(R.id.addMessage);

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a popup!
                final Dialog postMessageDialog = new Dialog(getActivity());
                TimeLineFragment.pushDialogToBottom(postMessageDialog);
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
                        postMessageDialog.dismiss();
                    }
                });

                postMessageDialog.show();
            }
        });



        swipeContainer = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeToRefresh);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateMessageList();
            }
        });


        ParseQuery<TimelinePost> query = ParseQuery.getQuery(TimelinePost.class);
        query.whereEqualTo("objectId",objectId);
        query.findInBackground(new FindCallback<TimelinePost>() {
            @Override
            public void done(List<TimelinePost> list, ParseException e) {
                    timelinePost = list.get(0);

                    TextView senderId = (TextView)(rootView.findViewById(R.id.senderId));
                    TextView date = (TextView)(rootView.findViewById(R.id.date));
                    TextView message = (TextView)(rootView.findViewById(R.id.message));

                    senderId.setText(timelinePost.getSender());
                    message.setText(timelinePost.getMessageText());

                    Date oldDate = timelinePost.getCreatedAt();
                    Date currentDate = new Date();
                    long result = (Math.abs((oldDate.getTime()/60000)
                            - (currentDate.getTime()/60000)));
                    String suffix = "m";

                    if (result > 60L){
                        result = result / 60;
                        suffix = "h";
                    }
                    String finalDate = result + suffix;
                    date.setText(finalDate);

                    updateMessageList();
            }
        });

        return rootView;
    }


    private void updateMessageList(){
        ParseQuery<TimelinePost> query = ParseQuery.getQuery(TimelinePost.class);
        query.whereEqualTo("commentReference",objectId);
        query.orderByDescending("createdAt");
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
        t.setCommentReference(objectId);
        t.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                updateMessageList();
            }
        });

        timelinePost.setCommentCount(timelinePost.getCommentCount()+1);
        timelinePost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                mAdapter.notifyDataSetChanged();
            }
        });

    }
}
