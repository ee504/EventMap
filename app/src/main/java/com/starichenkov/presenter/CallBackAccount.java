package com.starichenkov.presenter;

import com.starichenkov.data.Events;
import com.starichenkov.data.Users;

import java.util.List;

public interface CallBackAccount {
    void setUser(Users value);

    void setEvents(List<Events> listEvents);
}
