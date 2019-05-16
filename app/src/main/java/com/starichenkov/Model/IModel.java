package com.starichenkov.Model;

import android.location.Address;

public interface IModel {

    void createUser(String fio, String mail, String password);

    void findUser(String mail, String password);

    void createEvent(int idUser, String photoURI, String editNameEvent, String descriptionEvent, String dateEvent, String typeEvent, String addressEvent, double latitude, double longitude);

    void getAllEvents();
}
