package com.starichenkov.Model;

import android.content.Context;
import android.util.Log;

import com.starichenkov.RoomDB.App;
import com.starichenkov.RoomDB.AppDataBase;
import com.starichenkov.RoomDB.Users;
import com.starichenkov.RoomDB.UsersDao;
import com.starichenkov.eventmap.IView;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Model implements IModel{

    private AppDataBase db;
    private UsersDao userDao;
    private static final String TAG = "MyLog";

    public Model() {
        //this.db = new DB((Context) iView);
        //db.open();
        AppDataBase db = App.getInstance().getDatabase();
        userDao = db.usersDao();

    }

    public void createUser(final String fio, final String mail, final String password){

        //userDao.insert(new Users(fio, mail, password));

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
}
