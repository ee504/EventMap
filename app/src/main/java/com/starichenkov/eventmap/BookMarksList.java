package com.starichenkov.eventmap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.starichenkov.BookMarksListView.BookMarksListAdapter;
import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.presenter.Presenter;
import com.starichenkov.view.CallBackFromDB;
import com.starichenkov.view.IView;

import java.util.List;


public class BookMarksList extends Fragment implements IView, CallBackFromDB {

    private static final String TAG = "MyLog";

    // имена атрибутов для Map
    final String ATTRIBUTE_IMZGE_EVENT = "imageEvent";
    final String ATTRIBUTE_NAME_EVENT = "textNameEvent";
    final String ATTRIBUTE_TYPE_EVENT = "textTypeEvent";
    final String ATTRIBUTE_ADDRESS_EVENT = "textAddressEvent";

    private ListView lvBookMarks;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Presenter presenter;

    private List<Events> events;

    private CallBackInterfaceMap mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.activity_main_view, container, false);
        View view = inflater.inflate(R.layout.activity_book_marks, null);
        Log.d(TAG, "1");
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_book_marks);

        //lvBookMarks = (ListView) findViewById(R.id.lvBookMarks);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Log.d(TAG, "2");
        presenter = new Presenter(this);
        Log.d(TAG, "3");
        presenter.getEventsFromBookmarks();

        return view;
    }

    public void sendEvents(List<Events> events){
        this.events = events;
        setEvents(events);
    }

    private void setEvents(List<Events> events) {

        Log.d(TAG, "BookMarksList setEvents()");
        BookMarksListAdapter adapter = new BookMarksListAdapter(getActivity(), R.layout.item_event, events);
        //lvBookMarks.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
    }

    public void sendBookMarks(List<BookMarks> bookMarks){

    }

    @Override
    public void detachView(){
        presenter.detachView();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detachView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CallBackInterfaceMap) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackInterfaceMap");
        }
    }

    public void onEventClick(int position) {
        Log.d(TAG, "position: " + position);
        mListener.openMapWithMarker(events.get(position).id);
        //Log.d(TAG, "events.get(position).id: " + events.get(position).id);

    }
}
