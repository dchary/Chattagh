package com.wantaghstem.chattagh.techday;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;
import com.wantaghstem.chattagh.R;
import com.wantaghstem.chattagh.techday.fragments.CommentsFragment;
import com.wantaghstem.chattagh.techday.fragments.TimeLineFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel on 1/2/2016.
 */
public class TimelinePostAdapter extends ArrayAdapter<TimelinePost> {
    Context context;
    int layoutResourceId;
    List<TimelinePost> data = null;

    public TimelinePostAdapter(Context context, int layoutResourceId, List<TimelinePost> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(layoutResourceId, parent, false);


        TextView senderId = (TextView)(row.findViewById(R.id.senderId));
        TextView date = (TextView)(row.findViewById(R.id.date));
        TextView message = (TextView)(row.findViewById(R.id.message));
        ImageButton upVote = (ImageButton)(row.findViewById(R.id.upvote));
        ImageButton downVote = (ImageButton)(row.findViewById(R.id.downvote));
        TextView votecount = (TextView)(row.findViewById(R.id.votecount));

        final TextView comments = (TextView)(row.findViewById(R.id.comments));
        String commentsString = data.get(position).getCommentCount() + " Comments";
        comments.setText(commentsString);

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommentsFragment commentsFragment = new CommentsFragment();
                Bundle args = new Bundle();
                args.putString("objectId", data.get(position).getObjectId());
                commentsFragment.setArguments(args);

                FragmentTransaction fragmentTransaction = ((ChattaghActivity)getContext()).getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fade_in,R.animator.fade_out
                        ,R.animator.fade_in,R.animator.fade_out);
                fragmentTransaction.replace(R.id.container, commentsFragment,null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                //Toast.makeText(getContext(),"Go to Make Comments",Toast.LENGTH_SHORT).show();
            }
        });

        upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).
                        setVoteCount(
                                data.get(position).getVoteCount() + 1);
                data.get(position).saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        notifyDataSetChanged();
                    }
                });
            }
        });

        downVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).
                        setVoteCount(
                                data.get(position).getVoteCount()-1);
                data.get(position).saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        notifyDataSetChanged();
                    }
                });            }
        });

        Date oldDate = data.get(position).getCreatedAt();
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

        senderId.setText(data.get(position).getSender());
        message.setText(data.get(position).getMessageText());
        votecount.setText(Integer.toString(data.get(position).getVoteCount()));



        return row;
    }


}
