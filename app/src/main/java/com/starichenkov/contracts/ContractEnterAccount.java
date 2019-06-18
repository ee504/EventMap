package com.starichenkov.contracts;

import com.starichenkov.data.Events;
import com.starichenkov.presenter.interfaces.IPresenterMain;
import com.starichenkov.view.interfaces.IViewMain;

import java.util.List;

public interface ContractEnterAccount {
    interface View extends IViewMain {
        void startMainActivity();
    }

    interface Presenter extends IPresenterMain {
        void findUser(String mail, String password);
    }

    interface Model {
        void findUser(String mail, String password);
    }
}
