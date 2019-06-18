package com.starichenkov.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.starichenkov.contracts.ContractRegistration;
import com.starichenkov.data.Users;

public class ModelRegistration implements ContractRegistration.Model {

    private final String TAG = "MyLog";

    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    protected DatabaseReference userRef;

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
