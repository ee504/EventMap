package com.starichenkov.eventmap;

import android.net.Uri;

import com.starichenkov.RoomDB.BookMarks;

public interface CallBackInterfaceMap {

    void openBookMarksList();
    void openMapWithMarker(int position);

    void getAllBookmarks();

    void createBookMark(BookMarks bookMark);

    void deleteBookMark(BookMarks bookMark);

    void getAllEvents();

    void getEventsFromBookmarks();

    void openDrawer();

    void OpenEventsList();

    void getEvents();

    void setCurrentFragment(String currentFragment);

    void getSelectedMarker();

    void filter(String query);

    void back();

    void getCurrentUser();

    void openImageFullScreen(String url);

    void openLoadScreen();
}
