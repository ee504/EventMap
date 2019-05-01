package com.starichenkov.eventmap;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
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

public class CreateEventActivity extends Activity implements CreateEventMainFragment.OnClickAddressListener {


    private Fragment createEventMainFragment;
    //private Fragment createEventPlaceFragment;
    private FragmentTransaction fTrans;
    private static final String TAG = "MyLog";
    private int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        createEventMainFragment = new CreateEventMainFragment();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, createEventMainFragment)
                .commit();
        fTrans.show(createEventMainFragment);

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
    public void OnClickAddress(String test) {

        Log.d(TAG, "OnClickAddress: " + test);

    }

    @Override
    public void OpenPlaceAutocomplete() {

        Log.d(TAG, "OpenPlaceAutocomplete 0");

        //createEventPlaceFragment = new CreateEventPlaceFragment();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.hide(createEventMainFragment).commit();
        //fTrans.add(R.id.frgmCreateEvent, createEventPlaceFragment).commit();


        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        Log.d(TAG, "OpenPlaceAutocomplete 1");

        // Start the autocomplete intent.
        Places.initialize(getApplicationContext(), "AIzaSyCFM9hjthZrR52HYFFpoARIZ41EQJ8Ny7M");
        PlacesClient placesClient = Places.createClient(this);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setLocationRestriction(RectangularBounds.newInstance(
                        new LatLng(56.188109, 43.702207),
                        new LatLng(56.393887, 44.160886)))
                .build(CreateEventActivity.this);
        Log.d(TAG, "OpenPlaceAutocomplete 2");
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        Log.d(TAG, "OpenPlaceAutocomplete 3");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "OpenPlaceAutocomplete 4");
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress()  + ", " + place.getLatLng());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
