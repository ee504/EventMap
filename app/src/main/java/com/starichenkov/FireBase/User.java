package com.starichenkov.FireBase;

public class User {

    public User(String fio, String mail, String password){
        this.fio = fio;
        this.mail = mail;
        this.password = password;
    }

    public User(){

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


    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String fio;

    private String mail;

    private String password;

}
