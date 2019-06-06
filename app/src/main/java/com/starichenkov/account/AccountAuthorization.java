package com.starichenkov.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.starichenkov.RoomDB.App;

import static android.content.Context.MODE_PRIVATE;

public class AccountAuthorization {

    private SharedPreferences sPref;
    private String namePreference = "Authorization";

    //private int idUser;
    //private String mailUser;

    public AccountAuthorization(){

        sPref = App.getAppContext().getSharedPreferences("AccountPreference", MODE_PRIVATE);
       // sPref = con.getSharedPreferences(App.getAppContext(), MODE_PRIVATE);

    }

    public void saveAuthorization(int idUser){

        //this.idUser = idUser;
        Editor ed = sPref.edit();
        ed.putInt(namePreference, idUser);
        ed.commit();

    }

    public void deleteAuthorization(){

        Editor ed = sPref.edit();
        ed.putInt(namePreference, 0);
        ed.commit();

    }

    public boolean checkAuthorization(){

        if (sPref.getInt(namePreference, 0) != 0){
            return true;
        }else{
            return false;
        }

    }

    public int getIdUser(){
        return sPref.getInt(namePreference, 0);
    }



}
