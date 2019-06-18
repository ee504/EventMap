package com.starichenkov.presenter;

import android.util.Log;

import com.starichenkov.createEvent.CallBackCreateEvent;
import com.starichenkov.data.Events;
import com.starichenkov.model.CreateEventModel;
import com.starichenkov.presenter.interfaces1.IPresenterEvent;
import com.starichenkov.view.interfaces1.IViewCreateEvent;

import java.util.List;

public class PresenterEvent implements IPresenterEvent, CallBackCreateEvent {

    private static final String TAG = "MyLog";

    private IViewCreateEvent iView;
    private CreateEventModel createEventModel;
    private Events currentEvent;

    public PresenterEvent(IViewCreateEvent iView){
        this.iView = iView;
        this.createEventModel = new CreateEventModel(this);
    }

    @Override
    public Events getCurrentEvent(){
        if(currentEvent != null) {
            return currentEvent;
        }else{
            return new Events();
        }
    }

    @Override
    public void createEvent(Events event) {
        if(currentEvent != null){
            event.setId(currentEvent.getId());
            createEventModel.updateEvent(event);
        }
        createEventModel.createEvent(event);
    }

    @Override
    public void getEventById(String IdEvent) {
        createEventModel.getEventById(IdEvent);
    }

    @Override
    public void updateEvent(Events event) {

    }

    @Override
    public void deletePhoto(String photo) {
        createEventModel.deletePhoto(photo);
    }

    @Override
    public void detachView() {
        iView = null;
    }

    @Override
    public void startMainActivity(){
        Log.e(TAG, "PresenterEvent startMainActivity()");
        iView.startMainActivity();
    }

    @Override
    public void setEvents(List<Events> events) {
        this.currentEvent = events.get(0);
        iView.setEvents(events);
    }

}
