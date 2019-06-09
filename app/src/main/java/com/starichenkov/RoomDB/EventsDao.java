package com.starichenkov.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface EventsDao {

    @Query("SELECT * FROM Events")
    Single<List<Events>> getAll();

    @Insert
    void insert(Events events);

    @Query("SELECT Events.* FROM Events INNER JOIN BookMarks ON BookMarks.idEvent = Events.id WHERE BookMarks.idOrganizer = :idOrganizer ORDER BY Events.nameEvent")
    Single<List<Events>> getEventsFromBookmarks(long idOrganizer);

    @Query("SELECT Events.* FROM Events WHERE idOrganizer = :idOrganizer")
    Single<List<Events>> getUserEvents(long idOrganizer);

    @Query("SELECT Events.* FROM Events WHERE id = :id")
    Single<List<Events>> getEventById(long id);

    @Query("DELETE FROM Events")
    void deleteAllEvents();

    @Query("DELETE FROM Events WHERE id = :id")
    void deleteEventById(long id);

    @Update
    void update(Events event);

    @Delete
    void delete(Events event);
}
