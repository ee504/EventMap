package com.starichenkov.eventmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
//import android.support.v4.app.LoaderManager.LoaderCallbacks;


import com.starichenkov.RoomDB.App;
import com.starichenkov.RoomDB.AppDataBase;
import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.RoomDB.Users;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.view.IView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UsersDataActivity extends AppCompatActivity implements IView {


    private static final String TAG = "MyLog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_data);
        Log.d(TAG, "ID: " + new AccountAuthorization().getIdUser());
        Log.d(TAG, "--- Rows in mytable: ---");

        AppDataBase db = App.getInstance().getDatabase();

        boolean test = new AccountAuthorization().checkAuthorization();
        Log.d(TAG, "authorized = " + test);

        db.usersDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Users>>() {
                    @Override
                    public void onSuccess(List<Users> users) {
                        Log.d(TAG, "onSuccess");
                        for(Users user: users){
                            Log.d(TAG,
                                    "ID = " + user.id +
                                            ", fio = " + user.fio +
                                            ", email = " + user.mail +
                                            ", password = " + user.password);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Some error");
                        Log.d(TAG, e.getMessage());
                    }
                });

    }


    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
    }

    @Override
    public void sendEvents(List<Events> events){
    }

    @Override
    public void sendBookMarks(List<BookMarks> bookMarks){
    }

    @Override
    public void detachView(){
    }

}
