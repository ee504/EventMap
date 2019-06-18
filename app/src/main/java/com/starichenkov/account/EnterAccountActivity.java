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
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.presenter.PresenterEnterAccount;
import com.starichenkov.eventmap.R;

public class EnterAccountActivity extends Activity implements ContractEnterAccount.View, OnClickListener {

    private PresenterEnterAccount iPresenter;
    private String TAG;
    private EditText editMailEnter;
    private EditText editPasswordEnter;
    private Button buttonEnterAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_account);
        TAG = getResources().getString(R.string.TAG);

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
        //find user by email and password
        switch (v.getId()) {
            case R.id.buttonEnterAcc:
                Log.d(TAG, "Click buttonEnterAcc");
                iPresenter.findUser(editMailEnter.getText().toString(), editPasswordEnter.getText().toString());
                Intent intentLoadScreenActivity = new Intent(this, LoadScreenActivity.class);
                startActivity(intentLoadScreenActivity);
                break;

        }
    }
    //open google map
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
