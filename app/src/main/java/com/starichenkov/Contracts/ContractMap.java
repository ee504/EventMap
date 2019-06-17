package com.starichenkov.Contracts;

import com.starichenkov.data.Events;
import com.starichenkov.presenter.interfaces.IPresenterCurrentUser;
import com.starichenkov.presenter.interfaces.IPresenterMain;

import java.util.List;

public interface ContractMap {
    interface View {
        void setEvents(List<Events> events);
    }

    interface Presenter extends IPresenterCurrentUser {
        void createBookMark();
        void deleteBookMark();
        void getAllBookmarks();
        boolean checkAuthorization();
        boolean checkBookMark(String idEvent);
        void onClickMarker(String idEvent);
    }

    interface Model {
        void getAllEvents();
        void getEventsByBookmarks(String idUser);
    }
}
