package com.wantaghstem.chattagh.techday.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wantaghstem.chattagh.R;
import com.wantaghstem.chattagh.techday.UserListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 1/8/2016.
 */
public class UsersFragment extends Fragment {


    UserListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance){
        //They will implement everything written here!
        View rootView = layoutInflater.inflate(R.layout.userlist, container, false);


        return rootView;

    }
}
