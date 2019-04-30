package com.starichenkov.eventmap;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class CreateEventPlaceFragment extends Fragment {

    private static final String TAG = "MyLog";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place_autocomplete, null);

        /*PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //txtVw.setText(place.getName());
                Log.d(TAG, "onPlaceSelected: " + place.getName());
            }
            @Override
            public void onError(Status status) {
                //txtVw.setText(status.toString());
                Log.d(TAG, "onError: " + status.toString());
            }
        });*/

        return view;

    }


}
