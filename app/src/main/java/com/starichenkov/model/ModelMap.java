package com.starichenkov.model;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.starichenkov.contracts.ContractMap;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.presenter.CallBacks.CallBackMap;

import java.util.ArrayList;
import java.util.List;

public class ModelMap extends ModelCurrentUser implements ContractMap.Model{

    //private final String TAG = "MyLog";

    private CallBackMap callBackMap;

    private DatabaseReference eventRef;
    private DatabaseReference bookMarkRef;


    public ModelMap(CallBackMap callBackMap){
        super(callBackMap);
        this.callBackMap = callBackMap;
        eventRef = myRef.child("events");
        bookMarkRef = myRef.child("bookmarks");

        setChildListener(eventRef);



    }

    private void setChildListener(DatabaseReference eventRef) {
        eventRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                callBackMap.setEvent(dataSnapshot.getValue(Events.class));

                //callBackMap.setEvent(new Events());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                callBackMap.updateEvent(dataSnapshot.getValue(Events.class));
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                callBackMap.deleteEvent(dataSnapshot.getValue(Events.class).getId());
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void createBookMark(BookMarks bookMark) {
        Log.d(TAG, "Model createBookMark()");
        DatabaseReference pushedBookMarkRef = bookMarkRef.push();
        bookMark.setId(pushedBookMarkRef.getKey().toString());
        pushedBookMarkRef.setValue(bookMark);
    }

    @Override
    public void deleteBookMark(BookMarks bookMark) {
        Log.d(TAG, "Model deleteBookMark()");
        bookMarkRef.child(bookMark.getId()).removeValue();
    }

    @Override
    public void getAllBookmarks(final String idUser) {

        Log.e(TAG, "Model getAllBookmarks()");

        bookMarkRef.orderByChild("idOrganizer").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<BookMarks> listBookMarks = new ArrayList<BookMarks>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listBookMarks.add(dat.getValue(BookMarks.class));
                }
                callBackMap.setBookMarks(listBookMarks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });

    }

    /*@Override
    public void getCurrentUser(String idUser) {
        Log.e(TAG, "Model getCurrentUser()");

        userRef.orderByKey().equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    Users user = dat.getValue(Users.class);
                    callBackMap.setUser(dat.getValue(Users.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }*/
}
