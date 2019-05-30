package com.starichenkov.view;

import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;

import java.util.List;

public interface IView {

    void sendEvents(List<Events> events);

    void sendBookMarks(List<BookMarks> bookMarks);

}
