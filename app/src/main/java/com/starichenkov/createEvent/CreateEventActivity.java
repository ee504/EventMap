package com.starichenkov.createEvent;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.location.Address;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;

//import com.google.android.gms.location.places.Place;
//import com.google.android.libraries.places.api;
import com.google.android.gms.maps.model.LatLng;
import com.starichenkov.eventmap.R;

public class CreateEventActivity extends Activity implements CallBackInterfaceCreateEvent {


    private Fragment createEventMainFragment;
    private Fragment createEventPlaceFragment;
    private FragmentTransaction fTrans;
    private static final String TAG = "MyLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        createEventMainFragment = new CreateEventMainFragment();
        createEventPlaceFragment = new CreateEventPlaceFragment();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, createEventMainFragment)
                .show(createEventMainFragment)
                .commit();
        //fTrans.show(createEventMainFragment);

    }

    @Override
    protected void onResume(){
        super.onResume();
        /*fTrans = getFragmentManager().beginTransaction();
        fTrans.hide(createEventMainFragment)
                .show(createEventMainFragment)
                .commit();*/
    }

    @Override
    public void OpenPlaceAutocomplete() {

        Log.d(TAG, "OpenPlaceAutocomplete");

        fTrans = getFragmentManager().beginTransaction();
        /*fTrans.add(R.id.frgmCreateEvent, createEventPlaceFragment)
                .hide(createEventMainFragment)
                .show(createEventPlaceFragment)
                .commit();*/
        fTrans.replace(R.id.frgmCreateEvent, createEventPlaceFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void SetEventAddress(Address address, LatLng latLng){
        Log.d(TAG, "SetEventAddress");
        //fTrans = getFragmentManager().beginTransaction();
        //fTrans.replace(R.id.frgmCreateEvent, createEventMainFragment).commit();
        //getFragmentManager().popBackStack();
        getFragmentManager().popBackStackImmediate();
        getFragmentManager().popBackStackImmediate();
        //fTrans.replace(R.id.frgmCreateEvent, createEventMainFragment).commit();
        CreateEventMainFragment createEventMainFragment1 = (CreateEventMainFragment)
                getFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        Log.d(TAG, "Change fragment");
        //Fragment createEventMainFragment1 = getFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        createEventMainFragment1.SetEventAddress(address, latLng);
    }


}
