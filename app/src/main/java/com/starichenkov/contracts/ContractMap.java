package com.starichenkov.contracts;

import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.model.interfaces.IIModelCurrentUser;
import com.starichenkov.presenter.interfaces.IPresenterAuthorization;
import com.starichenkov.view.interfaces.IViewCurrentUser;

import java.util.List;

public interface ContractMap {
    interface View extends IViewCurrentUser {
        void setEvent(Events event);
        void setCurrentEvent(Events currentEvent);
        void deleteMarker(String id);
    }

    interface Presenter extends IPresenterAuthorization {
        void createBookMark();
        void deleteBookMark();
        void getAllBookmarks();
        boolean checkBookMark(String idEvent);
        void onClickMarker(String idEvent);
    }

    interface Model extends IIModelCurrentUser {
        void createBookMark(BookMarks bookMark);
        void deleteBookMark(BookMarks bookMark);
        void getAllBookmarks(String idUser);
    }
}
