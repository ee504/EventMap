package com.starichenkov.createEvent;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.starichenkov.eventmap.MyGoogleMap;
import com.starichenkov.eventmap.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateEventPlaceFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private String TAG;
    private GoogleMap mMap;
    private MapView mapView;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private MyGoogleMap myGoogleMap;

    private CallBackInterfaceCreateEvent mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place_autocomplete, null);
        TAG = getResources().getString(R.string.TAG);
        //create google map
        mapView = (MapView) view.findViewById(R.id.map_second);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        myGoogleMap = new MyGoogleMap(getActivity(), mMap);

        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setOnMapClickListener(this);

        myGoogleMap.onMapReady(googleMap);
        mMap = myGoogleMap.getmMap();

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        //check permission ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }else{
            //make the location button visible
            mMap.setMyLocationEnabled(true);
        }
    }
    //get address from click
    @Override
    public void onMapClick(LatLng latLng) {
            Log.d(TAG, "onMapClick: " + latLng);
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                String city = addresses.get(0).getLocality();
                String street = addresses.get(0).getThoroughfare();
                String house = addresses.get(0).getFeatureName();
                mListener.SetEventAddress(addresses.get(0), latLng);
            }catch (IOException e) {
                    e.printStackTrace();
                Log.d(TAG, "e.getMessage(): " + e.getMessage());
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CallBackInterfaceCreateEvent) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

}
