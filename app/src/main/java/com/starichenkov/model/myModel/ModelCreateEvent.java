package com.starichenkov.model.myModel;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.starichenkov.contracts.ContractCreateEvent;
import com.starichenkov.data.Events;
import com.starichenkov.presenter.CallBacks.CallBackCreateEvent;

import java.io.File;

public class ModelCreateEvent extends ModelPhoto implements ContractCreateEvent.Model {

    CallBackCreateEvent callBackCreateEvent;

    protected DatabaseReference eventRef;
    private StorageReference mStorageRef;

    public ModelCreateEvent(CallBackCreateEvent callBackCreateEvent) {
        super(callBackCreateEvent);
        this.callBackCreateEvent = callBackCreateEvent;
        eventRef = myRef.child("events");
        mStorageRef = FirebaseStorage.getInstance().getReference("photo");
    }

    @Override
    public void updateEvent(Events event) {
        Log.e(TAG, "Model updateEvent()");

        if(event.getPhotoEvent().startsWith("https")) {
            Log.e(TAG, "true");
            eventRef.child(event.getId()).setValue(event);
        }else{
            Log.e(TAG, "false");
            createEvent(event);
        }
    }

    @Override
    public void createEvent(final Events event) {
        Log.e(TAG, "Model createEvent()");

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

                } else {
                    Log.d(TAG, "task.getException().getMessage(): " + task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void getEventById(final String IdEvent) {

        Log.e(TAG, "Model getEventById()");

        eventRef.orderByKey().equalTo(IdEvent).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callBackCreateEvent.setCurrentEvent(dataSnapshot.getChildren().iterator().next().getValue(Events.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });
    }
}
