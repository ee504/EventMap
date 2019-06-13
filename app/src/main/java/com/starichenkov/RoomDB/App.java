package com.starichenkov.RoomDB;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.google.firebase.FirebaseApp;

public class App extends Application {

    //public static App instance;

    //private AppDataBase database;

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //instance = this;
        //database = Room.databaseBuilder(this, AppDataBase.class, "database").build();

        //FirebaseApp.initializeApp(this);
    }

    public static Context getAppContext() {
        return mContext;
    }

    //public static App getInstance() { return instance; }

    //public AppDataBase getDatabase() {return database;}

    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `idOrganizer` INTEGER NOT NULL, `photoEvent` TEXT, `nameEvent` TEXT, `dateEvent` TEXT, `typeEvent` TEXT,"
                    + "`addressEvent` TEXT, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, FOREIGN KEY (`idOrganizer`) REFERENCES 'Users' ('id'))");
            database.execSQL("CREATE INDEX `index_Events_nameEvent` ON `Events` (`nameEvent`)");
            database.execSQL("CREATE INDEX `index_Events_idOrganizer` ON `Events` (`idOrganizer`)");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `Events` "
                    + " ADD COLUMN `descriptionEvent` TEXT");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `BookMarks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `idOrganizer` INTEGER NOT NULL, `idEvent` INTEGER NOT NULL," +
                    " FOREIGN KEY (`idOrganizer`) REFERENCES 'Users' ('id'), FOREIGN KEY (`idEvent`) REFERENCES 'Events' ('id'))");
            database.execSQL("CREATE INDEX `index_BookMarks_nameEvent` ON `BookMarks` (`idOrganizer`)");
            database.execSQL("CREATE INDEX `index_BookMarks_idOrganizer` ON `BookMarks` (`idEvent`)");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `Events` "
                    + " ADD COLUMN `photoEventFullSize` TEXT");
        }
    };*/

}
