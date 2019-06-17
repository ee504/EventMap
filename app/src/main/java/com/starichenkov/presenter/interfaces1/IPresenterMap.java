package com.starichenkov.presenter.interfaces1;

public interface IPresenterMap extends IPresenterEventsList{

    void createBookMark();
    void deleteBookMark();
    boolean checkAuthorization();
    void deleteAuthorization();
}
