package com.starichenkov.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.starichenkov.contracts.ContractEnterAccount;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.presenter.myPresenters.PresenterEnterAccount;
import com.starichenkov.view.IView;
import com.starichenkov.eventmap.R;
import com.starichenkov.presenter.IPresenter;
import com.starichenkov.presenter.Presenter;

import java.util.List;

public class EnterAccountActivity extends Activity implements ContractEnterAccount.View, OnClickListener {

    private PresenterEnterAccount iPresenter;
    private static final String TAG = "MyLog";
    private EditText editMailEnter;
    private EditText editPasswordEnter;
    private Button buttonEnterAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_account);

        initView();
        iPresenter = new PresenterEnterAccount(this, new AccountAuthorization(this));
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
                Log.d(TAG, "Click buttonEnterAcc");
                iPresenter.findUser(editMailEnter.getText().toString(), editPasswordEnter.getText().toString());
                Intent intentLoadScreenActivity = new Intent(this, LoadScreenActivity.class);
                startActivity(intentLoadScreenActivity);
                break;

        }
    }

    @Override
    public void startMainActivity(){
        Intent intentMainMapActivity = new Intent(this, MainMapActivity.class);
        startActivity(intentMainMapActivity);
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
