package com.starichenkov.presenter.CallBacks;

import com.starichenkov.data.Events;

import java.util.List;

public interface CallBackAccount extends CallBackCurrentUser {
    void setEvents(List<Events> events);
}
