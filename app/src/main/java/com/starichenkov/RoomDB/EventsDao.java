package com.starichenkov.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface EventsDao {

    @Query("SELECT * FROM Events")
    Single<List<Users>> getAll();

    @Insert
    void insert(Users user);

}
