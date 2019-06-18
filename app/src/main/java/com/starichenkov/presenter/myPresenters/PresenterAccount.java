package com.starichenkov.presenter.myPresenters;

import com.starichenkov.contracts.ContractAccount;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.Events;
import com.starichenkov.model.myModel.ModelAccount;
import com.starichenkov.presenter.CallBacks.CallBackAccount;

import java.util.List;

public class PresenterAccount extends PresenterAuthorization implements ContractAccount.Presenter, CallBackAccount {

    private ContractAccount.View iView;
    private AccountAuthorization account;
    private ModelAccount model;
    private List<Events> events;

    public PresenterAccount(ContractAccount.View iView, AccountAuthorization account) {
        super(iView, account);
        this.iView = iView;
        this.account = account;
        model = new ModelAccount(this);
    }

    @Override
    public void deleteEvent(Events event) {
        model.deleteEvent(event);
    }

    @Override
    public void getUsersEvents() {
        model.getUserEvents(account.getIdUser());
    }

    @Override
    public Events getEventByPosition(int position) {
        return events.get(position);
    }

    @Override
    public void setEvents(List<Events> events) {
        this.events = events;
        iView.setEvents(this.events);
    }
}
