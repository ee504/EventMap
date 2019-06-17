package com.starichenkov.presenter.interfaces;

import com.starichenkov.data.Users;

public interface IPresenterUserData extends IPresenterCurrentUser {
    void updateUser(String idUser, Users user);
}
