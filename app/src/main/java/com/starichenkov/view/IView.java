package com.starichenkov.view;

import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;

import java.util.List;

public interface IView {

    void sendEvents(List<Events> events);

    void sendBookMarks(List<BookMarks> bookMarks);

    void detachView();

    void sendUser(Users user);

    void startMainActivity();
}
