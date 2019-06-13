package com.starichenkov.RoomDB;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface BookMarksDao {

    /*@Insert
    void insert(BookMarks bookMarks);

    @Delete
    void delete(BookMarks bookMarks);

    @Query("SELECT * FROM BookMarks WHERE idOrganizer = :idOrganizer")
    Single<List<BookMarks>> getAll(String idOrganizer);

    @Query("DELETE FROM BookMarks WHERE idOrganizer = :idClient AND idEvent = :idEvent")
    void deleteBookMark(String idClient, long idEvent);*/

}
