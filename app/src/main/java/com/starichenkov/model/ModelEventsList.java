package com.starichenkov.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.starichenkov.contracts.ContractEventsList;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.presenter.CallBacks.CallBackEventsList;

import java.util.ArrayList;
import java.util.List;

public class ModelEventsList implements ContractEventsList.Model {

    private final String TAG = "MyLog";

    private CallBackEventsList callBackEventsList;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference eventRef;
    private DatabaseReference bookMarkRef;

    private List<Events> eventsFromBookMark;

    public ModelEventsList(CallBackEventsList callBackEventsList){
        this.callBackEventsList = callBackEventsList;
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        eventRef = myRef.child("events");
        bookMarkRef = myRef.child("bookmarks");
        eventsFromBookMark = new ArrayList<Events>();

    }

    @Override
    public void getAllEvents() {

        Log.e(TAG, "ModelEventsList getAllEvents()");

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Events> listEvents = new ArrayList<Events>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listEvents.add(dat.getValue(Events.class));

                }
                callBackEventsList.setEvents(listEvents);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void getEventsByBookmarks(String idUser) {

        Log.e(TAG, "Model getEventsByBookmarks()");

        bookMarkRef.orderByChild("idOrganizer").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int sizeDataSnapshot = (int) dataSnapshot.getChildrenCount();
                Log.d(TAG, "sizeDataSnapshot: " + sizeDataSnapshot);
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    eventRef.orderByKey().equalTo(dat.getValue(BookMarks.class).getIdEvent()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, "dataSnapshot: " + dataSnapshot);
                            for(DataSnapshot dat : dataSnapshot.getChildren()) {
                                eventsFromBookMark.add(dat.getValue(Events.class));
                                if(eventsFromBookMark.size() == sizeDataSnapshot){
                                    callBackEventsList.setEvents(eventsFromBookMark);
                                    eventsFromBookMark = null;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });

    }
}
