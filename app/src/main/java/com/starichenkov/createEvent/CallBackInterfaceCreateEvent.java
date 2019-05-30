package com.starichenkov.createEvent;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

public interface CallBackInterfaceCreateEvent {

    void OpenPlaceAutocomplete();
    void SetEventAddress(Address address, LatLng latLng);

}
