package com.starichenkov.eventmap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
//import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.LoaderManager;


import com.starichenkov.Model.DB;

import java.util.concurrent.TimeUnit;

public class UsersDataActivity extends AppCompatActivity implements IView, LoaderManager.LoaderCallbacks<Cursor> {


    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;
    private static final String TAG = "MyLog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_data);

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // формируем столбцы сопоставления
        String[] from = new String[] { DB.COLUMN_FIO, DB.COLUMN_MAIL, DB.COLUMN_PASSWORD, DB.COLUMN_BIRDTH };
        int[] to = new int[] { R.id.textViewFIO, R.id.textViewMail, R.id.textViewPassword, R.id.textViewAge };

        // создаем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        // добавляем контекстное меню к списку
        //registerForContextMenu(lvData);

        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null,  this);

    }


    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }

    }
}
