package com.starichenkov.presenter;

import android.content.Context;
import android.location.Address;

import com.starichenkov.RoomDB.Events;
import com.starichenkov.customClasses.AccountAuthorization;
import com.starichenkov.eventmap.IView;

import com.starichenkov.Model.IModel;
import com.starichenkov.Model.Model;

import java.util.List;

public class Presenter implements IPresenter{

    private static final String TAG = "MyLog";
    private IView iView;
    private IModel iModel;

    public Presenter(IView iView){
        this.iView = iView;
        iModel = new Model(iView);
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
    public void createEvent(int idUser, String photoURI, String editNameEvent, String descriptionEvent, String dateEvent, String typeEvent, String addressEvent, double latitude, double longitude){
        iModel.createEvent(idUser, photoURI, editNameEvent, descriptionEvent, dateEvent, typeEvent, addressEvent, latitude, longitude);
    }

    @Override
    public void sendEvents(List<Events> events){

    }
}
