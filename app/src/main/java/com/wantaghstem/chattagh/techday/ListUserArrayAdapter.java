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

/**
 * Created by Daniel on 1/2/2016.
 */
public class ListUserArrayAdapter extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;
    ArrayList<String> data = null;

    public ListUserArrayAdapter(Context context, int layoutResourceId, ArrayList<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);


        TextView circle = (TextView)(row.findViewById(R.id.circle));
        TextView name = (TextView)(row.findViewById(R.id.name));

        name.setText(data.get(position));
        circle.setText((data.get(position)).substring(0,1).toUpperCase());

        return row;
    }

    static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
