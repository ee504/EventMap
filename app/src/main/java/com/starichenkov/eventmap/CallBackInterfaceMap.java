package com.starichenkov.eventmap;

import com.starichenkov.data.BookMarks;

public interface CallBackInterfaceMap {

    void openBookMarksList();

    void openMapWithMarker(String idEvent);

    void openDrawer();

    void OpenEventsList();

    String getSelectedMarker();

}
