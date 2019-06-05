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


public class BookMarksListFragment extends Fragment {

    private static final String TAG = "MyLog";
    private final String nameFragment = "bookMarksListFragment";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BookMarksListAdapter adapter;

    //private Presenter presenter;

   // private List<Events> events;

    private CallBackInterfaceMap mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.activity_main_view, container, false);
        View view = inflater.inflate(R.layout.activity_book_marks, null);

        Log.d(TAG, "------------------------------------");
        Log.d(TAG, "BookMarksListFragment onCreateView()");
        Log.d(TAG, "------------------------------------");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //presenter = new Presenter(this);
        //presenter.getEventsFromBookmarks();
        //mListener.getEventsFromBookmarks();
        mListener.getEvents();

        return view;
    }

    public void sendEvents(List<Events> events){
        //this.events = events;
        //setEvents(events);
        Log.d(TAG, "BookMarksListFragment setEvents()");
        adapter = new BookMarksListAdapter(getActivity(), R.layout.item_event, events);
        //lvBookMarks.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
    }

    /*@Override
    public void detachView(){
        presenter.detachView();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detachView();
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CallBackInterfaceMap) context;
            //mListener.setCurrentFragment(nameFragment);
            Log.d(TAG, nameFragment + " onAttach()");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackInterfaceMap");
        }
    }

    public void onEventClick(int position) {
        Log.d(TAG, "position: " + position);
        //mListener.openMapWithMarker(events.get(position).id);
        mListener.openMapWithMarker(position);
        //Log.d(TAG, "events.get(position).id: " + events.get(position).id);

    }

    public void filter(String query) {
        adapter.filter(query);
    }
}
