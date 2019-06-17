package com.starichenkov.eventmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.presenter.Presenter;
import com.starichenkov.view.IView;

import java.util.ArrayList;
import java.util.List;

public class MainMapActivity extends FragmentActivity implements CallBackInterfaceMap{

    private Fragment mapFragment;
    private Fragment bookMarksListFragment;
    private Fragment eventsListFragment;

    private static final String TAG = "MyLog";

    private String idEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mapFragment = new MapFragment();
        bookMarksListFragment = new BookMarksListFragment();
        eventsListFragment = new EventsListFragment();

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, mapFragment)
                .show(mapFragment)
                .commit();

        idEvent = null;
    }

    @Override
    public void openDrawer(){
        MapFragment mapFragment = (MapFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        mapFragment.openDrawer();
    }

    @Override
    public void openBookMarksList(){
        idEvent = null;
        Log.d(TAG, "OpenPlaceAutocomplete");
        //currentFragment = "bookMarksListFragment";
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        //fTrans.add(R.id.frgmCreateEvent, bookMarksListFragment)
                //.addToBackStack(null)
                //.hide(mapFragment)
                //.show(bookMarksListFragment)
                //.commit();
        //fTrans.replace(R.id.frgmCreateEvent, bookMarksListFragment)
                //.addToBackStack(null)
                //.commit();
        //presenter.getEventsFromBookmarks();
        fTrans.replace(R.id.frgmCreateEvent, bookMarksListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openMapWithMarker(String idEvent){
        Log.d(TAG, "openMapWithMarker(): " + idEvent);
        this.idEvent = idEvent;
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public String getSelectedMarker(){
        return idEvent;
    }

    @Override
    public void OpenEventsList(){
        idEvent = null;
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCreateEvent, eventsListFragment)
                .addToBackStack(null)
                .commit();
    }

}
