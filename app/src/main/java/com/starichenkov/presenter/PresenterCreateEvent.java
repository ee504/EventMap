package com.starichenkov.presenter;

import com.starichenkov.contracts.ContractCreateEvent;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.model.ModelCreateEvent;
import com.starichenkov.presenter.CallBacks.CallBackCreateEvent;

public class PresenterCreateEvent extends PresenterMain implements ContractCreateEvent.Presenter, CallBackCreateEvent {

    private static final String TAG = "MyLog";

    private ContractCreateEvent.View iView;
    private ModelCreateEvent model;
    private Events currentEvent;

    public PresenterCreateEvent(ContractCreateEvent.View iView) {
        super(iView);
        this.iView = iView;
        model = new ModelCreateEvent(this);
    }

    @Override
    public void createEvent(Events event) {
        if(currentEvent != null){
            event.setId(currentEvent.getId());
            model.updateEvent(event);
        }
        model.createEvent(event);
    }

    @Override
    public void getEventById(String IdEvent) {
        model.getEventById(IdEvent);
    }

    @Override
    public void deletePhoto(String photo) {
        model.deletePhoto(photo);
    }

    @Override
    public Events getCurrentEvent() {
        if(currentEvent != null) {
            return currentEvent;
        }else{
            return new Events();
        }
    }

    @Override
    public void setCurrentEvent(Events event) {
        this.currentEvent = event;
        iView.setCurrentEvent(event);
    }

    @Override
    public void setUser(Users user) {
    }
}
