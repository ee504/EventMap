package com.starichenkov.eventmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.starichenkov.BookMarksListView.BookMarksListAdapter;
import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.presenter.Presenter;
import com.starichenkov.view.IView;

import java.util.List;

public class MainMapActivity extends FragmentActivity implements CallBackInterfaceMap, BookMarksListAdapter.OnEventListener, IView {
    private Fragment mapFragment;
    private Fragment bookMarksListFragment;
    //private Fragment topSearchBarFragment;
    private Fragment eventsListFragment;

    private static final String TAG = "MyLog";

    private Presenter presenter;
    private List<Events> events;
    private List<BookMarks> bookMarks;
    private String currentFragment;
    private long idEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mapFragment = new MapFragment();
        bookMarksListFragment = new BookMarksListFragment();
        //topSearchBarFragment = new TopSearchBarFragment();
        eventsListFragment = new EventsListFragment();
        //createEventPlaceFragment = new CreateEventPlaceFragment();
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, mapFragment)
                .show(mapFragment)
                .commit();
        getSupportFragmentManager().popBackStackImmediate();
        //currentFragment = "mapFragment";
        //fTrans.show(createEventMainFragment);
        idEvent = -1;
        presenter = new Presenter(this);

    }

    @Override
    public void openDrawer(){
        MapFragment mapFragment = (MapFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        mapFragment.openDrawer();
    }

    @Override
    public void onEventClick(int position) {
        Log.d(TAG, "position: " + position);
        BookMarksListFragment catFragment = (BookMarksListFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        catFragment.onEventClick(position);
    }

    @Override
    public void openBookMarksList(){

        Log.d(TAG, "OpenPlaceAutocomplete");
        currentFragment = "bookMarksListFragment";
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        /*fTrans.add(R.id.frgmCreateEvent, bookMarksListFragment)
                .addToBackStack(null)
                .hide(mapFragment)
                //.show(bookMarksListFragment)
                .commit();*/
        fTrans.replace(R.id.frgmCreateEvent, bookMarksListFragment)
                .addToBackStack(null)
                .commit();
        //presenter.getEventsFromBookmarks();
        /*fTrans.replace(R.id.frgmCreateEvent, bookMarksListFragment)
                .addToBackStack(null)
                .commit();*/
    }

    @Override
    public void openMapWithMarker(int position){
        Log.d(TAG, "openMapWithMarker()");
        idEvent = events.get(position).id;
        //FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        //fTrans.replace(R.id.frgmCreateEvent, mapFragment)
                //.commit();
        //getSupportFragmentManager().popBackStackImmediate();
        /*FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCreateEvent, mapFragment)
                .remove(bookMarksListFragment)
                .show(mapFragment)
                .commit();*/
        getSupportFragmentManager().executePendingTransactions();
        //currentFragment = "mapFragment";
        getSupportFragmentManager().popBackStackImmediate();
        /*MapFragment mapFragment = (MapFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        mapFragment.setMarker(idEvent);*/
    }

    @Override
    public void getSelectedMarker(){
        if(idEvent != -1){
            MapFragment mapFragment = (MapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            mapFragment.setMarker(idEvent);
            idEvent = -1;
        }
    }

    @Override
    public void OpenEventsList(){
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCreateEvent, eventsListFragment)
                .addToBackStack(null)
                .commit();
        //currentFragment = "eventsListFragment";
        //sendEvents(events);
    }


    @Override
    public void sendEvents(List<Events> events){
        this.events = events;
        if(currentFragment == "mapFragment") {
            MapFragment mapFragment = (MapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            mapFragment.sendEvents(this.events);
        }else if(currentFragment == "bookMarksListFragment"){
            BookMarksListFragment bookMarksListFragment = (BookMarksListFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            bookMarksListFragment.sendEvents(this.events);
        }else if(currentFragment == "eventsListFragment"){
            EventsListFragment eventsListFragment = (EventsListFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            eventsListFragment.sendEvents(this.events);
            /*BookMarksListFragment bookMarksListFragment = (BookMarksListFragment)
                    getChildFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            bookMarksListFragment.sendEvents(this.events);*/
        }
    }

    @Override
    public void sendBookMarks(List<BookMarks> bookMarks){
        this.bookMarks = bookMarks;
        MapFragment mapFragment = (MapFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        mapFragment.sendBookMarks(this.bookMarks);
    }

    @Override
    public void getEvents(){
        if(currentFragment == "bookMarksListFragment") {
            getEventsFromBookmarks();
        }else if(currentFragment == "eventsListFragment") {
            sendEvents(events);
        }
    }

    @Override
    public void getAllBookmarks(){
        presenter.getAllBookmarks();
    }

    @Override
    public void getAllEvents(){
        presenter.getAllEvents();
    }

    @Override
    public void getEventsFromBookmarks(){
        presenter.getEventsFromBookmarks();
    }

    @Override
    public  void createBookMark(int idUser, long id){
        presenter.createBookMark(idUser, id);
        getAllBookmarks();
    }

    @Override
    public void deleteBookMark(int idUser, long id){
        presenter.deleteBookMark(idUser, id);
        getAllBookmarks();
    }

    @Override
    public void setCurrentFragment(String currentFragment){
        this.currentFragment = currentFragment;
    }

    @Override
    public void detachView(){
        presenter.detachView();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detachView();
    }

    @Override
    public void filter(String query){
        EventsListFragment eventsListFragment = (EventsListFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        eventsListFragment.filter(query);
    }
}
