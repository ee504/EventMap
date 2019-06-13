package com.starichenkov.Model;

import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.RoomDB.Users;

import java.util.List;

public interface IModel {

    void createUser(String fio, String mail, String password);

    void updateUser(Users user);

    void findUser(String mail, String password);

    //void createEvent(int idUser, String photoURI, String photoEventFullSize, String editNameEvent, String descriptionEvent, String dateEvent, String typeEvent, String addressEvent, double latitude, double longitude);
    void createEvent(Events event);

    void getAllEvents();

    void createBookMark(BookMarks bookMark);

    void deleteBookMark(BookMarks bookMark);

    void getAllBookmarks();

    void getEventsFromBookmarks(List<BookMarks> bookMarks);

    void deleteAllEvents();

    void detachView();

    void getUserEvents();

    void getCurrentUser();

    void getEventById(String idEvent);

    void deleteEventById(long id);

    void updateEvent(Events event);

    void deleteEvent(Events event);

    void deletePhoto(String photoEvent);
}
