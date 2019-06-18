package com.starichenkov.view.interfaces1;

import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;

import java.util.List;

public interface IViewMap extends IViewEvents {
    void setCurrentUser(Users user);
    void setBookMarks(List<BookMarks> bookMarks);
    void setCurrentEvent(Events event);

    void setEvent(Events event);

    void deleteMarker(String id);
}
