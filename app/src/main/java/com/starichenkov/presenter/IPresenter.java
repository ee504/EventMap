package com.starichenkov.presenter;

import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.RoomDB.Users;

import java.util.List;

public interface IPresenter {

    void createUser(String fio, String mail, String password);

    boolean findUser(String mail, String password);

    //void createEvent(int idUser, String photoURI, String editNameEvent, String descriptionEvent, String dateEvent, String typeEvent, String addressEvent, double latitude, double longitude);
    void createEvent(Events event);

    void sendEvents(List<Events> events);

    void getAllEvents();

    void createBookMark(BookMarks bookMark);

    void deleteBookMark(BookMarks bookMark);

    void getAllBookmarks();

    void sendBookMarks(List<BookMarks> bookMarks);

    void getEventsFromBookmarks(List<BookMarks> bookMarks);

    void deleteAllEvents();

    void detachView();

    void getUserEvents();

    void getCurrentUser();

    void sendUser(Users user);

    void updateUser(Users user);

    void getEventById(String idEvent);

    void deleteEventById(long id);

    void updateEvent(Events event);

    void deleteEvent(Events events);

    void startMainActivity();

    void deletePhoto(String photoEvent);
}
