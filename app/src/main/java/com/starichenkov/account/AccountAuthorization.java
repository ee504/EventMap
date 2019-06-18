package com.starichenkov.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class AccountAuthorization {

    private SharedPreferences sPref;
    private String namePreference = "Authorization";

    //class for authorization
    public AccountAuthorization(Context con){

        sPref = con.getSharedPreferences("AccountPreference", MODE_PRIVATE);

    }

    //log in
    public void saveAuthorization(String idUser){

        Editor ed = sPref.edit();
        ed.putString(namePreference, idUser);
        ed.commit();

    }

    //log out
    public void deleteAuthorization(){
        Editor ed = sPref.edit();
        ed.putString(namePreference, "0");
        ed.commit();

    }

    public boolean checkAuthorization(){
        if (!sPref.getString(namePreference, "0").equals("0")){
            return true;
        }else{
            return false;
        }

    }

    public String getIdUser(){
        return sPref.getString(namePreference, "0");
    }

}
