package com.starichenkov.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.starichenkov.createEvent.CallBackCreateEvent;
import com.starichenkov.data.Events;
import com.starichenkov.presenter.CallBackModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateEventModel {

    private final String TAG = "MyLog";

    private CallBackCreateEvent callBack;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;
    private DatabaseReference bookMarkRef;

    private StorageReference mStorageRef;

    public CreateEventModel(CallBackCreateEvent callBack) {

        this.callBack = callBack;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        eventRef = myRef.child("events");

        mStorageRef = FirebaseStorage.getInstance().getReference("photo");

    }

    public void createEvent(final Events event) {

        Log.e(TAG, "Model createEvent()");
        Log.e(TAG, "event.getPhotoEvent().startsWith(\"https\")" + event.getPhotoEvent().startsWith("https"));
        Log.e(TAG, "event.getPhotoEvent()" + event.getPhotoEvent());

        final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                + "." + "jpg");

        fileReference.putFile(Uri.parse(event.getPhotoEvent())).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    new File(Uri.parse(event.getPhotoEvent()).getPath()).delete();
                    event.setPhotoEvent(downloadUri.toString());

                    if(event.getId()!=null){
                        eventRef.child(event.getId()).setValue(event);
                    }else {
                        DatabaseReference pushedEventRef = eventRef.push();
                        event.setId(pushedEventRef.getKey().toString());
                        pushedEventRef.setValue(event);
                    }

                    //getAllEvents();
                    //callBack.startMainActivity();

                } else {
                    Log.d(TAG, "task.getException().getMessage(): " + task.getException().getMessage());
                    //Toast.makeText(MainActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void updateEvent(final Events event) {

        Log.e(TAG, "Model updateEvent()");
        Log.e(TAG, "event.getPhotoEvent().startsWith(\"https\")" + event.getPhotoEvent().startsWith("https"));
        Log.e(TAG, "event.getPhotoEvent()" + event.getPhotoEvent());

        if(event.getPhotoEvent().startsWith("https")) {
            Log.e(TAG, "true");
            eventRef.child(event.getId()).setValue(event);
            //callBack.startMainActivity();
        }else{
            Log.e(TAG, "false");
            createEvent(event);
        }


    }

    public void getEventById(String idEvent) {

        Log.e(TAG, "Model getEventById()");

        eventRef.orderByKey().equalTo(idEvent).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Events> listEvents = new ArrayList<Events>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listEvents.add(dat.getValue(Events.class));
                }
                //callBack.setEvent(dataSnapshot.getChildren().iterator().next().getValue(Events.class));
                callBack.setEvents(listEvents);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });

    }

}
