package com.starichenkov.view.interfaces1;

import com.starichenkov.data.Events;
import com.starichenkov.data.Users;

import java.util.List;

public interface IViewEvents extends IViewMain {
    void setEvents(List<Events> events);

    void setUser(Users user);
}
