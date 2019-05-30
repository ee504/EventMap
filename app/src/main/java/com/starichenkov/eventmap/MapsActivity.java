package com.starichenkov.eventmap;

import android.Manifest;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.account.EnterAccountActivity;
import com.starichenkov.account.RegistrationActivity;
import com.starichenkov.createEvent.CreateEventActivity;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.presenter.Presenter;
import com.starichenkov.view.CallBackFromDB;
import com.starichenkov.view.IView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends Fragment implements OnMapReadyCallback, OnClickListener, GoogleMap.OnMarkerClickListener, CallBackFromDB, IView {

    private GoogleMap mMap;
    private MapView mapView;
    private static final String TAG = "MyLog";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
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
    private List<Marker> listMarkers;
    private Events currentEvent;
    private List<BookMarks> bookMarks;

    private BottomSheetBehavior bottomSheetBehavior;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.activity_main_view, container, false);
        View view = inflater.inflate(R.layout.activity_main_view, null);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
        //setContentView(R.layout.activity_main_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment
        /*SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/


        presenter = new Presenter(this, getActivity().getLocalClassName());
        presenter.getAllEvents();

        initView(view);
        Log.d(TAG, "Hello");
        return view;

    }

    private void initView(View view) {


        btnDrawerOpener = (Button) view.findViewById(R.id.btnDrawerOpener);
        Log.d(TAG, "btnDrawerOpener:" + btnDrawerOpener);
        btnDrawerOpener.setOnClickListener(this);
        //btnDrawerOpener.setVisibility(View.GONE);

        ibtnDrawerOpener = (ImageButton) view.findViewById(R.id.ibtnDrawerOpener);
        ibtnDrawerOpener.setOnClickListener(this);

        btnUsersData = (Button) view.findViewById(R.id.btnUsersData);
        btnUsersData.setOnClickListener(this);
        //btnUsersData.setVisibility(View.GONE);

        btnFloatingAction = (FloatingActionButton) view.findViewById(R.id.btnFloatingAction);
        btnFloatingAction.setOnClickListener(this);

        drawerLayout = view.findViewById(R.id.drawer_layout);

        navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // Handle navigation view item clicks here.
                        switch (item.getItemId()) {
                            //int id = item.getItemId();
                            //if (id == R.id.nav_search) {

                            case R.id.nav_search:
                                Log.d(TAG, "Click nav_search");
                                break;

                            case R.id.nav_bookmarks:
                                Log.d(TAG, "Click nav_bookmarks");
                                Intent intentBookMarks = new Intent(getActivity().getApplicationContext(), BookMarksList.class);
                                startActivity(intentBookMarks);
                                break;
                        }
                        return true;
                    }
                }
        );

        ibtnBookMark = (ImageButton) view.findViewById(R.id.ibtnBookMark);
        ibtnBookMark.setOnClickListener(this);

        header_authorized = LayoutInflater.from(getActivity()).inflate(R.layout.nav_header_authorized, navigationView, false);
        header_not_authorized = LayoutInflater.from(getActivity()).inflate(R.layout.nav_header_not_authorized, navigationView, false);

        if(new AccountAuthorization(getActivity()).checkAuthorization()) {
            navigationView.addHeaderView(header_authorized);
            presenter.getAllBookmarks();

        }else{
            navigationView.addHeaderView(header_not_authorized);
            ibtnBookMark.setEnabled(false);
        }

        btnRegistration = (Button) header_not_authorized.findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(this);

        btnEnterAccount = (Button) header_not_authorized.findViewById(R.id.btnEnterAccount);
        btnEnterAccount.setOnClickListener(this);

        btnExit = (Button) header_authorized.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);

        btnAccount = (Button) header_authorized.findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(this);

        LinearLayout llBottomSheet = (LinearLayout) view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        imageEvent = (ImageView) view.findViewById(R.id.imageEvent);
        textNameEvent = (TextView) view.findViewById(R.id.textNameEvent);
        textTypeEvent = (TextView) view.findViewById(R.id.textTypeEvent);
        textDateEvent = (TextView) view.findViewById(R.id.textDateEvent);
        textAddressEvent = (TextView) view.findViewById(R.id.textAddressEvent);
        textDescriptionEvent = (TextView) view.findViewById(R.id.textDescriptionEvent);

        listMarkers = new ArrayList<Marker>();

    }

    @Override
    public void onClick(View v) {
        // по id определяем кнопку, вызвавшую этот обработчик
        //Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
        switch (v.getId()) {
            case R.id.btnDrawerOpener:
                //drawerLayout.openDrawer(GravityCompat.START);
                Log.d(TAG, "Click Menu");
                presenter.getAllBookmarks();
                break;

            case R.id.ibtnDrawerOpener:
                drawerLayout.openDrawer(GravityCompat.START);
                Log.d(TAG, "Click Menu");
                break;

            case R.id.btnUsersData:
                Log.d(TAG, "Click btnUsersData");
                Log.d(TAG, "this.getLocalClassName(): " + getActivity().getLocalClassName());
                Intent intentUsersData = new Intent(getActivity(), UsersDataActivity.class);
                startActivity(intentUsersData);
                //View frgm = findViewById(R.id.map);
                //frgm.setClickable(false);
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;

            case R.id.btnFloatingAction:
                if(new AccountAuthorization(getActivity()).checkAuthorization()) {
                    Log.d(TAG, "Click btnFloatingAction");
                    Intent intentCreateEvent = new Intent(getActivity(), CreateEventActivity.class);
                    startActivity(intentCreateEvent);
                }else{
                    Toast toast = Toast.makeText(getActivity(), "Требуется регистрация",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                break;

            case R.id.btnEnterAccount:
                Log.d(TAG, "Click btnEnterAccount");
                Intent intentEnterAccount = new Intent(getActivity(), EnterAccountActivity.class);
                startActivity(intentEnterAccount);
                break;

            case R.id.btnRegistration:
                Log.d(TAG, "Click btnRegistration");
                Intent intentRegistration = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intentRegistration);
                break;

            case R.id.btnAccount:
                Log.d(TAG, "Click btnAccount");
                //Intent intentAccount = new Intent(this, EnterAccountActivity.class);
                //startActivity(intentAccount);
                break;

            case R.id.btnExit:
                Log.d(TAG, "Click btnExit");
                new AccountAuthorization(getActivity()).deleteAuthorization();
                Intent intentExit = new Intent(getActivity(), MapsActivity.class);
                startActivity(intentExit);
                break;

            case R.id.ibtnBookMark:
                Log.d(TAG, "Click ibtnBookMark");
                if(ibtnBookMark.getTag() == this.getString(R.string.ic_bookmark_border_black_24dp)){
                    ibtnBookMark.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bookmark_black_24dp));
                    ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_black_24dp));
                    Toast toast = Toast.makeText(getActivity(), "Закладка сохранена",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM,0,0);
                    toast.show();
                    presenter.createBookMark(new AccountAuthorization(getActivity()).getIdUser(), currentEvent.id);
                    presenter.getAllBookmarks();
                }else if(ibtnBookMark.getTag() == this.getString(R.string.ic_bookmark_black_24dp)){
                    ibtnBookMark.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bookmark_border_black_24dp));
                    ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_border_black_24dp));
                    Toast toast = Toast.makeText(getActivity(), "Закладка удалена",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM,0,0);
                    toast.show();
                    presenter.deleteBookMark(new AccountAuthorization(getActivity()).getIdUser(), currentEvent.id);
                    presenter.getAllBookmarks();
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
    public void onMapReady(GoogleMap googleMap) {//this.mMap = googleMap;};

    //public void onMap(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        mMap = googleMap;
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(),
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
                            getActivity(), R.raw.map_style));

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
    public boolean onMarkerClick(final Marker marker) {
        currentEvent = (Events)marker.getTag();

        if(checkBookMark(currentEvent.id)){
            Log.d(TAG, "checkBookMark(): " + currentEvent.id);
            ibtnBookMark.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bookmark_black_24dp));
            ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_black_24dp));
        }else{
            ibtnBookMark.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bookmark_border_black_24dp));
            ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_border_black_24dp));
        }

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Log.e(TAG, "Marker id: " + currentEvent.id);
        Log.e(TAG, "Marker name: " + currentEvent.nameEvent);

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
        for(Events event : this.events) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(event.latitude, event.longitude)));
            marker.setTag(event);
            listMarkers.add(marker);
        }
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void sendBookMarks(List<BookMarks> bookMarks){
        this.bookMarks = bookMarks;
    }

    public boolean checkBookMark(long idEvent){
        if(bookMarks == null){
            return false;
        }
        for(BookMarks bookMark: bookMarks){
            if(idEvent == bookMark.idEvent){
                return true;
            }
        }
        return false;
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

}
