package com.starichenkov.createEvent;

import com.starichenkov.data.Events;

import java.util.List;

public interface CallBackCreateEvent {
    void startMainActivity();
    void setEvents(List<Events> events);
}
