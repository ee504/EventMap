package com.starichenkov.Model;

import android.content.Context;

import com.starichenkov.eventmap.IView;

public class Model implements IModel{

    private DB db;

    public Model(IView iView) {
        this.db = new DB((Context) iView);
        db.open();
    }

    public void createUser(String fio, String mail, String password, String date_birdth){

        db.addUser(fio, mail, password, date_birdth);
        db.close();

    }
}
