package com.starichenkov.createEvent;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

//import com.google.android.gms.location.places.Place;
//import com.google.android.libraries.places.api;
import com.google.android.gms.maps.model.LatLng;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.account.AccountActivity;
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.eventmap.R;

import java.util.List;

public class CreateEventActivity extends FragmentActivity implements CallBackInterfaceCreateEvent {


    private Fragment createEventMainFragment;
    private Fragment createEventPlaceFragment;
    private FragmentTransaction fTrans;
    private static final String TAG = "MyLog";

    //private IPresenter presenter;

    //private String idEvent;
    //private Events event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //presenter = new Presenter(this);
        createEventMainFragment = new CreateEventMainFragment();
        createEventPlaceFragment = new CreateEventPlaceFragment();

        //Bundle extras = getIntent().getExtras();
        //if (extras != null) {
            //idEvent = extras.getString("idEvent");
            //presenter.getEventById(idEvent);
        //}else {
            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.add(R.id.frgmCreateEvent, createEventMainFragment)
                    .show(createEventMainFragment)
                    .commit();
        //}

    }

    @Override
    public void OpenPlaceAutocomplete() {

        Log.d(TAG, "OpenPlaceAutocomplete");

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, createEventPlaceFragment)
                .addToBackStack(null)
                .hide(createEventMainFragment)
                .show(createEventPlaceFragment)
                .commit();
        /*fTrans.replace(R.id.frgmCreateEvent, createEventPlaceFragment)
                .addToBackStack(null)
                .commit();*/
    }

    @Override
    public void SetEventAddress(Address address, LatLng latLng){
        Log.d(TAG, "SetEventAddress");
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.remove(createEventPlaceFragment)
                .show(createEventMainFragment)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
        getSupportFragmentManager().executePendingTransactions();
        //fTrans.replace(R.id.frgmCreateEvent, createEventMainFragment).commit();
        //getFragmentManager().popBackStack();
        //getSupportFragmentManager().popBackStackImmediate();
        //getSupportFragmentManager().popBackStackImmediate();
        //fTrans.replace(R.id.frgmCreateEvent, createEventMainFragment).commit();

        CreateEventMainFragment createEventMainFragment1 = (CreateEventMainFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        Log.d(TAG, "Change fragment");
        createEventMainFragment1.SetEventAddress(address, latLng);

        //Fragment createEventMainFragment1 = getFragmentManager().findFragmentById(R.id.frgmCreateEvent);

    }

    /*@Override
    public void createEvent(Events event){
        if(this.event != null){
            Log.d(TAG, "Change event");
            event.setId(this.event.getId());
            presenter.updateEvent(event);
            Intent intent = new Intent(this, AccountActivity.class);
            this.startActivity(intent);
        }else{
            presenter.createEvent(event);
        }
    }*/

    @Override
    public String getIdEvent(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("idEvent");
        }else{
            return null;
        }
        /*Log.d(TAG, "CreateEventActivity getEvent");
        if(event != null){
            CreateEventMainFragment createEventMainFragment = (CreateEventMainFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            createEventMainFragment.sentEvent(this.event);
        }*/

    }

    /*@Override
    public void sendEvents(List<Events> events){

        Log.d(TAG, "CreateEventActivity sendEvents");
        this.event = events.get(0);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, createEventMainFragment)
                .show(createEventMainFragment)
                .commit();

    }

    @Override
    public void sendBookMarks(List<BookMarks> bookMarks){
    }

    @Override
    public void sendUser(Users user){
    }

    @Override
    public void startMainActivity(){

        Intent intentMainMapActivity = new Intent(this, MainMapActivity.class);
        startActivity(intentMainMapActivity);

    }

    @Override
    public void deletePhoto(String photoEvent){
        presenter.deletePhoto(photoEvent);
    }

    @Override
    public void detachView(){
        presenter.detachView();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detachView();
    }*/


}
