package com.starichenkov.presenter;

import com.starichenkov.contracts.ContractRegistration;
import com.starichenkov.model.ModelRegistration;

public class PresenterRegistration extends PresenterMain implements ContractRegistration.Presenter{

    private ModelRegistration model;

    public PresenterRegistration(ContractRegistration.View iView) {
        super(iView);
        model = new ModelRegistration();
    }

    @Override
    public void createUser(String fio, String mail, String password) {
        model.createUser(fio, mail, password);
    }
}
