package com.starichenkov.contracts;

import com.starichenkov.data.Events;
import com.starichenkov.model.interfaces.IModelPhoto;
import com.starichenkov.presenter.interfaces.IPresenterAuthorization;
import com.starichenkov.view.interfaces.IViewCurrentUser;

import java.util.List;

public interface ContractAccount {
    interface View extends IViewCurrentUser {
        void setEvents(List<Events> events);
    }

    interface Presenter extends IPresenterAuthorization {
        void deleteEvent(Events event);
        void getUsersEvents();
        Events getEventByPosition(int position);
    }

    interface Model extends IModelPhoto{
        void deleteEvent(Events event);
        void getUserEvents(String idUser);
    }
}
