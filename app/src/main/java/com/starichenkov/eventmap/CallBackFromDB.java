package com.starichenkov.eventmap;

import com.starichenkov.RoomDB.Events;

import java.util.List;

public interface CallBackFromDB {

    void sendEvents(List<Events> events);

}
