package com.starichenkov.eventmap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.createEvent.CreateEventMainFragment;
import com.starichenkov.createEvent.CreateEventPlaceFragment;
import com.starichenkov.view.IView;

import java.util.List;

public class MainMapActivity extends FragmentActivity {
    private Fragment mapFragment;
    //private Fragment createEventPlaceFragment;
    private FragmentTransaction fTrans;
    private static final String TAG = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mapFragment = new MapsActivity();
        //createEventPlaceFragment = new CreateEventPlaceFragment();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, mapFragment)
                .show(mapFragment)
                .commit();
        //fTrans.show(createEventMainFragment);

    }
}
