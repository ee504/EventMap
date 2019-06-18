package com.starichenkov.presenter;

import com.starichenkov.contracts.ContractChangeAccount;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.Users;
import com.starichenkov.model.ModelChangeAccount;
import com.starichenkov.view.interfaces.IViewCurrentUser;

public class PresenterChangeAccount extends PresenterCurrentUser implements ContractChangeAccount.Presenter/*, CallBackCurrentUser*/ {

    private ModelChangeAccount model;
    private AccountAuthorization account;

    public PresenterChangeAccount(IViewCurrentUser iView, AccountAuthorization account) {
        super(iView, account);
        model = new ModelChangeAccount(this);
        this.account = account;
    }

    @Override
    public void updateUser(Users user) {
        model.updateUser(account.getIdUser(), user);
    }

}
