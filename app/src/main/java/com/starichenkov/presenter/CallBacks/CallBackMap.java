package com.starichenkov.presenter.CallBacks;

import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;

import java.util.List;

public interface CallBackMap extends CallBackCurrentUser {


    void setEvent(Events value);

    void updateEvent(Events value);

    void deleteEvent(String id);

    void setBookMarks(List<BookMarks> listBookMarks);
}
