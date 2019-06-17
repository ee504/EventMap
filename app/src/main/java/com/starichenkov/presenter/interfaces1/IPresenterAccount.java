package com.starichenkov.presenter.interfaces1;

import com.starichenkov.data.Events;
import com.starichenkov.data.Users;

public interface IPresenterAccount extends IPresenterCurrentUser{
    void deleteEvent(Events event);
    void getUsersEvents();

    void deleteAuthorization();

    Events getEventByPosition(int position);

    void updateUser(Users users);
}
