package com.starichenkov.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.presenter.CallBackModel;
import com.starichenkov.presenter.IPresenter;

import java.util.ArrayList;
import java.util.List;

public class mModel {

    private final String TAG = "MyLog";

    private CallBackModel callBackModel;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;
    private DatabaseReference bookMarkRef;

    private StorageReference mStorageRef;

    private List<Events> eventsFromBookMark;

    public mModel(CallBackModel callBackModel) {

        this.callBackModel = callBackModel;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");
        eventRef = myRef.child("events");
        bookMarkRef = myRef.child("bookmarks");

        mStorageRef = FirebaseStorage.getInstance().getReference("photo");

        eventsFromBookMark = new ArrayList<Events>();
    }


    public void getAllEvents() {

        Log.e(TAG, "Model getAllEvents()");

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Events> listEvents = new ArrayList<Events>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listEvents.add(dat.getValue(Events.class));

                }
                callBackModel.setEvents(listEvents);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }


    public void getAllBookmarks(final String idUser){

        Log.e(TAG, "Model getAllBookmarks()");

        bookMarkRef.orderByChild("idOrganizer").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<BookMarks> listBookMarks = new ArrayList<BookMarks>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listBookMarks.add(dat.getValue(BookMarks.class));
                }
                callBackModel.setBookMarks(listBookMarks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });

    }

    public void createBookMark(BookMarks bookMark) {
        Log.d(TAG, "Model createBookMark()");
        DatabaseReference pushedBookMarkRef = bookMarkRef.push();
        bookMark.setId(pushedBookMarkRef.getKey().toString());
        pushedBookMarkRef.setValue(bookMark);
    }

    public void deleteBookMark(BookMarks bookMark) {
        Log.d(TAG, "Model deleteBookMark()");
        bookMarkRef.child(bookMark.getId()).removeValue();
    }

    public void getCurrentUser(String idUser) {

        Log.e(TAG, "Model getCurrentUser()");

        userRef.orderByKey().equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    Users user = dat.getValue(Users.class);
                    callBackModel.setUser(dat.getValue(Users.class));
                    //callBackModel.setUser(dat.getChildren().iterator().next().getValue(Users.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }

    public void getEventsByBookmarks(String idUser) {

        Log.e(TAG, "Model getEventsByBookmarks()");

        bookMarkRef.orderByChild("idOrganizer").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "size: " + (int) dataSnapshot.getChildrenCount());
                final int sizeDataSnapshot = (int) dataSnapshot.getChildrenCount();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    //Log.d(TAG, "dat: " + dat);
                    eventRef.orderByKey().equalTo(dat.getValue(BookMarks.class).getIdEvent()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot dat : dataSnapshot.getChildren()) {
                                //Log.d(TAG, "dat: " + dat);
                                eventsFromBookMark.add(dat.getValue(Events.class));
                                if(eventsFromBookMark.size() == sizeDataSnapshot){
                                    callBackModel.setEvents(eventsFromBookMark);
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
                callBackModel.setEvents(eventsFromBookMark);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });

    }

    private void addEventFromBookMarks(Events event){
        eventsFromBookMark.add(event);
    }
}
