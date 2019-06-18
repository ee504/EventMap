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

import com.starichenkov.contracts.ContractEventsList;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.data.Events;
import com.starichenkov.presenter.myPresenters.PresenterEventsList;

import java.util.List;


public class BookMarksListFragment extends Fragment implements ContractEventsList.View, EventsListAdapter.OnEventListener {

    private static final String TAG = "MyLog";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EventsListAdapter adapter;

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

        presenterEventsList = new PresenterEventsList(this, new AccountAuthorization(getActivity()));
        if(getParentFragment() != null){
            presenterEventsList.getAllEvents();
        }else{
            presenterEventsList.getEventsFromBookmarks();
        }
        return view;
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
        adapter = new EventsListAdapter(getActivity(), this, R.layout.item_event, events);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void detachView() {
        presenterEventsList.detachView();
    }
}
