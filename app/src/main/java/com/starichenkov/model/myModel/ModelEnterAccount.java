package com.starichenkov.model.myModel;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.starichenkov.contracts.ContractEnterAccount;
import com.starichenkov.data.Users;
import com.starichenkov.presenter.CallBacks.CallBackEnterAccount;

public class ModelEnterAccount implements ContractEnterAccount.Model {

    private CallBackEnterAccount callBackEnterAccount;

    private final String TAG = "MyLog";

    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    protected DatabaseReference userRef;

    public ModelEnterAccount(CallBackEnterAccount callBackEnterAccount){
        this.callBackEnterAccount = callBackEnterAccount;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");
    }

    @Override
    public void findUser(final String mail, final String password) {
        Log.d(TAG, "Model findUser");

        userRef.orderByChild("mail").equalTo(mail).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    if(dat.getValue(Users.class).getPassword().equals(password)){
                        Log.d(TAG, "Авторизован");
                        callBackEnterAccount.saveAuthorization(dat.getKey());
                    }
                    callBackEnterAccount.startMainActivity();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }
}
