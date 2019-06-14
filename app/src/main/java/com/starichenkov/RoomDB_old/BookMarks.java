package com.starichenkov.RoomDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(onDelete = CASCADE, entity = Users.class, parentColumns = "id", childColumns = "idOrganizer"),
            @ForeignKey(onDelete = CASCADE, entity = Events.class, parentColumns = "id", childColumns = "idEvent")},
            indices = {@Index("idEvent"), @Index("idOrganizer")})
public class BookMarks {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long idOrganizer;

    public long idEvent;

    public BookMarks(long idOrganizer, long idEvent){
        this.idOrganizer = idOrganizer;
        this.idEvent = idEvent;
    }
}
