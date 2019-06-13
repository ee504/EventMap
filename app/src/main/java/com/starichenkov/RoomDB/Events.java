package com.starichenkov.RoomDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(
        indices = {@Index("nameEvent"), @Index("idOrganizer")})
public class Events {

    public String getIdOrganizer() {
        return idOrganizer;
    }

    public String getPhotoEvent() {
        return photoEvent;
    }

    public String getPhotoEventFullSize() {
        return photoEventFullSize;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public String getDescriptionEvent() {
        return descriptionEvent;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public String getAddressEvent() {
        return addressEvent;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setIdOrganizer(String idOrganizer) {
        this.idOrganizer = idOrganizer;
    }

    public void setPhotoEvent(String photoEvent) {
        this.photoEvent = photoEvent;
    }

    public void setPhotoEventFullSize(String photoEventFullSize) {
        this.photoEventFullSize = photoEventFullSize;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public void setDescriptionEvent(String descriptionEvent) {
        this.descriptionEvent = descriptionEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public void setAddressEvent(String addressEvent) {
        this.addressEvent = addressEvent;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    private String idOrganizer;

    private String photoEvent;
    private String photoEventFullSize;

    private String nameEvent;

    private String descriptionEvent;

    private String dateEvent;

    private String typeEvent;

    private String addressEvent;

    private double latitude;

    private double longitude;

    public Events(String idOrganizer, String photoEvent, String photoEventFullSize, String nameEvent, String descriptionEvent, String dateEvent, String typeEvent, String addressEvent, double latitude, double longitude){
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

    public Events(){

    }

    public boolean equals(BookMarks bookMarks) {
        return (this.getId().equals(bookMarks.getIdEvent()));
    }
}
