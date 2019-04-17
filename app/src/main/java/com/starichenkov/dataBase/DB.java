package com.starichenkov.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE_USERS = "users";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIO = "fio";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_BIRDTH = "date_birdth";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_PASSWORD = "password";

    private static final String DB_CREATE_USERS =
            "create table " + DB_TABLE_USERS + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_FIO + " text, " +
                    COLUMN_MAIL + " text, " +
                    COLUMN_BIRDTH + " numeric, " +
                    COLUMN_PASSWORD + " text, " +
                    COLUMN_PHOTO + " text" +
                    ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE_USERS, null, null, null, null, null, null);
    }

    // добавить запись в DB_CREATE_USERS
    public void addRec(String fio, String mail, String date_birdth, String password) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIO, fio);
        cv.put(COLUMN_MAIL, mail);
        cv.put(COLUMN_BIRDTH, date_birdth);
        cv.put(COLUMN_BIRDTH, password);
        mDB.insert(DB_CREATE_USERS, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_CREATE_USERS, COLUMN_ID + " = " + id, null);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_USERS);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
