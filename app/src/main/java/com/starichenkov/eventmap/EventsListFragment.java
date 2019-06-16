package com.starichenkov.eventmap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starichenkov.data.Events;
import com.starichenkov.presenter.PresenterEventsList;
import com.starichenkov.view.interfaces.IViewEvents;

import java.util.List;

public class EventsListFragment extends Fragment implements FilterCallback{

    private static final String TAG = "MyLog";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events_list, null);

        Log.d(TAG, "------------------------------------");
        Log.d(TAG, "EventsListFragment onCreateView()");
        Log.d(TAG, "------------------------------------");

        return view;
    }


    @Override
    public void filter(String query) {
        BookMarksListFragment bookMarksListFragment = (BookMarksListFragment)
                getChildFragmentManager().findFragmentById(R.id.frgmEventsList);
        bookMarksListFragment.filter(query);
    }

    @Override
    public void back() {
        //getSupportFragmentManager().popBackStackImmediate();
        getFragmentManager().popBackStackImmediate();
    }

}
