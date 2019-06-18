package com.starichenkov.eventmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.starichenkov.contracts.ContractMap;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.account.AccountActivity;
import com.starichenkov.account.EnterAccountActivity;
import com.starichenkov.account.RegistrationActivity;
import com.starichenkov.createEvent.CreateEventActivity;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.createEvent.TypeEvent;
import com.starichenkov.presenter.PresenterMap;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, OnClickListener, GoogleMap.OnMarkerClickListener, ContractMap.View {

    private GoogleMap mMap;
    private MapView mapView;
    private String TAG;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private ImageButton ibtnZoomIn;
    private ImageButton ibtnZoomOut;
    private ImageButton ibtnLocation;
    private Button btnUsersData;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Button btnRegistration;
    private Button btnEnterAccount;
    private TextView tvNameUser;
    private Button btnAccount;
    private View header_not_authorized;
    private View header_authorized;

    private ImageView imageEvent;
    private TextView textNameEvent;
    private TextView textTypeEvent;
    private TextView textDateEvent;
    private TextView textAddressEvent;
    private TextView textDescriptionEvent;
    private ImageButton ibtnBookMark;
    private ImageView imageDot;

    private FloatingActionButton btnFloatingAction;

    private TypeEvent typeEvent;

    private BottomSheetBehavior bottomSheetBehavior;

    private CallBackInterfaceMap mListener;

    private View locationButton;

    private PresenterMap presenterMap;

    private MyGoogleMap myGoogleMap;

    private MToast mToast;

    private List<Marker> markers;

    private Marker currentMarker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main_view, null);

        TAG = getResources().getString(R.string.TAG);

        Log.d(TAG, "------------------------------------");
        Log.d(TAG, "MapFragment onCreateView()");
        Log.d(TAG, "------------------------------------");

        //create google map
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        myGoogleMap = new MyGoogleMap(getActivity(), mMap);

        presenterMap = new PresenterMap(this, new AccountAuthorization(getActivity()));

        initView(view);

        return view;

    }

    //layout initialization
    private void initView(View view) {

        ibtnZoomIn = (ImageButton) view.findViewById(R.id.ibtnZoomIn);
        ibtnZoomIn.setOnClickListener(this);

        ibtnZoomOut = (ImageButton) view.findViewById(R.id.ibtnZoomOut);
        ibtnZoomOut.setOnClickListener(this);

        ibtnLocation = (ImageButton) view.findViewById(R.id.ibtnLocation);
        ibtnLocation.setOnClickListener(this);

        btnUsersData = (Button) view.findViewById(R.id.btnUsersData);
        btnUsersData.setOnClickListener(this);
        btnUsersData.setVisibility(View.GONE);

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

                            case R.id.nav_search:
                                Log.d(TAG, "Click nav_search");
                                drawerLayout.closeDrawer(GravityCompat.START);
                                mListener.OpenEventsList();
                                break;

                            case R.id.nav_bookmarks:
                                Log.d(TAG, "Click nav_bookmarks");
                                drawerLayout.closeDrawer(GravityCompat.START);
                                if(presenterMap.checkAuthorization()) {
                                    mListener.openBookMarksList();
                                }else{
                                    mToast.showLongCenterMessage("Требуется регистрация");
                                }
                                break;

                            case R.id.nav_exit:
                                Log.d(TAG, "Click nav_exit");
                                drawerLayout.closeDrawer(GravityCompat.START);
                                presenterMap.deleteAuthorization();
                                Intent intentExit = new Intent(getActivity(), MainMapActivity.class);
                                startActivity(intentExit);
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

        btnRegistration = (Button) header_not_authorized.findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(this);

        btnEnterAccount = (Button) header_not_authorized.findViewById(R.id.btnEnterAccount);
        btnEnterAccount.setOnClickListener(this);

        tvNameUser = (TextView) header_authorized.findViewById(R.id.tvNameUser);
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
        imageDot = (ImageView) view.findViewById(R.id.imageDot);

        //check user authorization
        //set right header
        if(presenterMap.checkAuthorization()) {
            navigationView.addHeaderView(header_authorized);
            presenterMap.getCurrentUser();
            presenterMap.getAllBookmarks();

        }else{
            navigationView.addHeaderView(header_not_authorized);
            ibtnBookMark.setEnabled(false);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_exit).setVisible(false);
        }

        //class with event types
        typeEvent = new TypeEvent();
        //make Toast easier
        mToast = new MToast(getActivity());
        //google map marker list
        markers = new ArrayList<Marker>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //zoom google map
            case R.id.ibtnZoomIn:
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            //zoom out google map
            case R.id.ibtnZoomOut:
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
                break;
            //geo location
            case R.id.ibtnLocation:
                locationButton.callOnClick();
                break;
            //for tests
            case R.id.btnUsersData:
                break;
            //create new event(if user is logged in)
            case R.id.btnFloatingAction:
                if(presenterMap.checkAuthorization()) {
                    Intent intentCreateEvent = new Intent(getActivity(), CreateEventActivity.class);
                    startActivity(intentCreateEvent);
                }else{
                    mToast.showLongCenterMessage("Требуется регистрация");
                }
                break;
            //authorization
            case R.id.btnEnterAccount:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intentEnterAccount = new Intent(getActivity(), EnterAccountActivity.class);
                startActivity(intentEnterAccount);
                break;

            case R.id.btnRegistration:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intentRegistration = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intentRegistration);
                break;
            //login account
            case R.id.btnAccount:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intentAccountSecond = new Intent(getActivity(), AccountActivity.class);
                startActivity(intentAccountSecond);
                break;
            //save or delete bookmark(if user is logged in)
            case R.id.ibtnBookMark:
                if(ibtnBookMark.getTag() == this.getString(R.string.ic_bookmark_border_black_24dp)){
                    ibtnBookMark.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bookmark_black_24dp));
                    ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_black_24dp));
                    mToast.showLongBottomMessage("Закладка сохранена");
                    presenterMap.createBookMark();

                }else if(ibtnBookMark.getTag() == this.getString(R.string.ic_bookmark_black_24dp)){
                    ibtnBookMark.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bookmark_border_black_24dp));
                    ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_border_black_24dp));
                    mToast.showLongBottomMessage("Закладка удалена");
                    presenterMap.deleteBookMark();
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
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady()");
        myGoogleMap.onMapReady(googleMap);
        mMap = myGoogleMap.getmMap();

        mMap.setOnMarkerClickListener(this);

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

        //Get standard location button
        locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        // Change the visibility of my location button
        if(locationButton != null)
            locationButton.setVisibility(View.GONE);

    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        //change color
        if(currentMarker != null){
            currentMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        //save current marker
        currentMarker = marker;
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        String idEvent = (String)marker.getTag();
        //is it a bookmark?
        if(presenterMap.checkBookMark(idEvent)){
            ibtnBookMark.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bookmark_black_24dp));
            ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_black_24dp));
        }else{
            ibtnBookMark.setImageDrawable(getActivity().getDrawable(R.drawable.ic_bookmark_border_black_24dp));
            ibtnBookMark.setTag(this.getString(R.string.ic_bookmark_border_black_24dp));
        }

        //get selected event data
        presenterMap.onClickMarker(idEvent);
        return false;
    }

    //show information about the selected event
    @Override
    public void setCurrentEvent(Events currentEvent){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        Picasso.get().load(currentEvent.getPhotoEvent()).placeholder(R.drawable.event_map_logo).error(R.drawable.event_map_logo).into(imageEvent);
        textNameEvent.setText(currentEvent.getNameEvent());
        textTypeEvent.setText(currentEvent.getTypeEvent());
        imageDot.setImageDrawable(typeEvent.getDrawable(getActivity(), currentEvent.getTypeEvent()));
        textDateEvent.setText(currentEvent.getDateEvent());
        textAddressEvent.setText(currentEvent.getAddressEvent());
        textDescriptionEvent.setText(currentEvent.getDescriptionEvent());
    }

    //add new event on map
    @Override
    public void setEvent(Events event) {
        Log.e(TAG, "Map fragment setEvents():");
        //get data idEvent Activity
        String idSelectedEvent = mListener.getSelectedMarker();

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(event.getLatitude(), event.getLongitude())));
        markers.add(marker);
        String idEvent = event.getId();
        marker.setTag(idEvent);

        //open if it was selected in EventList or BookmarkList
        if(idSelectedEvent != null && idSelectedEvent.equals(idEvent)) {
            onMarkerClick(marker);
        }
    }
    //find the marker by id and delete it
    @Override
    public void deleteMarker(String id) {
        int i = 0;
        for(Marker mar : markers){
            if(id.equals(mar.getTag().toString())){
                //mar.remove();
                markers.remove(i);
                break;
            }
            i++;
        }
    }

    /*@Override
    public void setBookMarks(List<BookMarks> bookMarks){
        //this.bookMarks = bookMarks;
    }*/

    //set user fio in the navigationView header
    @Override
    public void setUser(Users user){
        tvNameUser.setText(user.getFio());
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
    public void onDestroy(){
        super.onDestroy();
        detachView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CallBackInterfaceMap) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackInterfaceMap");
        }
    }

    public void openDrawer() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    //detach the view from the presenter
    @Override
    public void detachView() {
        presenterMap.detachView();
    }
}
