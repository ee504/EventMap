package com.starichenkov.eventmap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starichenkov.bookMarksListView.BookMarksListAdapter;
import com.starichenkov.data.Events;
import com.starichenkov.presenter.PresenterEventsList;
import com.starichenkov.view.interfaces.IViewEvents;

import java.util.List;


public class BookMarksListFragment extends Fragment implements IViewEvents, BookMarksListAdapter.OnEventListener {

    private static final String TAG = "MyLog";
    private final String nameFragment = "bookMarksListFragment";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BookMarksListAdapter adapter;

    //private Presenter presenter;

   // private List<Events> events;

    private CallBackInterfaceMap mListener;

    private PresenterEventsList presenterEventsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_book_marks, null);

        Log.d(TAG, "------------------------------------");
        Log.d(TAG, "BookMarksListFragment onCreateView()");
        Log.d(TAG, "------------------------------------");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        presenterEventsList = new PresenterEventsList(this);
        if(getParentFragment() != null){
            presenterEventsList.getAllEvents();
        }else{
            presenterEventsList.getAllBookmarks();
        }
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenterEventsList.detachView();
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

    @Override
    public void onEventClick(int position) {
        Log.d(TAG, "position: " + position);
        mListener.openMapWithMarker(presenterEventsList.getIdEventByPosition(position));
    }

    public void filter(String query) {
        adapter.filter(query);
    }

    @Override
    public void setEvents(List<Events> events) {
        Log.d(TAG, "BookMarksListFragment setEvents()");
        adapter = new BookMarksListAdapter(getActivity(), this, R.layout.item_event, events);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startMainActivity() {

    }
}
