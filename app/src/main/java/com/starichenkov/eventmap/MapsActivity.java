package com.starichenkov.eventmap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;

import com.google.android.gms.maps.model.MarkerOptions;
import com.starichenkov.customClasses.SomeEvet;
import com.starichenkov.Model.DB;
import com.starichenkov.presenter.Presenter;

public class MapsActivity extends FragmentActivity  implements OnMapReadyCallback, OnClickListener{

    private GoogleMap mMap;
    private static final String TAG = "MyLog";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private SomeEvet someEvent = new SomeEvet();
    private Button btnDrawerOpener;
    private Button btnUsersData;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Presenter presenter;
    private DB db;

    private Button btnRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnDrawerOpener = (Button) findViewById(R.id.btnDrawerOpener);
        btnDrawerOpener.setOnClickListener(this);

        btnUsersData = (Button) findViewById(R.id.btnUsersData);
        btnUsersData.setOnClickListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header, navigationView, false);
        navigationView.addHeaderView(header);

        btnRegistration = (Button) header.findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // Handle navigation view item clicks here.
                        int id = item.getItemId();

                        if (id == R.id.nav_search) {
                            Log.d(TAG, "Click nav_search");
                        }
                        return true;
                    }
                }
        );

        Log.d(TAG, "Hello");

        // открываем подключение к БД
        //db = new DB(this);
        //db.open();

    }

    @Override
    public void onClick(View v) {
        // по id определяем кнопку, вызвавшую этот обработчик
        Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
        switch (v.getId()) {
            case R.id.btnDrawerOpener:
                drawerLayout.openDrawer(GravityCompat.START);
                Log.d(TAG, "Click");
                break;

            case R.id.btnUsersData:
                Log.d(TAG, "UsersData");
                Intent intent1 = new Intent(this, UsersDataActivity.class);
                startActivity(intent1);
                break;

            case R.id.btnRegistration:
                Log.d(TAG, "Registration");
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
        }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(someEvent.latitude, someEvent.longtude))
                .title(someEvent.name));

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            //mMap.setMyLocationEnabled(true);

        }else{
            mMap.setMyLocationEnabled(true);
            //uiSettings.setMyLocationButtonEnabled(true);
        }

        //mMap.setMyLocationEnabled(true);
        //uiSettings.setMyLocationButtonEnabled(true);

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //get latlong for corners for specified place
        LatLng one = new LatLng(56.188109, 43.702207);
        LatLng two = new LatLng(56.393887, 44.160886);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //add them to builder
        builder.include(one);

        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.20);

        //set latlong bounds
        mMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);

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

                    if (ContextCompat.checkSelfPermission(this,
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

}
