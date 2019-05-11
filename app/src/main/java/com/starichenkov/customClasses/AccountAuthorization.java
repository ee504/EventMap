package com.starichenkov.customClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import static android.content.Context.MODE_PRIVATE;

public class AccountAuthorization {

    private SharedPreferences sPref;
    private String namePreference = "Authorization";
    private int idUser;

    public AccountAuthorization(Context con){

        sPref = con.getSharedPreferences("AccountPreference", MODE_PRIVATE);

    }

    public void saveAuthorization(int idUser){

        this.idUser = idUser;
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
        return this.idUser;
    }



}
