package com.starichenkov.presenter;

import android.content.Context;
import android.util.Log;

import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.view.CallBackFromDB;
import com.starichenkov.view.IView;

import com.starichenkov.Model.IModel;
import com.starichenkov.Model.Model;

import java.util.List;

public class Presenter implements IPresenter{

    private static final String TAG = "MyLog";
    private IView iView;
    private IModel iModel;

    //private CallBackFromDB mListener;
    //private IView mListener;

    public Presenter(IView iView, String nameActivity){
        this.iView = iView;
        iModel = new Model(iView);
        if (nameActivity == "MapsActivity" || nameActivity ==  "Model") {
            //mListener = (CallBackFromDB) iView;
            //mListener = (iView) iView;
        }
    }


    @Override
    public void createUser(String fio, String mail, String password){

        iModel.createUser(fio, mail, password);

    };

    @Override
    public boolean findUser(String mail, String password){

        iModel.findUser(mail, password);
        return new AccountAuthorization((Context)this.iView).checkAuthorization();

    }

    @Override
    public void createEvent(Events event){
        iModel.createEvent(event);
    }

    @Override
    public void sendEvents(List<Events> events){
        Log.e(TAG, "Presenter sendEvents()");
        //mListener.sendEvents(events);
        iView.sendEvents(events);

    }

    @Override
    public void getAllEvents() {
        Log.e(TAG, "Presenter getAllEvents()");
        iModel.getAllEvents();
    }

    @Override
    public void createBookMark(long idOrganizer, long id){
        iModel.createBookMark(idOrganizer, id);
    }

    @Override
    public void deleteBookMark(int idUser, long id){
        iModel.deleteBookMark(idUser, id);
    }

    @Override
    public void getAllBookmarks(){
        iModel.getAllBookmarks();
    }

    @Override
    public void sendBookMarks(List<BookMarks> bookMarks){
        Log.e(TAG, "Presenter sendBookMarks()");
        //mListener.sendBookMarks(bookMarks);
        iView.sendBookMarks(bookMarks);
    }

    @Override
    public void getEventsFromBookmarks(){
        iModel.getEventsFromBookmarks();
    }

    @Override
    public void deleteAllEvents(){iModel.deleteAllEvents();};
}
