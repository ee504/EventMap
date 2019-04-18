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

public class UsersDataActivity extends AppCompatActivity implements IView {


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
        //getSupportLoaderManager().initLoader(0, null,  this);

        Log.d(TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.getAllData();

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("_id");
            int nameColIndex = c.getColumnIndex("fio");
            int emailColIndex = c.getColumnIndex("mail");
            int birdthlColIndex = c.getColumnIndex("date_birdth");
            int passwordColIndex = c.getColumnIndex("password");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", email = " + c.getString(emailColIndex) +
                                ", birdthlColIndex = " + c.getString(birdthlColIndex) +
                                ", passwordColIndex = " + c.getString(passwordColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(TAG, "0 rows");
        c.close();

    }


    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }


}
