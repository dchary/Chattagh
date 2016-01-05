package com.wantaghstem.chattagh.techday;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wantaghstem.chattagh.R;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(layoutResourceId, parent, false);


        TextView senderId = (TextView)(row.findViewById(R.id.senderId));
        TextView date = (TextView)(row.findViewById(R.id.date));
        TextView message = (TextView)(row.findViewById(R.id.message));

        Date oldDate = data.get(position).getCreatedAt();
        Date currentDate = new Date();
        String result =
                Long.toString(Math.abs((oldDate.getTime()/60000)
                        - (currentDate.getTime()/60000)));



        senderId.setText(data.get(position).getSender());
        date.setText(result+"m");
        message.setText(data.get(position).getMessageText());



        return row;
    }


}
