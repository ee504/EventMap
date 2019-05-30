package com.starichenkov.eventmap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.starichenkov.BookMarksListView.BookMarksListAdapter;
import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.createEvent.CreateEventMainFragment;
import com.starichenkov.createEvent.CreateEventPlaceFragment;
import com.starichenkov.view.IView;

import java.util.List;

public class MainMapActivity extends FragmentActivity implements CallBackInterfaceMap, BookMarksListAdapter.OnEventListener{
    private Fragment mapFragment;
    private Fragment bookMarksListFragment;
    //private FragmentTransaction fTrans;
    private static final String TAG = "MyLog";
    private List<Events> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mapFragment = new MapsActivity();
        bookMarksListFragment = new BookMarksList();
        //createEventPlaceFragment = new CreateEventPlaceFragment();
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, mapFragment)
                .show(mapFragment)
                .commit();
        //fTrans.show(createEventMainFragment);

    }

    @Override
    public void onEventClick(int position) {
        Log.d(TAG, "position: " + position);
        BookMarksList catFragment = (BookMarksList)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        catFragment.onEventClick(position);
    }

    @Override
    public void openBookMarksList(){

        Log.d(TAG, "OpenPlaceAutocomplete");
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, bookMarksListFragment)
                .hide(mapFragment)
                //.show(bookMarksListFragment)
                .commit();
        /*fTrans.replace(R.id.frgmCreateEvent, bookMarksListFragment)
                .addToBackStack(null)
                .commit();*/
    }

    @Override
    public void openMapWithMarker(long idEvent){
        Log.d(TAG, "openMapWithMarker()");
        //FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        //fTrans.replace(R.id.frgmCreateEvent, mapFragment)
                //.commit();
        //getSupportFragmentManager().popBackStackImmediate();
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCreateEvent, mapFragment)
                .remove(bookMarksListFragment)
                .show(mapFragment)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
        //getFragmentManager().popBackStackImmediate();
        MapsActivity catFragment1 = (MapsActivity)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        catFragment1.setMarker(idEvent);
    }
}
