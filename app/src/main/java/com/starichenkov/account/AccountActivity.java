package com.starichenkov.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.RoomDB.Users;
import com.starichenkov.createEvent.CreateEventActivity;
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.eventmap.R;
import com.starichenkov.presenter.IPresenter;
import com.starichenkov.presenter.Presenter;
import com.starichenkov.view.IView;

import java.util.List;

public class AccountActivity extends FragmentActivity implements IView, CallBackInterfaceAccount, EventsListInAccountAdapter.OnEventListener {

    private IPresenter presenter;

    private static final String TAG = "MyLog";

    private Fragment accountFragment;
    private ChangeAccountFragment changeAccountFragment;

    private List<Events> events;

    private String currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_event);

        accountFragment = new AccountFragment();
        changeAccountFragment = new ChangeAccountFragment();

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCreateEvent, accountFragment)
                .show(accountFragment)
                .commit();
        //getSupportFragmentManager().popBackStackImmediate();
        getSupportFragmentManager().executePendingTransactions();
        presenter = new Presenter(this);
    }

    @Override
    public void getAccountData(){
        presenter.getCurrentUser();
    }

    @Override
    public void openChangeAccountFragment(){

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCreateEvent, changeAccountFragment)
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void sendEvents(List<Events> events){

        this.events = events;
        AccountFragment accountFragment = (AccountFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        accountFragment.sendEvents(this.events);
    }

    @Override
    public void sendBookMarks(List<BookMarks> bookMarks) {

    }

    @Override
    public void sendUser(Users user){
        if(currentFragment == "ChangeAccountFragment") {
            ChangeAccountFragment changeAccountFragment = (ChangeAccountFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            changeAccountFragment.setUser(user);
        }else if(currentFragment == "AccountFragment"){
            AccountFragment accountFragment = (AccountFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
            accountFragment.setUser(user);
        }
    }

    @Override
    public void onEditClick(int position) {
        Log.d(TAG, "onEditClick(): " + events.get(position).getNameEvent());

        Intent intent = new Intent(this, CreateEventActivity.class);
        intent.putExtra("idEvent", events.get(position).getId());
        this.startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        /*Log.d(TAG, "position: " + position);
        AccountFragment accountFragment = (AccountFragment)
                getSupportFragmentManager().findFragmentById(R.id.frgmCreateEvent);
        accountFragment.onEditClick(position);*/
        Log.d(TAG, "onDeleteClick(): " + events.get(position).getNameEvent());
        //presenter.deleteEventById(events.get(position).id);
        presenter.deleteEvent(events.get(position));
        Intent intentLoadScreenActivity = new Intent(this, LoadScreenActivity.class);
        startActivity(intentLoadScreenActivity);
    }

    @Override
    public void updateUser(Users user){
        presenter.updateUser(user);
    }

    @Override
    public void getUserEvents(){
        presenter.getUserEvents();
    }

    @Override
    public void setCurrentFragment(String nameFragment){
        this.currentFragment = nameFragment;
    }

    @Override
    public void detachView(){
        presenter.detachView();
    }

    @Override
    public void startMainActivity(){
        Intent intentMainMapActivity = new Intent(this, MainMapActivity.class);
        startActivity(intentMainMapActivity);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detachView();
    }
}
