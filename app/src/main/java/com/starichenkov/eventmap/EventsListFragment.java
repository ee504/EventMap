package com.starichenkov.eventmap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starichenkov.data.Events;

import java.util.List;

public class EventsListFragment extends Fragment {

    private static final String TAG = "MyLog";

    private CallBackInterfaceMap mListener;
    private final String nameFragment = "eventsListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events_list, null);

        Log.d(TAG, "------------------------------------");
        Log.d(TAG, "EventsListFragment onCreateView()");
        Log.d(TAG, "------------------------------------");

        return view;
    }

    public void sendEvents(List<Events> events) {

        //Fragment bookMarksListFragment = new BookMarksListFragment();
        BookMarksListFragment bookMarksListFragment = (BookMarksListFragment)
                getChildFragmentManager().findFragmentById(R.id.frgmEventsList);
        bookMarksListFragment.sendEvents(events);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CallBackInterfaceMap) context;
            mListener.setCurrentFragment(nameFragment);
            Log.d(TAG, nameFragment + " onAttach()");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackInterfaceMap");
        }
    }

    public void filter(String query) {
        BookMarksListFragment bookMarksListFragment = (BookMarksListFragment)
                getChildFragmentManager().findFragmentById(R.id.frgmEventsList);
        bookMarksListFragment.filter(query);
    }

    public void onEventClick(int position) {
        BookMarksListFragment bookMarksListFragment = (BookMarksListFragment)
                getChildFragmentManager().findFragmentById(R.id.frgmEventsList);
        bookMarksListFragment.onEventClick(position);
    }
}
