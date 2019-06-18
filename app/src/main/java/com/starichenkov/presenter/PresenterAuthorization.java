package com.starichenkov.presenter;

import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.presenter.interfaces.IPresenterAuthorization;
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
