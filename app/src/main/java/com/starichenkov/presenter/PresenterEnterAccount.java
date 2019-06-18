package com.starichenkov.presenter;

import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.contracts.ContractEnterAccount;
import com.starichenkov.model.ModelEnterAccount;
import com.starichenkov.presenter.CallBacks.CallBackEnterAccount;

public class PresenterEnterAccount extends PresenterMain implements ContractEnterAccount.Presenter, CallBackEnterAccount {

    private ContractEnterAccount.View iView;
    private ModelEnterAccount model;
    private AccountAuthorization account;

    public PresenterEnterAccount(ContractEnterAccount.View iView, AccountAuthorization account) {
        super(iView);
        this.iView = iView;
        this.account = account;
        model = new ModelEnterAccount(this);
    }

    @Override
    public void findUser(String mail, String password) {
        model.findUser(mail, password);
    }

    @Override
    public void startMainActivity() {
        iView.startMainActivity();
    }

    @Override
    public void saveAuthorization(String key) {
        account.saveAuthorization(key);
    }
}
