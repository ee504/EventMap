package com.starichenkov.presenter;

import android.content.Context;
import android.util.Log;

import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.RoomDB.Users;
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

    public Presenter(IView iView){
        this.iView = iView;
        iModel = new Model(this);
    }


    @Override
    public void createUser(String fio, String mail, String password){

        iModel.createUser(fio, mail, password);

    };

    @Override
    public boolean findUser(String mail, String password){

        iModel.findUser(mail, password);
        return new AccountAuthorization().checkAuthorization();

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
    public void getEventById(long idEvent){iModel.getEventById(idEvent);}

    @Override
    public void sendBookMarks(List<BookMarks> bookMarks){
        Log.e(TAG, "Presenter sendBookMarks()");
        //mListener.sendBookMarks(bookMarks);
        iView.sendBookMarks(bookMarks);
    }

    @Override
    public void deleteEventById(long id){
        iModel.deleteEventById(id);
    }

    @Override
    public void deleteEvent(Events event){
        iModel.deleteEvent(event);
    }

    @Override
    public void updateEvent(Events event){
        iModel.updateEvent(event);
    }

    @Override
    public void sendUser(Users user){
        iView.sendUser(user);
    }

    @Override
    public void getEventsFromBookmarks(){
        iModel.getEventsFromBookmarks();
    }

    @Override
    public void deleteAllEvents(){iModel.deleteAllEvents();}

    @Override
    public void getUserEvents(){iModel.getUserEvents();}

    @Override
    public void getCurrentUser(){iModel.getCurrentUser();}

    @Override
    public void updateUser(Users user){
        iModel.updateUser(user);
    }

    @Override
    public void detachView(){
        iModel.detachView();
        iView = null;
    }
}
