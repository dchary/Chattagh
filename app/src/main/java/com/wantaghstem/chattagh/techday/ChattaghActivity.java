package com.wantaghstem.chattagh.techday;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.wantaghstem.chattagh.R;
import com.wantaghstem.chattagh.techday.fragments.LoginFragment;

/**
 * Created by Daniel on 1/2/2016.
 */
public class ChattaghActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chattagh_activity);





        Fragment loginFragment = new LoginFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.fade_in,R.animator.fade_out);
        fragmentTransaction.add(R.id.container, loginFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

}
