package com.starichenkov.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class AccountAuthorization {

    private SharedPreferences sPref;
    private String namePreference = "Authorization";
    private final String TAG = "MyLog";

    //private int idUser;
    //private String mailUser;

    public AccountAuthorization(Context con){

        sPref = con.getSharedPreferences("AccountPreference", MODE_PRIVATE);
       // sPref = con.getSharedPreferences(App.getAppContext(), MODE_PRIVATE);

    }

    public void saveAuthorization(String idUser){

        //this.idUser = idUser;
        Editor ed = sPref.edit();
        ed.putString(namePreference, idUser);
        ed.commit();

    }

    public void deleteAuthorization(){
        Log.e(TAG, "deleteAuthorization");
        Editor ed = sPref.edit();
        ed.putString(namePreference, "0");
        ed.commit();

    }

    public boolean checkAuthorization(){
        Log.e(TAG, "sPref.getString(namePreference, \"0\") " + sPref.getString(namePreference, "0"));
        if (!sPref.getString(namePreference, "0").equals("0")){
            Log.e(TAG, "AccountAuthorization return true");
            return true;
        }else{
            Log.e(TAG, "AccountAuthorization return false");
            return false;
        }

    }

    public String getIdUser(){
        return sPref.getString(namePreference, "0");
    }

}
