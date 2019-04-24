package com.starichenkov.Model;

import android.content.Context;
import android.util.Log;

import com.starichenkov.RoomDB.App;
import com.starichenkov.RoomDB.AppDataBase;
import com.starichenkov.RoomDB.Users;
import com.starichenkov.RoomDB.UsersDao;
import com.starichenkov.customClasses.AccountAuthorization;
import com.starichenkov.eventmap.IView;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Model implements IModel {

    private AppDataBase db;
    private UsersDao userDao;
    private static final String TAG = "MyLog";
    private IView iView;

    public Model(IView iView) {

        AppDataBase db = App.getInstance().getDatabase();
        userDao = db.usersDao();
        this.iView = iView;

    }


    @Override
    public void createUser(final String fio, final String mail, final String password) {

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
}
