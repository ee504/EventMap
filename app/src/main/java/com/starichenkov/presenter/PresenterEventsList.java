package com.starichenkov.presenter;

import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.model.mModel;
import com.starichenkov.presenter.interfaces.IPresenterEventsList;
import com.starichenkov.view.interfaces.IViewEvents;
import com.starichenkov.view.interfaces.IViewMap;

import java.util.List;

public class PresenterEventsList implements IPresenterEventsList, CallBackModel {

    private IViewEvents iView;
    private mModel model;
    private AccountAuthorization account;
    private List<Events> events;

    public PresenterEventsList(IViewEvents iViewMain){
        this.iView = iViewMain;
        model = new mModel(this);
        account = new AccountAuthorization();

    }

    @Override
    public void getAllEvents() {
        model.getAllEvents();
    }

    @Override
    public void getAllBookmarks() {
        model.getAllBookmarks(account.getIdUser());
    }

    @Override
    public void getCurrentUser() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void setEvents(List<Events> events) {
        this.events = events;
        iView.setEvents(events);
    }

    @Override
    public void setBookMarks(List<BookMarks> bookMarks) {

    }

    @Override
    public void setUser(Users user) {

    }

    public String getIdEventByPosition(int position) {
        return events.get(position).getId();
    }
}
