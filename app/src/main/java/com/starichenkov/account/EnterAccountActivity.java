package com.starichenkov.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.view.IView;
import com.starichenkov.eventmap.MapFragment;
import com.starichenkov.eventmap.R;
import com.starichenkov.presenter.IPresenter;
import com.starichenkov.presenter.Presenter;

import java.util.List;

public class EnterAccountActivity extends Activity implements IView, OnClickListener {

    private IPresenter iPresenter;
    private static final String TAG = "MyLog";
    private EditText editMailEnter;
    private EditText editPasswordEnter;
    private Button buttonEnterAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_account);

        initView();
        iPresenter = new Presenter(this);
    }

    private void initView() {

        editMailEnter = (EditText) findViewById(R.id.editMailEnter);
        editPasswordEnter = (EditText) findViewById(R.id.editPasswordEnter);
        buttonEnterAcc = (Button) findViewById(R.id.buttonEnterAcc);
        buttonEnterAcc.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonEnterAcc:

                boolean test = iPresenter.findUser(editMailEnter.getText().toString(), editPasswordEnter.getText().toString());

                Log.d(TAG, "Click buttonEnterAcc");
                Log.d(TAG, "authorized = " + test);
                Intent intentMapsActivity = new Intent(this, MapFragment.class);
                startActivity(intentMapsActivity);
                break;

        }
    }

    @Override
    public void sendEvents(List<Events> events){
    }

    @Override
    public void sendBookMarks(List<BookMarks> bookMarks){
    }

    @Override
    public void detachView(){
        iPresenter.detachView();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detachView();
    }

}
