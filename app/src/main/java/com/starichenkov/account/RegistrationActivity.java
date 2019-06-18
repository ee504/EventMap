package com.starichenkov.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import com.starichenkov.contracts.ContractRegistration;
import com.starichenkov.data.BookMarks;
import com.starichenkov.data.Events;
import com.starichenkov.data.Users;
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.presenter.myPresenters.PresenterRegistration;
import com.starichenkov.view.IView;
import com.starichenkov.eventmap.R;
import com.starichenkov.presenter.IPresenter;
import com.starichenkov.presenter.Presenter;

import java.util.List;

//import static com.google.android.gms.wearable.DataMap.TAG;

public class RegistrationActivity extends Activity implements ContractRegistration.View, OnClickListener {

    private PresenterRegistration iPresenter;
    private EditText editFIO;
    private EditText editMail;
    private EditText editPassword;
    private Button buttonCreateAcc;

    private final String TAG = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        initView();
        iPresenter = new PresenterRegistration(this);
    }

    private void initView() {

        editFIO = (EditText) findViewById(R.id.editFIO);
        editMail = (EditText) findViewById(R.id.editMail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        buttonCreateAcc = (Button) findViewById(R.id.buttonCreateAcc);
        buttonCreateAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // по id определяем кнопку, вызвавшую этот обработчик
        Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
        switch (v.getId()) {

            case R.id.buttonCreateAcc:
                Log.d(TAG, "Create account");
                iPresenter.createUser(editFIO.getText().toString(), editMail.getText().toString(), editPassword.getText().toString());
                Intent intent = new Intent(this, MainMapActivity.class);
                startActivity(intent);
                break;
        }
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
