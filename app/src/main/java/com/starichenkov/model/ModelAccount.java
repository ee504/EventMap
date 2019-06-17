package com.starichenkov.model;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.presenter.CallBackAccount;
import com.starichenkov.presenter.CallBackModel;

import java.util.ArrayList;
import java.util.List;

public class ModelAccount {

    private final String TAG = "MyLog";

    private CallBackAccount callBackAccount;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;

    public ModelAccount(CallBackAccount callBack) {

        this.callBackAccount = callBack;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");
        eventRef = myRef.child("events");

    }

    public void getCurrentUser(String idUser) {
        Log.e(TAG, "Model getCurrentUser()");

        userRef.orderByKey().equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    //Users user = dat.getValue(Users.class);
                callBackAccount.setUser(dataSnapshot.getChildren().iterator().next().getValue(Users.class));
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }

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

    public void deletePhoto(String photoEvent){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference photoRef = storage.getReferenceFromUrl(photoEvent);
        photoRef.delete();
    }

    public void updateUser(String idUser, Users user) {
        userRef.child(idUser).setValue(user);
    }
}
