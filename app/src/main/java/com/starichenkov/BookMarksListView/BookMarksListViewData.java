package com.starichenkov.BookMarksListView;

public class BookMarksListViewData {

    public String getNameEvent() {
        return nameEvent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public String getAddressEvent() {
        return addressEvent;
    }

    private String nameEvent;
    private String typeEvent;
    private String addressEvent;

    public BookMarksListViewData(String nameEvent, String typeEvent, String addressEvent){
        this.nameEvent = nameEvent;
        this.typeEvent = typeEvent;
        this.addressEvent = addressEvent;
    }
}
