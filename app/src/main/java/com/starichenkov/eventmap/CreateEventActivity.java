package com.starichenkov.eventmap;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.starichenkov.presenter.Presenter;

public class CreateEventActivity extends Activity implements CreateEventMainFragment.OnClickAddressListener {


    private Fragment createEventMainFragment;
    private FragmentTransaction fTrans;
    private static final String TAG = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        createEventMainFragment = new CreateEventMainFragment();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, createEventMainFragment).commit();
        //fTrans.show(createEventMainFragment);

    }

    @Override
    public void OnClickAddress(String test) {

        Log.d(TAG, "String from fragment" + test);

    }

}
