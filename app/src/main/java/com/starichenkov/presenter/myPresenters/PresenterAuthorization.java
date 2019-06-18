package com.starichenkov.presenter.myPresenters;

import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.model.myModel.ModelCurrentUser;
import com.starichenkov.presenter.interfaces.IPresenterAuthorization;
import com.starichenkov.presenter.interfaces.IPresenterCurrentUser;
import com.starichenkov.view.interfaces.IViewCurrentUser;

public class PresenterAuthorization extends PresenterCurrentUser implements IPresenterAuthorization {

    private AccountAuthorization account;

    public PresenterAuthorization(IViewCurrentUser iView, AccountAuthorization account) {
        super(iView, account);
        this.account = account;
    }

    @Override
    public boolean checkAuthorization() {
        return account.checkAuthorization();
    }

    @Override
    public void deleteAuthorization() {
        account.deleteAuthorization();
    }
}
