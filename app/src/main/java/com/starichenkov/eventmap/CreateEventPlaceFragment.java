package com.starichenkov.eventmap;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

public class CreateEventPlaceFragment extends Fragment {

    private static final String TAG = "MyLog";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place_autocomplete, null);

        //AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                //getSupportFragmentManager().findFragmentById(R.layout.fragment_place_autocomplete);
        /*autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
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
