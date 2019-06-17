package com.starichenkov.presenter.interfaces1;

import com.starichenkov.data.Users;

public interface IPresenterUserData extends IPresenterCurrentUser {
    void updateUser(String idUser, Users user);
}
