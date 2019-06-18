package com.starichenkov.contracts;

import com.starichenkov.presenter.interfaces.IPresenterMain;
import com.starichenkov.view.interfaces.IViewMain;

public interface ContractRegistration {
    interface View extends IViewMain {
    }

    interface Presenter extends IPresenterMain {
        void createUser(String fio, String mail, String password);
    }

    interface Model {
        void createUser(String fio, String mail, String password);
    }
}
