package com.starichenkov.presenter;

import android.content.Context;

import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.model.ModelAccount;
import com.starichenkov.presenter.interfaces1.IPresenterAccount;
import com.starichenkov.view.interfaces1.IViewEvents;

import java.util.ArrayList;
import java.util.List;

public class PresenterAccount implements IPresenterAccount, CallBackAccount {

    private IViewEvents iView;
    private AccountAuthorization accountAuthorization;
    private ModelAccount model;
    private List<Events> events;

    public PresenterAccount(IViewEvents iView, AccountAuthorization accountAuthorization){
        this.iView = iView;
        this.accountAuthorization = accountAuthorization;
        model = new ModelAccount(this);
        events = new ArrayList<>();
    }

    @Override
    public void deleteEvent(Events event) {
        model.deleteEvent(event);
    }

    @Override
    public void getUsersEvents() {
        model.getUserEvents(accountAuthorization.getIdUser());
    }

    @Override
    public void deleteAuthorization() {
        accountAuthorization.deleteAuthorization();
    }

    @Override
    public Events getEventByPosition(int position) {
        return events.get(position);
    }

    @Override
    public void updateUser(Users user) {
        model.updateUser(accountAuthorization.getIdUser(), user);
    }

    @Override
    public void getCurrentUser() {
        model.getCurrentUser(accountAuthorization.getIdUser());
    }

    @Override
    public void setUser(Users user){
        iView.setUser(user);
    }

    @Override
    public void setEvents(List<Events> listEvents) {
        this.events = listEvents;
        iView.setEvents(listEvents);
    }

    @Override
    public void detachView() {
        iView = null;
    }
}
