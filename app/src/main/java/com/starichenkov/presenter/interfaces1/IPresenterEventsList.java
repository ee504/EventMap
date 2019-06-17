package com.starichenkov.presenter.interfaces1;

public interface IPresenterEventsList extends IPresenterCurrentUser{
    void getAllEvents();
    void getAllBookmarks();
    void getEventsFromBookmarks();
}
