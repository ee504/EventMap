package com.starichenkov.eventmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.starichenkov.bookMarksListView.BookMarksListAdapter;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.image.ImageFullSizeFragment;
import com.starichenkov.presenter.Presenter;
import com.starichenkov.view.IView;

import java.util.ArrayList;
import java.util.List;

public class MainMapActivity extends FragmentActivity implements CallBackInterfaceMap, BookMarksListAdapter.OnEventListener, IView {
    private Fragment mapFragment;
    private Fragment bookMarksListFragment;
    //private Fragment topSearchBarFragment;
    private Fragment eventsListFragment;
    private Fragment imageFullSizeFragment;
    private Fragment loadScreenFragment;

    private static final String TAG = "MyLog";

    private Presenter presenter;
    private List<Events> events;
    private List<BookMarks> bookMarks;
    private String currentFragment;
    private String idEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mapFragment = new MapFragment();
        bookMarksListFragment = new BookMarksListFragment();
        //topSearchBarFragment = new TopSearchBarFragment();
        eventsListFragment = new EventsListFragment();
        imageFullSizeFragment = new ImageFullSizeFragment();
        loadScreenFragment = new LoadScreenFragment();
        //createEventPlaceFragment = new CreateEventPlaceFragment();
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, mapFragment)
                .show(mapFragment)
                .commit();
        getSupportFragmentManager().popBackStackImmediate();
        //currentFragment = "mapFragment";
        //fTrans.show(createEventMainFragment);
        idEvent = "0";
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
        if(currentFragment == "bookMarksListFragment") {
            Log.d(TAG, "position: " + position);
            BookMarksListFragment bookMarksListFragment = (BookMarksListFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            bookMarksListFragment.onEventClick(position);
        }else if(currentFragment == "eventsListFragment") {
            Log.d(TAG, "position: " + position);
            EventsListFragment eventsListFragment = (EventsListFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            eventsListFragment.onEventClick(position);
        }
    }

    @Override
    public void openBookMarksList(){

        Log.d(TAG, "OpenPlaceAutocomplete");
        currentFragment = "bookMarksListFragment";
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
    public void openMapWithMarker(int position){
        Log.d(TAG, "openMapWithMarker()");
        idEvent = events.get(position).getId();
        //FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        //fTrans.replace(R.id.frgmCreateEvent, mapFragment)
                //.commit();
        //getSupportFragmentManager().popBackStackImmediate();
        /*FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCreateEvent, mapFragment)
                .remove(bookMarksListFragment)
                .show(mapFragment)
                .commit();*/
        //getSupportFragmentManager().executePendingTransactions();
        //currentFragment = "mapFragment";
        getSupportFragmentManager().popBackStackImmediate();
        MapFragment mapFragment = (MapFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        mapFragment.setMarker(idEvent);
    }

    @Override
    public void getSelectedMarker(){
        if(idEvent != "0"){
            MapFragment mapFragment = (MapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            mapFragment.setMarker(idEvent);
            idEvent = "0";
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
            Log.d(TAG, "sendEvents() mapFragment");
            MapFragment mapFragment = (MapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            mapFragment.sendEvents(this.events);
        }else if(currentFragment == "bookMarksListFragment"){
            Log.d(TAG, "sendEvents() bookMarksListFragment");
            BookMarksListFragment bookMarksListFragment = (BookMarksListFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            bookMarksListFragment.sendEvents(this.events);
        }else if(currentFragment == "eventsListFragment"){
            Log.d(TAG, "sendEvents() eventsListFragment");
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
    public void openImageFullScreen(String url){
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        /*fTrans.replace(R.id.frgmCreateEvent, imageFullSizeFragment)
                .addToBackStack(null)
                .commit();*/

        fTrans.add(R.id.frgmCreateEvent, imageFullSizeFragment)
                .addToBackStack(null)
                .hide(mapFragment)
                //.show(bookMarksListFragment)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
        ImageFullSizeFragment imageFullSizeFragment = (ImageFullSizeFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        imageFullSizeFragment.setImage(url);
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
        List<Events> listEvents = new ArrayList<>();
        for(BookMarks bm : bookMarks){
            for(Events el : events){
                if(bm.getIdEvent().equals(el.getId())){
                    listEvents.add(el);
                    continue;
                }
            }
        }
        sendEvents(listEvents);

    }

    @Override
    public void getCurrentUser(){
        presenter.getCurrentUser();
    }

    @Override
    public void sendUser(Users user){

        //Log.e(TAG, "user.getFio(): " + user.getFio());
        //Log.e(TAG, "user.getMail(): " + user.getMail());
        MapFragment mapFragment = (MapFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        mapFragment.setCurrentUser(user);
    }

    @Override
    public  void createBookMark(BookMarks bookMark){
        presenter.createBookMark(bookMark);
        getAllBookmarks();
    }

    @Override
    public void deleteBookMark(BookMarks bookMark){
        presenter.deleteBookMark(bookMark);
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

    @Override
    public void back(){
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void openLoadScreen(){
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCreateEvent, loadScreenFragment)
                .commit();
    }

    @Override
    public void startMainActivity(){

        Intent intentMainMapActivity = new Intent(this, MainMapActivity.class);
        startActivity(intentMainMapActivity);

    }
}
