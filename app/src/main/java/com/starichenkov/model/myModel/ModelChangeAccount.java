package com.starichenkov.model.myModel;

import com.starichenkov.contracts.ContractChangeAccount;
import com.starichenkov.data.Users;
import com.starichenkov.presenter.CallBacks.CallBackCurrentUser;

public class ModelChangeAccount extends ModelCurrentUser implements ContractChangeAccount.Model {

    public ModelChangeAccount(CallBackCurrentUser callBackCurrentUser) {
        super(callBackCurrentUser);
    }

    @Override
    public void updateUser(String idUser, Users user) {
        userRef.child(idUser).setValue(user);
    }
}
