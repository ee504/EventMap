package com.starichenkov.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.presenter.IPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Model implements IModel {

    private static final String TAG = "MyLog";

    private IPresenter presenter;
    private AccountAuthorization accountAuthorization;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private DatabaseReference eventRef;
    private DatabaseReference bookMarkRef;

    private StorageReference mStorageRef;

    public Model(IPresenter presenter) {

        //db = App.getInstance().getDatabase();
        //userDao = db.usersDao();
        //eventsDao = db.eventsDao();
        //bookMarksDao = db.bookMarksDao();
        accountAuthorization = new AccountAuthorization();
        this.presenter = presenter;
        //this.iView = iView;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");
        eventRef = myRef.child("events");
        bookMarkRef = myRef.child("bookmarks");

        mStorageRef = FirebaseStorage.getInstance().getReference("photo");
    }


    @Override
    public void createUser(final String fio, final String mail, final String password) {

        userRef.push().setValue(new Users(fio, mail, password));

    }

    @Override
    public void updateUser(final Users user) {

        userRef.child(accountAuthorization.getIdUser()).setValue(user);


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
                        accountAuthorization.saveAuthorization(dat.getKey());
                        presenter.startMainActivity();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });


    }


    @Override
    public void getCurrentUser(){

        Log.e(TAG, "Model getCurrentUser()");

        userRef.orderByKey().equalTo(accountAuthorization.getIdUser()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    //Log.e(TAG, "Model dataSnapshot: " + dat);
                    Users user = dat.getValue(Users.class);
                    //Log.e(TAG, "Model user.getFio(): " + user.getFio());
                    //Log.e(TAG, "Model user.getMail(): " + user.getMail());
                    presenter.sendUser(dat.getValue(Users.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });


    }

    @Override
    public void createEvent(final Events event){

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

                    //getAllEvents();
                    presenter.startMainActivity();

                } else {
                    Log.d(TAG, "task.getException().getMessage(): " + task.getException().getMessage());
                    //Toast.makeText(MainActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void deletePhoto(String photoEvent){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference photoRef = storage.getReferenceFromUrl(photoEvent);
        photoRef.delete();
    }

    @Override
    public void updateEvent(final Events event){

        Log.e(TAG, "Model updateEvent()");

        createEvent(event);

    }

    @Override
    public void getAllEvents(){

        Log.e(TAG, "Model getAllEvents()");

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Events> listEvents = new ArrayList<Events>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listEvents.add(dat.getValue(Events.class));

                }
                presenter.sendEvents(listEvents);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });

    }

    @Override
    public void createBookMark(final BookMarks bookMark){

        DatabaseReference pushedBookMarkRef = bookMarkRef.push();
        bookMark.setId(pushedBookMarkRef.getKey().toString());
        pushedBookMarkRef.setValue(bookMark);


    }

    @Override
    public void deleteBookMark(final BookMarks bookMark){

        Log.d(TAG, "Model deleteBookMark()");

        bookMarkRef.child(bookMark.getId()).removeValue();

    }

    @Override
    public void getAllBookmarks(){

        Log.e(TAG, "Model getAllBookmarks()");

        bookMarkRef.orderByChild("idOrganizer").equalTo(accountAuthorization.getIdUser()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<BookMarks> listBookMarks = new ArrayList<BookMarks>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listBookMarks.add(dat.getValue(BookMarks.class));
                }
                presenter.sendBookMarks(listBookMarks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });

    }

    @Override
    public void getEventsFromBookmarks(List<BookMarks> bookMarks){

        Log.e(TAG, "Model getEventsFromBookmarks()");

        /*final List<Events> listEvents = new ArrayList<Events>();
        for(BookMarks bm : bookMarks){

            eventRef.orderByChild("id").equalTo(bm.getIdEvent()).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listEvents.add(dataSnapshot.getChildren().iterator().next().getValue(Events.class));
                    //presenter.sendBookMarks(listBookMarks);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
                }
            });

        }*/

        /*//eventsDao.getEventsFromBookmarks(new AccountAuthorization().getIdUser())
        eventsDao.getEventsFromBookmarks(accountAuthorization.getIdUser())
        //eventsDao.getEventsFromBookmarks(new AccountAuthorization().getIdUser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Events>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // add it to a CompositeDisposable
                    }
                    @Override
                    public void onSuccess(List<Events> events) {
                        Log.d(TAG, "Model getAllEvents() onSuccess");
                        //new Presenter(iView, "Model").sendEvents(events);
                        presenter.sendEvents(events);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Some error");
                        Log.d(TAG, e.getMessage());
                    }
                });*/
    }

    @Override
    public void deleteAllEvents(){

        /*Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                eventsDao.deleteAllEvents();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // just like with a Single
                        Log.d(TAG, "deleteAllEvents() onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        // action was completed successfully
                        Log.d(TAG, "deleteAllEvents() onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // something went wrong
                        Log.d(TAG, "deleteBookMark() onError");
                        Log.d(TAG, e.getMessage());
                    }
                });*/
    }

    @Override
    public void detachView(){
        presenter = null;
        accountAuthorization = null;
    }

    @Override
    public void getUserEvents(){

        Log.e(TAG, "Model getUserEvents()");

        eventRef.orderByChild("idOrganizer").equalTo(accountAuthorization.getIdUser()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Events> listEvents = new ArrayList<Events>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listEvents.add(dat.getValue(Events.class));
                }
                presenter.sendEvents(listEvents);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });

        /*Log.e(TAG, "Model getUserEvents()");
        //eventsDao.getUserEvents(new AccountAuthorization().getIdUser())
        eventsDao.getUserEvents(accountAuthorization.getIdUser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Events>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // add it to a CompositeDisposable
                    }
                    @Override
                    public void onSuccess(List<Events> events) {
                        Log.d(TAG, "Model getUserEvents() onSuccess");
                        //new Presenter(iView, "Model").sendEvents(events);
                        presenter.sendEvents(events);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getUserEvents Some error");
                        Log.d(TAG, e.getMessage());
                    }
                });*/

    }

    @Override
    public void getEventById(String idEvent){

        Log.e(TAG, "Model getEventById()");

        eventRef.orderByKey().equalTo(idEvent).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Events> listEvents = new ArrayList<Events>();
                for(DataSnapshot dat : dataSnapshot.getChildren()) {
                    listEvents.add(dat.getValue(Events.class));
                }
                presenter.sendEvents(listEvents);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError.getMessage(): " + databaseError.getMessage());
            }
        });

        /*Log.e(TAG, "Model getEventById()");
        //eventsDao.getUserEvents(new AccountAuthorization().getIdUser())
        eventsDao.getEventById(idEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Events>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // add it to a CompositeDisposable
                    }
                    @Override
                    public void onSuccess(List<Events> events) {
                        Log.d(TAG, "Model getUserEvents() onSuccess");
                        //new Presenter(iView, "Model").sendEvents(events);
                        presenter.sendEvents(events);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getUserEvents Some error");
                        Log.d(TAG, e.getMessage());
                    }
                });*/

    }

    @Override
    public void deleteEventById(final long id){

        /*Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                eventsDao.deleteEventById(id);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // just like with a Single
                        Log.d(TAG, "deleteEventById() onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        // action was completed successfully
                        Log.d(TAG, "deleteEventById() onComplete");
                        getAllEvents();

                    }

                    @Override
                    public void onError(Throwable e) {
                        // something went wrong
                        Log.d(TAG, "deleteEventById() onError");
                        Log.d(TAG, e.getMessage());
                    }
                });*/
    }

    @Override
    public void deleteEvent(final Events event){

        Log.d(TAG, "Model deleteEvent()");

        if(event.getPhotoEvent() != null) {
            deletePhoto(event.getPhotoEvent());
        }
        eventRef.child(event.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid) {
                presenter.startMainActivity();
            }
        });
        //presenter.startMainActivity();
        /*new File(Uri.parse(event.photoEvent).getPath()).delete();
        new File(Uri.parse(event.photoEventFullSize).getPath()).delete();

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                eventsDao.delete(event);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        // action was completed successfully
                        Log.d(TAG, "deleteEvent() onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // something went wrong
                        Log.d(TAG, "deleteEvent() onError");
                        Log.d(TAG, e.getMessage());
                    }
                });*/

    }
}
