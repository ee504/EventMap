package com.starichenkov.contracts;

import com.starichenkov.data.Events;
import com.starichenkov.presenter.interfaces.IPresenterMain;
import com.starichenkov.view.interfaces.IViewMain;

import java.util.List;

public interface ContractEventsList {
    interface View extends IViewMain {
        void setEvents(List<Events> events);
    }

    interface Presenter extends IPresenterMain{
        void getAllEvents();
        void getEventsFromBookmarks();
        String getIdEventByPosition(int position);
    }

    interface Model {
        void getAllEvents();
        void getEventsByBookmarks(String idUser);
    }
}
