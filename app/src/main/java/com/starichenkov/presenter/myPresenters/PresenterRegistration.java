package com.starichenkov.presenter.myPresenters;

import com.starichenkov.contracts.ContractRegistration;
import com.starichenkov.model.myModel.ModelRegistration;
import com.starichenkov.view.interfaces.IViewMain;

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
