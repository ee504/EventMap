package com.starichenkov.presenter;

import com.starichenkov.contracts.ContractMap;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.model.ModelMap;
import com.starichenkov.presenter.CallBacks.CallBackMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresenterMap extends PresenterAuthorization implements ContractMap.Presenter, CallBackMap {

    private ContractMap.View iView;
    private ModelMap model;
    private AccountAuthorization account;

    private List<Events> events;
    //private List<BookMarks> bookMarks;
    private Map<String, String> bookMarksMap;
    private Events currentEvent;

    public PresenterMap(ContractMap.View iView, AccountAuthorization account){
        super(iView, account);
        this.iView = iView;
        this.account = account;
        model = new ModelMap(this);
        bookMarksMap = new HashMap<String, String>();
        events = new ArrayList<Events>();
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
    public void getAllBookmarks() {
        model.getAllBookmarks(account.getIdUser());
    }

    @Override
    public boolean checkBookMark(String idEvent) {
        return bookMarksMap.containsValue(idEvent);
    }

    @Override
    public void onClickMarker(String idEvent) {
        for(Events event : events){
            if(event.getId().equals(idEvent)){
                currentEvent = event;
            }
        }
        iView.setCurrentEvent(currentEvent);
    }

    @Override
    public void detachView() {
        iView = null;
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
        for(String key : bookMarksMap.keySet()){
            if(bookMarksMap.get(key).equals(id)){
                model.deleteBookMark(new BookMarks(key, account.getIdUser(), id));
                break;
            }
        }
    }

    @Override
    public void setBookMarks(List<BookMarks> bookMarks) {
        for (BookMarks bm: bookMarks) {
            bookMarksMap.put(bm.getId(), bm.getIdEvent());
        }
    }
}
