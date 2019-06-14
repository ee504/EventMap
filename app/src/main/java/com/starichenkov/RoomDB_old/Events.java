package com.starichenkov.RoomDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(foreignKeys = @ForeignKey(entity = Users.class, parentColumns = "id", childColumns = "idOrganizer"),
        indices = {@Index("nameEvent"), @Index("idOrganizer")})
public class Events {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long idOrganizer;

    public String photoEvent;
    public String photoEventFullSize;

    public String nameEvent;

    public String descriptionEvent;

    public String dateEvent;

    public String typeEvent;

    public String addressEvent;

    public double latitude;

    public double longitude;

    public Events(long idOrganizer, String photoEvent, String photoEventFullSize, String nameEvent, String descriptionEvent, String dateEvent, String typeEvent, String addressEvent, double latitude, double longitude){
        this.idOrganizer = idOrganizer;
        this.photoEvent = photoEvent;
        this.photoEventFullSize = photoEventFullSize;
        this.nameEvent = nameEvent;
        this.descriptionEvent = descriptionEvent;
        this.dateEvent = dateEvent;
        this.typeEvent = typeEvent;
        this.addressEvent = addressEvent;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Events(Events event){
        this.idOrganizer = event.idOrganizer;
        this.photoEvent = event.photoEvent;
        this.photoEventFullSize = event.photoEventFullSize;
        this.nameEvent = event.nameEvent;
        this.descriptionEvent = event.descriptionEvent;
        this.dateEvent = event.dateEvent;
        this.typeEvent = event.typeEvent;
        this.addressEvent = event.addressEvent;
        this.latitude = event.latitude;
        this.longitude = event.longitude;
    }
}
