package com.starichenkov.presenter;

import android.location.Address;
import android.text.Editable;

import com.starichenkov.RoomDB.Events;

import java.util.List;

public interface IPresenter {

    void createUser(String fio, String mail, String password);

    boolean findUser(String mail, String password);

    void createEvent(int idUser, String photoURI, String editNameEvent, String descriptionEvent, String dateEvent, String typeEvent, String addressEvent, double latitude, double longitude);

    void sendEvents(List<Events> events);
}
