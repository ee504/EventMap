package com.starichenkov.presenter;

import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;

import java.util.List;

public interface CallBackModel {

    void setEvents(List<Events> events);
    void setBookMarks(List<BookMarks> bookMarks);
    void setUser(Users user);

    void setEvent(Events event);

    void updateEvent(Events value);

    void deleteEvent(String id);
}
