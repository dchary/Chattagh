package com.wantaghstem.chattagh.techday.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wantaghstem.chattagh.LoginActivity;
import com.wantaghstem.chattagh.MessageService;
import com.wantaghstem.chattagh.R;
import com.wantaghstem.chattagh.techday.ChattaghActivity;
import com.wantaghstem.chattagh.techday.ListUserArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 1/2/2016.
 */
public class ListUsersFragment extends Fragment {

    private String currentUserId;
    private ArrayAdapter<String> namesArrayAdapter;
    private ArrayList<String> names;
    private ListView usersListView;
    private Button logoutButton;




    private View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Creates the initial login screen.
        rootView = inflater.inflate(R.layout.activity_list_users, container, false);

        logoutButton = (Button) rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().stopService(new Intent(getContext(), MessageService.class));
                ParseUser.logOut();

                ((ChattaghActivity)getActivity()).onBackPressed();

                //Intent intent = new Intent(getContext(), LoginActivity.class);
                //startActivity(intent);
            }
        });

        return rootView;
    }


    private void updateUserList(){
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        names = new ArrayList<String>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", currentUserId);

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        names.add(userList.get(i).getUsername().toString());
                    }

                    usersListView = (ListView) rootView.findViewById(R.id.usersListView);
                    //namesArrayAdapter =
                    //        new ArrayAdapter<String>(getContext(),
                    //                R.layout.user_list_item, names);

                    namesArrayAdapter = new ListUserArrayAdapter(getContext(),
                            R.layout.user_list_item,names);
                    usersListView.setAdapter(namesArrayAdapter);
                    usersListView.setDivider(null);
                    usersListView.setDividerHeight(0);
                    usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                            openConversation(names, i);
                        }
                    });

                } else {
                    Toast.makeText(getContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    //open a conversation with one person
    private void openConversation(ArrayList<String> names, int pos) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", names.get(pos));

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> user, ParseException e) {
                if (e == null) {
                    //Intent intent = new Intent(getContext(), MessagingActivity.class);
                    //intent.putExtra("RECIPIENT_ID", user.get(0).getObjectId());
                    //startActivity(intent);
                    Bundle bundle = new Bundle();
                    bundle.putString("RECIPIENT_ID",user.get(0).getObjectId());
                    MessagingFragment messagingFragment = new MessagingFragment();
                    messagingFragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fade_in,R.animator.fade_out
                            ,R.animator.fade_in,R.animator.fade_out);
                    fragmentTransaction.replace(R.id.container, messagingFragment, null);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else {
                    Toast.makeText(getContext(),
                            "Error finding that user",
                            Toast.LENGTH_SHORT).show();
                }
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
        updateUserList();
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
