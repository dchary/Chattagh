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


}
