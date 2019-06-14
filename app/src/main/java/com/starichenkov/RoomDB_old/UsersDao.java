package com.starichenkov.RoomDB;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UsersDao {

    @Query("SELECT * FROM Users")
    Single<List<Users>> getAll();

    @Query("SELECT * FROM Users WHERE id = :id")
    Single<Users> getById(long id);

    @Query("SELECT id FROM Users WHERE mail = :mail and password = :password")
    Single<Integer> getId(String mail, String password);

    @Insert
    void insert(Users user);
    //Single<Long> insert(Users user);

    @Update
    void update(Users users);

    @Delete
    void delete(Users users);

}
