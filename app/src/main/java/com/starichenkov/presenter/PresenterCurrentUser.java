package com.starichenkov.presenter;

import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.Users;
import com.starichenkov.model.ModelCurrentUser;
import com.starichenkov.presenter.CallBacks.CallBackCurrentUser;
import com.starichenkov.presenter.interfaces.IPresenterCurrentUser;
import com.starichenkov.view.interfaces.IViewCurrentUser;

public class PresenterCurrentUser extends PresenterMain implements IPresenterCurrentUser, CallBackCurrentUser {

    private IViewCurrentUser iView;
    private ModelCurrentUser model;
    private AccountAuthorization account;

    public PresenterCurrentUser(IViewCurrentUser iView, AccountAuthorization account) {
        super(iView);
        this.iView = iView;
        model = new ModelCurrentUser(this);
        this.account = account;
    }

    @Override
    public void getCurrentUser() {
        model.getCurrentUser(account.getIdUser());
    }

    @Override
    public void setUser(Users user) {
        iView.setUser(user);
    }

}
