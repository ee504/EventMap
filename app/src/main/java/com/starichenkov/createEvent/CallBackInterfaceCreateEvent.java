package com.starichenkov.createEvent;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;
import com.starichenkov.data.Events;

public interface CallBackInterfaceCreateEvent {

    void OpenPlaceAutocomplete();
    void SetEventAddress(Address address, LatLng latLng);
    String getIdEvent();
}
