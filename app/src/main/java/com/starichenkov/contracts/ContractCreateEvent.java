package com.starichenkov.contracts;

import com.starichenkov.data.Events;
import com.starichenkov.model.interfaces.IModelPhoto;
import com.starichenkov.presenter.interfaces.IPresenterMain;
import com.starichenkov.view.interfaces.IViewMain;

public interface ContractCreateEvent {
    interface View extends IViewMain {
        void setCurrentEvent(Events event);
    }

    interface Presenter extends IPresenterMain {
        void createEvent(Events event);
        void getEventById(String IdEvent);
        void deletePhoto(String photo);
        Events getCurrentEvent();
    }

    interface Model extends IModelPhoto {
        void updateEvent(Events event);
        void createEvent(Events event);
        void getEventById(String IdEvent);
    }
}
