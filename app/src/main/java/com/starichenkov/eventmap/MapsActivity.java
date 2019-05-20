package com.starichenkov.eventmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.starichenkov.RoomDB.App;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.RoomDB.Users;
import com.starichenkov.customClasses.AccountAuthorization;
import com.starichenkov.customClasses.SomeEvet;
import com.starichenkov.presenter.Presenter;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity  implements OnMapReadyCallback, OnClickListener, GoogleMap.OnMarkerClickListener, CallBackFromDB, IView {

    private GoogleMap mMap;
    private static final String TAG = "MyLog";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private SomeEvet someEvent = new SomeEvet();
    private Button btnDrawerOpener;
    private ImageButton ibtnDrawerOpener;
    private Button btnUsersData;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Presenter presenter;

    private Button btnRegistration;
    private Button btnEnterAccount;
    private Button btnAccount;
    private Button btnExit;
    private View header_not_authorized;
    private View header_authorized;

    private ImageView imageEvent;
    private TextView textNameEvent;
    private TextView textTypeEvent;
    private TextView textDateEvent;
    private TextView textAddressEvent;
    private TextView textDescriptionEvent;
    private ImageButton ibtnBookMark;

    private FloatingActionButton btnFloatingAction;

    private List<Events> events;
    private Events currentEvent;

    private BottomSheetBehavior bottomSheetBehavior;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
        setContentView(R.layout.activity_main_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initView();
        presenter = new Presenter(this, this.getLocalClassName());
        presenter.getAllEvents();

        Log.d(TAG, "Hello");

    }

    private void initView() {

        btnDrawerOpener = (Button) findViewById(R.id.btnDrawerOpener);
        btnDrawerOpener.setOnClickListener(this);
        //btnDrawerOpener.setVisibility(View.GONE);

        ibtnDrawerOpener = (ImageButton) findViewById(R.id.ibtnDrawerOpener);
        ibtnDrawerOpener.setOnClickListener(this);

        btnUsersData = (Button) findViewById(R.id.btnUsersData);
        btnUsersData.setOnClickListener(this);
        //btnUsersData.setVisibility(View.GONE);

        btnFloatingAction = (FloatingActionButton) findViewById(R.id.btnFloatingAction);
        btnFloatingAction.setOnClickListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
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


        header_authorized = LayoutInflater.from(this).inflate(R.layout.nav_header_authorized, navigationView, false);
        header_not_authorized = LayoutInflater.from(this).inflate(R.layout.nav_header_not_authorized, navigationView, false);

        if(new AccountAuthorization(this).checkAuthorization()) {
            navigationView.addHeaderView(header_authorized);
        }else{
            navigationView.addHeaderView(header_not_authorized);
        }

        btnRegistration = (Button) header_not_authorized.findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(this);

        btnEnterAccount = (Button) header_not_authorized.findViewById(R.id.btnEnterAccount);
        btnEnterAccount.setOnClickListener(this);

        btnExit = (Button) header_authorized.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);

        btnAccount = (Button) header_authorized.findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);

        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        imageEvent = (ImageView) findViewById(R.id.imageEvent);
        textNameEvent = (TextView) findViewById(R.id.textNameEvent);
        textTypeEvent = (TextView) findViewById(R.id.textTypeEvent);
        textDateEvent = (TextView) findViewById(R.id.textDateEvent);
        textAddressEvent = (TextView) findViewById(R.id.textAddressEvent);
        textDescriptionEvent = (TextView) findViewById(R.id.textDescriptionEvent);

        ibtnBookMark = (ImageButton) findViewById(R.id.ibtnBookMark);
        ibtnBookMark.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        // по id определяем кнопку, вызвавшую этот обработчик
        //Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
        switch (v.getId()) {
            case R.id.btnDrawerOpener:
                drawerLayout.openDrawer(GravityCompat.START);
                Log.d(TAG, "Click Menu");
                break;

            case R.id.ibtnDrawerOpener:
                drawerLayout.openDrawer(GravityCompat.START);
                Log.d(TAG, "Click Menu");
                break;

            case R.id.btnUsersData:
                Log.d(TAG, "Click btnUsersData");
                Log.d(TAG, "this.getLocalClassName(): " + this.getLocalClassName());
                Intent intentUsersData = new Intent(this, UsersDataActivity.class);
                startActivity(intentUsersData);
                //View frgm = findViewById(R.id.map);
                //frgm.setClickable(false);
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;

            case R.id.btnFloatingAction:
                if(new AccountAuthorization(this).checkAuthorization()) {
                    Log.d(TAG, "Click btnFloatingAction");
                    Intent intentCreateEvent = new Intent(this, CreateEventActivity.class);
                    startActivity(intentCreateEvent);
                }else{
                    Toast toast = Toast.makeText(this, "Требуется регистрация",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                break;

            case R.id.btnEnterAccount:
                Log.d(TAG, "Click btnEnterAccount");
                Intent intentEnterAccount = new Intent(this, EnterAccountActivity.class);
                startActivity(intentEnterAccount);
                break;

            case R.id.btnRegistration:
                Log.d(TAG, "Click btnRegistration");
                Intent intentRegistration = new Intent(this, RegistrationActivity.class);
                startActivity(intentRegistration);
                break;

            case R.id.btnAccount:
                Log.d(TAG, "Click btnAccount");
                //Intent intentAccount = new Intent(this, EnterAccountActivity.class);
                //startActivity(intentAccount);
                break;

            case R.id.btnExit:
                Log.d(TAG, "Click btnExit");
                new AccountAuthorization(this).deleteAuthorization();
                Intent intentExit = new Intent(this, MapsActivity.class);
                startActivity(intentExit);
                break;

            case R.id.ibtnBookMark:
                Log.d(TAG, "Click ibtnBookMark");
                if(ibtnBookMark.getTag() == this.getString(R.string.ic_bookmark_border_black_24dp)){
                    ibtnBookMark.setImageDrawable(this.getDrawable(R.drawable.ic_bookmark_black_24dp));
                    ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_black_24dp));
                    Toast toast = Toast.makeText(this, "Закладка сохранена",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM,0,0);
                    toast.show();
                    //presenter.createBookMark(currentEvent.idOrganizer, currentEvent.id);
                }else if(ibtnBookMark.getTag() == this.getString(R.string.ic_bookmark_black_24dp)){
                    ibtnBookMark.setImageDrawable(this.getDrawable(R.drawable.ic_bookmark_border_black_24dp));
                    ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_border_black_24dp));
                    Toast toast = Toast.makeText(this, "Закладка удалена",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM,0,0);
                    toast.show();
                }
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
    public void onMapReady(GoogleMap googleMap) {this.mMap = googleMap;};

    public void onMap(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        mMap = googleMap;
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

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

        //MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //View zoomControls = mapFragment.getView().findViewById(0x1);

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

        /*App.getInstance().getDatabase().eventsDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Events>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // add it to a CompositeDisposable
                    }
                    @Override
                    public void onSuccess(List<Events> events) {
                        Log.d(TAG, "onSuccess");
                        setEvents(events);
                        for(Events event : events){
                            Marker  marker  = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(event.latitude, event.longitude))
                                    .title(event.nameEvent));
                            marker.setTag(event);
                        }

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Some error");
                        Log.d(TAG, e.getMessage());
                    }
                });*/

        Log.e(TAG, "OnMapReady for");
        for(Events event : events) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(event.latitude, event.longitude)));
                    //.title(event.nameEvent));
            marker.setTag(event);
        }
        mMap.setOnMarkerClickListener(this);

    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Log.e(TAG, "Marker: " + ((Events)marker.getTag()).nameEvent);
        Log.e(TAG, "Marker description: " + ((Events)marker.getTag()).descriptionEvent);
        currentEvent = (Events)marker.getTag();
        imageEvent.setImageURI(Uri.parse(currentEvent.photoEvent));
        textNameEvent.setText(currentEvent.nameEvent);
        textTypeEvent.setText(currentEvent.typeEvent);
        textDateEvent.setText(currentEvent.dateEvent);
        textAddressEvent.setText(currentEvent.addressEvent);
        textDescriptionEvent.setText(currentEvent.descriptionEvent);


        return false;
    }

    @Override
    public void sendEvents(List<Events> events){
        this.events = events;
        onMap(mMap);
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
