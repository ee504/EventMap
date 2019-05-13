package com.starichenkov.eventmap;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

//import com.google.android.gms.location.places.Place;
//import com.google.android.libraries.places.api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.starichenkov.presenter.Presenter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CreateEventActivity extends Activity implements CallBackInterfaceCreateEvent {


    private Fragment createEventMainFragment;
    private Fragment createEventPlaceFragment;
    private FragmentTransaction fTrans;
    private static final String TAG = "MyLog";
    private int AUTOCOMPLETE_REQUEST_CODE = 1;

    private String nameEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        createEventMainFragment = new CreateEventMainFragment();
        createEventPlaceFragment = new CreateEventPlaceFragment();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, createEventMainFragment).show(createEventMainFragment)
                .commit();
        //fTrans.show(createEventMainFragment);

    }

    @Override
    protected void onResume(){
        super.onResume();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.hide(createEventMainFragment)
                .show(createEventMainFragment)
                .commit();
    }

    @Override
    public void OpenPlaceAutocomplete() {

        Log.d(TAG, "OpenPlaceAutocomplete 0");

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
    public void SetEventAddress(String city, String street, String house){

    }

}
