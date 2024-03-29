package com.starichenkov.data;

public class BookMarks {

    public String getId() {
        return id;
    }

    public String getIdOrganizer() {
        return idOrganizer;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdOrganizer(String idOrganizer) {
        this.idOrganizer = idOrganizer;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    private String id;

    private String idOrganizer;

    private String idEvent;

    public BookMarks(String idOrganizer, String idEvent){
        this.idOrganizer = idOrganizer;
        this.idEvent = idEvent;
    }

    public BookMarks(String id, String idOrganizer, String idEvent){
        this.id = id;
        this.idOrganizer = idOrganizer;
        this.idEvent = idEvent;
    }

    public BookMarks(){
    }

}
