package com.starichenkov.RoomDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity()
public class Users {

    public Users(String fio, String mail, String password){
        this.fio = fio;
        this.mail = mail;
        this.password = password;
    }


    public long getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String fio;

    private String mail;

    private String password;

    private String photo;

}
