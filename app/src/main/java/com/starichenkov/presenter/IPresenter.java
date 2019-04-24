package com.starichenkov.presenter;

import java.util.List;

public interface IPresenter {

    void createUser(String fio, String mail, String password);

    boolean findUser(String mail, String password);
}
