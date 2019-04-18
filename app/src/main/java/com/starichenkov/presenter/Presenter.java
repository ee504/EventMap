package com.starichenkov.presenter;

import com.starichenkov.eventmap.IView;

import com.starichenkov.Model.IModel;
import com.starichenkov.Model.Model;

public class Presenter implements IPresenter{

    private static final String TAG = "MyLog";
    //private IView iView;
    private IModel iModel;

    public Presenter(){
        //this.iView = iView;
        iModel = new Model();
    }


    @Override
    public void createUser(String fio, String mail, String password){

        iModel.createUser(fio, mail, password);

    };
}
