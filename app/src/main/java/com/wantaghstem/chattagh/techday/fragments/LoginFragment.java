package com.wantaghstem.chattagh.techday.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.wantaghstem.chattagh.R;

/**
 * Created by Daniel on 1/2/2016.
 */
public class LoginFragment extends Fragment {

    private Button signUpButton;
    private Button loginButton;
    private EditText usernameField;
    private EditText passwordField;
    private String username;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*  The inflator is a special object in android that can take an XML layout and "inflate it".
          this means taking the layout and converting it into a view. A view is a layout item that
          has been pushed onto the screen. View's are what we ultamitely interact with!
         */
        View rootView = inflater.inflate(R.layout.login, container, false);

        // We know that the login buttons and text fields were in our layout are now somewhere on the
        // screen in rootView. Here we retrieve these items for use in our application.


        //Let's define a behavior that occurs when the login button is clicked!

        //Let's define a behavior that occurs when the sign-up button is clicked!




        return rootView;
    }

    private void goToTimelineFragment(){

        TimeLineFragment timeLineFragment = new TimeLineFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.fade_in,R.animator.fade_out
                ,R.animator.fade_in,R.animator.fade_out);
        fragmentTransaction.replace(R.id.container, timeLineFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        ParseUser.logOut();
    }



}
