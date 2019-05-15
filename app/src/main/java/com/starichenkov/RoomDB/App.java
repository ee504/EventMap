package com.starichenkov.RoomDB;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;

public class App extends Application {

    public static App instance;

    private AppDataBase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDataBase.class, "database")
                .addMigrations(App.MIGRATION_1_2)
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDatabase() {
        return database;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `idOrganizer` INTEGER NOT NULL, `photoEvent` TEXT, `nameEvent` TEXT, `dateEvent` TEXT, `typeEvent` TEXT,"
                    + "`addressEvent` TEXT, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, FOREIGN KEY (`idOrganizer`) REFERENCES 'Users' ('id'))");
            database.execSQL("CREATE INDEX `index_Events_nameEvent` ON `Events` (`nameEvent`)");
            database.execSQL("CREATE INDEX `index_Events_idOrganizer` ON `Events` (`idOrganizer`)");
        }
    };

}
