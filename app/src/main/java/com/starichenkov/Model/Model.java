package com.starichenkov.Model;

import android.content.Context;
import android.util.Log;

import com.starichenkov.RoomDB.App;
import com.starichenkov.RoomDB.AppDataBase;
import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.BookMarksDao;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.RoomDB.EventsDao;
import com.starichenkov.RoomDB.Users;
import com.starichenkov.RoomDB.UsersDao;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.eventmap.IView;
import com.starichenkov.presenter.Presenter;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Model implements IModel {

    private AppDataBase db;
    private UsersDao userDao;
    private EventsDao eventsDao;
    private BookMarksDao bookMarksDao;
    private static final String TAG = "MyLog";
    private IView iView;

    public Model(IView iView) {

        db = App.getInstance().getDatabase();
        userDao = db.usersDao();
        eventsDao = db.eventsDao();
        bookMarksDao = db.bookMarksDao();
        this.iView = iView;

    }


    @Override
    public void createUser(final String fio, final String mail, final String password) {

        //userDao.insert(new Users(fio, mail, password))
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                userDao.insert(new Users(fio, mail, password));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // just like with a Single
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        // action was completed successfully
                        Log.d(TAG, "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // something went wrong
                        Log.d(TAG, "onError");
                        Log.d(TAG, e.getMessage());
                    }
                });

    }

    @Override
    public void findUser(String mail, String password) {

        final AccountAuthorization accountAuthorization = new AccountAuthorization((Context)this.iView);

        userDao.getId(mail, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer id) {
                        Log.d(TAG, "onSuccess");
                        Log.d(TAG, "ID = " + id);
                        accountAuthorization.saveAuthorization(id);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Some error");
                        Log.d(TAG, e.getMessage());

                    }
                });

    }

    @Override
    public void createEvent(final Events event){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                eventsDao.insert(new Events(event));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // just like with a Single
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        // action was completed successfully
                        Log.d(TAG, "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // something went wrong
                        Log.d(TAG, "onError");
                        Log.d(TAG, e.getMessage());
                    }
                });
    }

    @Override
    public void getAllEvents(){
        Log.e(TAG, "Model getAllEvents()");
        eventsDao.getAll()
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
                        new Presenter(iView, "Model").sendEvents(events);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Some error");
                        Log.d(TAG, e.getMessage());
                    }
                });
    }

    @Override
    public void createBookMark(final long idOrganizer, final long id){

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                bookMarksDao.insert(new BookMarks(idOrganizer, id));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // just like with a Single
                        Log.d(TAG, "createBookMark() onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        // action was completed successfully
                        Log.d(TAG, "createBookMark() onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // something went wrong
                        Log.d(TAG, "createBookMark() onError");
                        Log.d(TAG, e.getMessage());
                    }
                });

    }

    /*@Override
    public void deleteBookMark(final long idClient, final long id){
        Log.d(TAG, "deleteBookMark(): idClient="+ idClient+" idEvent=" + id);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                bookMarksDao.delete(new BookMarks(idClient, id));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // just like with a Single
                        Log.d(TAG, "deleteBookMark() onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        // action was completed successfully
                        Log.d(TAG, "deleteBookMark() onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // something went wrong
                        Log.d(TAG, "deleteBookMark() onError");
                        Log.d(TAG, e.getMessage());
                    }
                });

    }*/

    @Override
    public void deleteBookMark(final long idClient, final long id){
        Log.d(TAG, "deleteBookMark(): idClient="+ idClient+" idEvent=" + id);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                bookMarksDao.deleteBookMark(idClient, id);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // just like with a Single
                        Log.d(TAG, "deleteBookMark() onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        // action was completed successfully
                        Log.d(TAG, "deleteBookMark() onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // something went wrong
                        Log.d(TAG, "deleteBookMark() onError");
                        Log.d(TAG, e.getMessage());
                    }
                });

    }

    @Override
    public void getAllBookmarks(){

        bookMarksDao.getAll(new AccountAuthorization((Context)iView).getIdUser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BookMarks>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // add it to a CompositeDisposable
                        Log.d(TAG, "Model getAllBookmarks() onSubscribe");
                    }
                    @Override
                    public void onSuccess(List<BookMarks> bookMarks) {
                        Log.d(TAG, "Model getAllBookmarks() onSuccess");
                        new Presenter(iView, "Model").sendBookMarks(bookMarks);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Model getAllBookmarks() Some error");
                        Log.d(TAG, e.getMessage());
                    }
                });
    }

    @Override
    public void getEventsFromBookmarks(){
        Log.e(TAG, "Model getEventsFromBookmarks()");
        eventsDao.getEventsFromBookmarks(new AccountAuthorization((Context)iView).getIdUser())
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
                        new Presenter(iView, "Model").sendEvents(events);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Some error");
                        Log.d(TAG, e.getMessage());
                    }
                });
    }

    @Override
    public void deleteAllEvents(){

        Completable.fromAction(new Action() {
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
                });
    }
}
