package com.starichenkov.presenter;

import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.model.Model;
import com.starichenkov.model.mModel;
import com.starichenkov.presenter.interfaces.IPresenterMap;
import com.starichenkov.view.interfaces.IViewMain;
import com.starichenkov.view.interfaces.IViewMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PresenterMap implements IPresenterMap, CallBackModel {

    private IViewMap iView;
    private mModel model;
    private AccountAuthorization account;

    private List<Events> events;
    //private List<BookMarks> bookMarks;
    private Map<String, String> bookMarksMap;
    private Events currentEvent;

    public PresenterMap(IViewMap iViewMain){
        this.iView = iViewMain;
        account = new AccountAuthorization();
        model = new mModel(this);
        bookMarksMap = new HashMap<String, String>();
        events = new ArrayList<Events>();
    }


    @Override
    public void getAllEvents(){
        model.getAllEvents();
    }

    @Override
    public void getAllBookmarks() {
        model.getAllBookmarks(account.getIdUser());
    }

    @Override
    public void getEventsFromBookmarks() {

    }

    @Override
    public void createBookMark() {
        model.createBookMark(new BookMarks(account.getIdUser(), currentEvent.getId()));
    }

    @Override
    public void deleteBookMark() {
        for(String key : bookMarksMap.keySet()){
            if(bookMarksMap.get(key).equals(currentEvent.getId())){
                model.deleteBookMark(new BookMarks(key, account.getIdUser(), currentEvent.getId()));
                break;
            }
        }
    }

    @Override
    public boolean checkAuthorization() {
        return account.checkAuthorization();
    }

    @Override
    public void deleteAuthorization(){
        account.deleteAuthorization();
    }

    @Override
    public void getCurrentUser() {
        model.getCurrentUser(account.getIdUser());
    }

    @Override
    public void detachView() {
        iView = null;
    }

    @Override
    public void setEvents(List<Events> events){
        this.events = events;
        iView.setEvents(events);
        //model.setValueEventListener();
    }

    @Override
    public void setBookMarks(List<BookMarks> bookMarks){
        for (BookMarks bm: bookMarks) {
            bookMarksMap.put(bm.getId(), bm.getIdEvent());
        }
        iView.setBookMarks(bookMarks);
    }

    @Override
    public void setUser(Users user){
        iView.setCurrentUser(user);
    }

    @Override
    public void setEvent(Events event) {
        events.add(event);
        iView.setEvent(event);
    }

    @Override
    public void updateEvent(Events event) {
        int i = 0;
        for(Events ev : events) {
            if (ev.getId().equals(event.getId())) {
                events.set(i, event);
                break;
            }
            i++;
        }
    }

    @Override
    public void deleteEvent(String id) {
        int i = 0;
        for(Events ev : events) {
            if (ev.getId().equals(id)) {
                events.remove(i);
                break;
            }
            i++;
        }
        iView.deleteMarker(id);
    }


    public void onClickMarker(String idEvent) {
        for(Events event : events){
            if(event.getId().equals(idEvent)){
                currentEvent = event;
                //return currentEvent;
            }
        }
        //return null;
        iView.setCurrentEvent(currentEvent);
    }

    public boolean checkBookMark(String idEvent) {
        return bookMarksMap.containsValue(idEvent);
    }

}
