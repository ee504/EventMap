package com.starichenkov.createEvent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.starichenkov.eventmap.R;


public class CreateEventActivity extends FragmentActivity implements CallBackInterfaceCreateEvent {


    private Fragment createEventMainFragment;
    private Fragment createEventPlaceFragment;
    private FragmentTransaction fTrans;
    private String TAG;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        TAG = getResources().getString(R.string.TAG);

        createEventMainFragment = new CreateEventMainFragment();
        createEventPlaceFragment = new CreateEventPlaceFragment();

            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.add(R.id.frgmCreateEvent, createEventMainFragment)
                    .show(createEventMainFragment)
                    .commit();

    }
    //open map to choose the address
    @Override
    public void OpenPlaceAutocomplete() {

        Log.d(TAG, "OpenPlaceAutocomplete");

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, createEventPlaceFragment)
                .addToBackStack(null)
                .hide(createEventMainFragment)
                .show(createEventPlaceFragment)
                .commit();
    }
    //pass coordinates from map to the fragment
    @Override
    public void SetEventAddress(Address address, LatLng latLng){
        Log.d(TAG, "SetEventAddress");
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.remove(createEventPlaceFragment)
                .show(createEventMainFragment)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
        getSupportFragmentManager().executePendingTransactions();

        CreateEventMainFragment createEventMainFragment1 = (CreateEventMainFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        Log.d(TAG, "Change fragment");
        createEventMainFragment1.SetEventAddress(address, latLng);


    }
    //check. is it editing or creating a new event
    @Override
    public String getIdEvent(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("idEvent");
        }else{
            return null;
        }

    }


}
