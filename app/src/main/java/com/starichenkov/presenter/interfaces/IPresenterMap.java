package com.starichenkov.presenter.interfaces;

public interface IPresenterMap extends IPresenterCurrentUser {
    void createBookMark();
    void deleteBookMark();
    void getAllBookmarks();
    boolean checkAuthorization();
    boolean checkBookMark(String idEvent);
    void onClickMarker(String idEvent);
}
