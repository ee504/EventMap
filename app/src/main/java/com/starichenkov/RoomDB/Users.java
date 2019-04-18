package com.starichenkov.RoomDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity()
public class Users {

    public Users(String fio, String mail, String password){
        this.fio = fio;
        this.mail = mail;
        this.password = password;
    }

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String fio;

    public String mail;

    public String password;

    public String photo;

}
