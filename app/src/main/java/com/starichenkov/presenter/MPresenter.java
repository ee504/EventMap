package com.starichenkov.presenter;

import com.starichenkov.data.BookMarks;
import com.starichenkov.view.interfaces.IViewMain;

public class MPresenter {

    IViewMain iView;

    public MPresenter(IViewMain iView){
        this.iView = iView;
    }

    public boolean checkAuthorization() {
        return true;
    }

    public void getCurrentUser() {
    }

    public void getAllBookmarks() {
    }

    public void createBookMark(BookMarks bookMarks) {
    }

    public void deleteBookMark(BookMarks bm) {
    }

    public void setCurrentFragment(String nameFragment) {
    }

    public void getAllEvents() {
    }
}
