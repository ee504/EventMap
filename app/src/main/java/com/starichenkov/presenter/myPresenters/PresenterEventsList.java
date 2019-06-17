package com.starichenkov.presenter.myPresenters;

import com.starichenkov.Contracts.ContractEventsList;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.Events;
import com.starichenkov.presenter.CallBacks.CallBackEventsList;
import com.starichenkov.model.myModel.ModelEventsList;

import java.util.List;

public class PresenterEventsList implements ContractEventsList.Presenter, CallBackEventsList {

    private ContractEventsList.View iView;
    private ModelEventsList model;
    private AccountAuthorization account;
    private List<Events> events;



    public PresenterEventsList(ContractEventsList.View iViewMain){
        this.iView = iViewMain;
        model = new ModelEventsList(this);
        account = new AccountAuthorization();
    }

    @Override
    public void getAllEvents() {
        model.getAllEvents();
    }

    @Override
    public void getEventsFromBookmarks() {
        model.getEventsByBookmarks(account.getIdUser());
    }

    @Override
    public String getIdEventByPosition(int position) {
        return events.get(position).getId();
    }

    @Override
    public void setEvents(List<Events> listEvents) {
        this.events = events;
        iView.setEvents(events);
    }

    @Override
    public void detachView() {
        iView = null;
    }
}
