package com.starichenkov.presenter;

import com.starichenkov.eventmap.IView;

import com.starichenkov.Model.IModel;
import com.starichenkov.Model.Model;

public class Presenter implements IPresenter{

    private static final String TAG = "MyLog";
    private IView iView;
    private IModel iModel;

    public Presenter(IView iView){
        this.iView = iView;
        iModel = new Model(iView);
    }


    @Override
    public void createUser(String fio, String mail, String password, String date_birdth){

        iModel.createUser(fio, mail, password, date_birdth);

    };
}
