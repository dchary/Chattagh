package com.wantaghstem.chattagh.techday;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;
import com.wantaghstem.chattagh.R;

import java.util.List;

/**
 * Created by Daniel on 1/8/2016.
 */
public class UserListAdapter extends ArrayAdapter<ParseUser> {

    private Activity context;
    private int layoutResourceId;
    private List<ParseUser> data = null;

    public UserListAdapter(Context context, int layoutResourceId, List<ParseUser> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = (Activity)context;
        this.data = data;
    }

    //They will write this
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rootView = layoutInflater.inflate(layoutResourceId, parent, false);

        TextView name = (TextView)rootView.findViewById(R.id.name);
        TextView circle = (TextView)rootView.findViewById(R.id.circle);

        //Let's go ahead and set the username
        name.setText(data.get(position).getUsername());

        //We need to do something special to the circle to get the uppercase first leter of the username
        circle.setText(data.get(position).getUsername().substring(0,1).toUpperCase());


        //Now return our completed view!
        return rootView;


    }

}
