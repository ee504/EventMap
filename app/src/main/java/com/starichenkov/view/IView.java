package com.starichenkov.view;

import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.RoomDB.Users;

import java.util.List;

public interface IView {

    void sendEvents(List<Events> events);

    void sendBookMarks(List<BookMarks> bookMarks);

    void detachView();

    void sendUser(Users user);

    void startMainActivity();
}
