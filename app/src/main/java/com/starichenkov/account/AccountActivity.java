package com.starichenkov.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.starichenkov.eventmap.R;

public class AccountActivity extends FragmentActivity implements CallBackInterfaceAccount {

    private String TAG;

    private Fragment accountFragment;
    private ChangeAccountFragment changeAccountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getResources().getString(R.string.TAG);
        setContentView(R.layout.activity_create_event);

        accountFragment = new AccountFragment();
        changeAccountFragment = new ChangeAccountFragment();

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, accountFragment)
                .show(accountFragment)
                .commit();
        //getSupportFragmentManager().popBackStackImmediate();
        getSupportFragmentManager().executePendingTransactions();
    }


    @Override
    public void openChangeAccountFragment(){

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCreateEvent, changeAccountFragment)
                .addToBackStack(null)
                .commit();

    }
}
