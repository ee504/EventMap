package com.starichenkov.account;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.starichenkov.RoomDB.Events;
import com.starichenkov.eventmap.CallBackInterfaceMap;
import com.starichenkov.eventmap.R;

import java.util.List;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private ImageView imagePhoto;
    private ImageView imageMore;
    private TextView textFio;
    private TextView textMail;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EventsListInAccountAdapter adapter;

    private static final String TAG = "MyLog";

    private CallBackInterfaceAccount mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.activity_main_view, container, false);
        View view = inflater.inflate(R.layout.account, null);

        imagePhoto = (ImageView) view.findViewById(R.id.imagePhoto);
        imageMore = (ImageView) view.findViewById(R.id.imageMore);
        imageMore.setOnClickListener(this);

        textFio = (TextView) view.findViewById(R.id.textFio);
        textFio.setText("Тестовый юзер");
        textMail = (TextView) view.findViewById(R.id.textMail);
        textMail.setText("тест@тест");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mListener.getUserEvents();

        return view;
    }

    public void sendEvents(List<Events> events){
        //this.events = events;
        //setEvents(events);
        Log.d(TAG, "BookMarksListFragment setEvents()");
        adapter = new EventsListInAccountAdapter(getActivity(), R.layout.item_event_in_account, events);
        //lvBookMarks.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.imageMore:
                Log.d(TAG, "onClick() imageMore");
                showPopupMenu(v);
                break;
        }
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.inflate(R.menu.account_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.itemMenuEdit:
                                Log.d(TAG, "onClick() Редактировать профиль");
                                mListener.openChangeAccountFragment();
                                return true;
                            case R.id.itemMenuExit:
                                Log.d(TAG, "onClick() Выход");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
        popupMenu.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CallBackInterfaceAccount) context;
            //mListener.setCurrentFragment(nameFragment);
            Log.d(TAG, "AccountFragment onAttach()");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackInterfaceMap");
        }
    }

}
