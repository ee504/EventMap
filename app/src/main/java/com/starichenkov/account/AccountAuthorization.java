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

    public void saveAuthorization(String idUser){

        //this.idUser = idUser;
        Editor ed = sPref.edit();
        ed.putString(namePreference, idUser);
        ed.commit();

    }

    public void deleteAuthorization(){

        Editor ed = sPref.edit();
        ed.putString(namePreference, "0");
        ed.commit();

    }

    public boolean checkAuthorization(){

        if (sPref.getString(namePreference, "0") != "0"){
            return true;
        }else{
            return false;
        }

    }

    public String getIdUser(){
        return sPref.getString(namePreference, "0");
    }



}
