package com.starichenkov.model.myModel;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.starichenkov.contracts.ContractAccount;
import com.starichenkov.data.Events;
import com.starichenkov.presenter.CallBacks.CallBackAccount;

import java.util.ArrayList;
import java.util.List;

public class ModelAccount extends ModelPhoto implements ContractAccount.Model {

    protected DatabaseReference eventRef;
    CallBackAccount callBackAccount;

    public ModelAccount(CallBackAccount callBackAccount) {
        super(callBackAccount);
        this.callBackAccount = callBackAccount;
        eventRef = myRef.child("events");
    }

    @Override
    public void deleteEvent(Events event) {
        Log.d(TAG, "Model deleteEvent()");

        if(event.getPhotoEvent() != null) {
            deletePhoto(event.getPhotoEvent());
        }
        eventRef.child(event.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid) {
            }
        });
    }

    @Override
    public void getUserEvents(String idUser) {
        Log.e(TAG, "Model getUserEvents()");

        eventRef.orderByChild("idOrganizer").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Events> listEvents = new ArrayList<Events>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listEvents.add(dat.getValue(Events.class));
                }
                callBackAccount.setEvents(listEvents);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }
}
