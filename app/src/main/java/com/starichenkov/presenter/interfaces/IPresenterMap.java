package com.starichenkov.presenter.interfaces;

import com.starichenkov.data.BookMarks;

public interface IPresenterMap extends IPresenterEventsList{

    void createBookMark();
    void deleteBookMark();
    boolean checkAuthorization();
    void deleteAuthorization();
}
