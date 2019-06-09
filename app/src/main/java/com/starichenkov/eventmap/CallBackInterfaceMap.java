package com.starichenkov.eventmap;

public interface CallBackInterfaceMap {

    void openBookMarksList();
    void openMapWithMarker(int position);

    void getAllBookmarks();

    void createBookMark(int idUser, long id);

    void deleteBookMark(int idUser, long id);

    void getAllEvents();

    void getEventsFromBookmarks();

    void openDrawer();

    void OpenEventsList();

    void getEvents();

    void setCurrentFragment(String currentFragment);

    void getSelectedMarker();

    void filter(String query);

    void back();
}
