package com.starichenkov.contracts;

import com.starichenkov.data.Users;
import com.starichenkov.model.interfaces.IIModelCurrentUser;
import com.starichenkov.presenter.interfaces.IPresenterCurrentUser;
import com.starichenkov.view.interfaces.IViewCurrentUser;

public interface ContractChangeAccount {
    interface View extends IViewCurrentUser {
    }

    interface Presenter extends IPresenterCurrentUser {
        void updateUser(Users user);
    }

    interface Model extends IIModelCurrentUser {
        void updateUser(String idUser, Users user);
    }
}
