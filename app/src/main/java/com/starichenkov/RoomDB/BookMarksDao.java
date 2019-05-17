package com.starichenkov.RoomDB;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface BookMarksDao {

    @Insert
    void insert(BookMarks bookMarks);

}
