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

        ListView userListView = (ListView)rootView.findViewById(R.id.usersListView);

        //Now we need to make our adapter,
        ArrayList<ParseUser> userList = new ArrayList<ParseUser>();
        adapter = new UserListAdapter(getActivity(),R.layout.user_list_item,userList);
        userListView.setAdapter(adapter);

        //We will query the internet, and Parse for user information
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                adapter.clear();
                adapter.addAll(list);
            }
        });

        return rootView;

    }
}
