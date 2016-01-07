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
        loginButton = (Button) rootView.findViewById(R.id.loginButton);
        signUpButton = (Button) rootView.findViewById(R.id.signupButton);
        usernameField = (EditText) rootView.findViewById(R.id.loginUsername);
        passwordField = (EditText) rootView.findViewById(R.id.loginPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Let's define a behavior that occurs when the login button is clicked!
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            goToTimelineFragment();
                        } else {
                            Toast.makeText(getContext(), "Wrong username/password combo", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //Let's define a behavior that occurs when the sign-up button is clicked!
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            goToTimelineFragment();
                        } else {
                            Toast.makeText(getContext(),
                                    "There was an error signing up."
                                    , Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });



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
