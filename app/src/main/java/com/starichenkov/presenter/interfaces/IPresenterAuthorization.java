package com.starichenkov.presenter.interfaces;

public interface IPresenterAuthorization extends IPresenterCurrentUser{
    boolean checkAuthorization();
    void deleteAuthorization();
}
