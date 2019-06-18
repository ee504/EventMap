package com.starichenkov.model.myModel;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.starichenkov.data.Users;
import com.starichenkov.model.interfaces.IIModelCurrentUser;
import com.starichenkov.presenter.CallBacks.CallBackCurrentUser;

public class ModelCurrentUser implements IIModelCurrentUser {

    protected final String TAG = "MyLog";
    private CallBackCurrentUser callBackCurrentUser;
    protected FirebaseDatabase database;
    protected DatabaseReference myRef;
    protected DatabaseReference userRef;

    public ModelCurrentUser(CallBackCurrentUser callBackCurrentUser){
        this.callBackCurrentUser = callBackCurrentUser;
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");

    }

    @Override
    public void getCurrentUser(String idUser) {
        Log.e(TAG, "Model getCurrentUser()");

        userRef.orderByKey().equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    Users user = dat.getValue(Users.class);
                    callBackCurrentUser.setUser(dat.getValue(Users.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }
}
