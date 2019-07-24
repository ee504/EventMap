package com.starichenkov.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.starichenkov.contracts.ContractRegistration;
import com.starichenkov.data.Users;

public class ModelRegistration implements ContractRegistration.Model {

    private final String TAG = "MyLog";

    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    protected DatabaseReference userRef;
    //protected FirebaseMessaging fMessaging;

    public ModelRegistration(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");
    }

    @Override
    public void createUser(String fio, String mail, String password) {
        userRef.push().setValue(new Users(fio, mail, password));
    }
}
