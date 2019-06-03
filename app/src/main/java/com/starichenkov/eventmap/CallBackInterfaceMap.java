package com.starichenkov.eventmap;

public interface CallBackInterfaceMap {

    void openBookMarksList();
    void openMapWithMarker(long idEvent);

    void getAllBookmarks();

    void createBookMark(int idUser, long id);

    void deleteBookMark(int idUser, long id);

    void getAllEvents();
}
