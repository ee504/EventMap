package com.starichenkov.account;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.starichenkov.createEvent.CreateEventActivity;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.eventmap.R;
import com.starichenkov.presenter.PresenterAccount;
import com.starichenkov.view.interfaces.IViewEvents;

import java.util.List;

public class AccountFragment extends Fragment implements View.OnClickListener, IViewEvents, EventsListInAccountAdapter.OnEventListener {

    private ImageView imagePhoto;
    private ImageView imageMore;
    private TextView textFio;
    private TextView textMail;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EventsListInAccountAdapter adapter;

    private static final String TAG = "MyLog";

    private CallBackInterfaceAccount mListener;

    //private String nameFragment = "AccountFragment";

    private PresenterAccount presenterAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.activity_main_view, container, false);
        View view = inflater.inflate(R.layout.account_fragment, null);

        initView(view);

        presenterAccount = new PresenterAccount(this);

        //mListener.setCurrentFragment(nameFragment);
        //mListener.getUserEvents();
        //mListener.getAccountData();

        presenterAccount.getUsersEvents();
        presenterAccount.getCurrentUser();

        return view;
    }

    private void initView(View view){
        imagePhoto = (ImageView) view.findViewById(R.id.imagePhoto);
        imageMore = (ImageView) view.findViewById(R.id.imageMore);
        imageMore.setOnClickListener(this);

        textFio = (TextView) view.findViewById(R.id.textFio);
        textMail = (TextView) view.findViewById(R.id.textMail);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void setEvents(List<Events> events) {
        adapter = new EventsListInAccountAdapter(getActivity(), R.layout.item_event_in_account, events);
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
                                Log.d(TAG, "Click btnExit");
                                presenterAccount.deleteAuthorization();
                                //new AccountAuthorization().deleteAuthorization();
                                Intent intentExit = new Intent(getActivity(), MainMapActivity.class);
                                startActivity(intentExit);
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

    @Override
    public void setUser(Users user) {
        textFio.setText(user.getFio());
        textMail.setText(user.getMail());
    }

    @Override
    public void onEditClick(int position) {
        Intent intent = new Intent(getActivity(), CreateEventActivity.class);
        intent.putExtra("idEvent", presenterAccount.getEventByPosition(position).getId());
        //intent.putExtra("idEvent", events.get(position).getId());
        this.startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        presenterAccount.deleteEvent(presenterAccount.getEventByPosition(position));
        Intent intentMainMapActivity = new Intent(getActivity(), MainMapActivity.class);
        startActivity(intentMainMapActivity);
    }

    @Override
    public void startMainActivity() {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenterAccount.detachView();
    }
}
