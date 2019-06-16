package com.starichenkov.view.interfaces;

import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.view.interfaces.IViewMain;

import java.util.List;

public interface IViewEvents extends IViewMain {
    void setEvents(List<Events> events);

}
