package com.starichenkov.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.starichenkov.data.Users;
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.eventmap.R;
import com.starichenkov.presenter.PresenterChangeAccount;
import com.starichenkov.view.interfaces.IViewCurrentUser;

public class ChangeAccountFragment extends Fragment implements View.OnClickListener, IViewCurrentUser {

    private TextView textViewRegistration;
    private EditText editFIO;
    private EditText editMail;
    private EditText editPassword;
    private Button buttonCreateAcc;

    private String TAG;

    private CallBackInterfaceAccount mListener;

    private PresenterChangeAccount presenterAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_registration, null);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        TAG = getResources().getString(R.string.TAG);
        initView(view);
        presenterAccount = new PresenterChangeAccount(this, new AccountAuthorization(getContext()));
        //get data about current user
        presenterAccount.getCurrentUser();

        return view;
    }

    private void initView(View view){
        textViewRegistration = (TextView) view.findViewById(R.id.textViewRegistration);
        textViewRegistration.setText("Редактирование");

        editFIO = (EditText) view.findViewById(R.id.editFIO);
        editMail = (EditText) view.findViewById(R.id.editMail);
        editPassword = (EditText) view.findViewById(R.id.editPassword);

        buttonCreateAcc = (Button) view.findViewById(R.id.buttonCreateAcc);
        buttonCreateAcc.setText("Сохранить");
        buttonCreateAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //save changes
            case R.id.buttonCreateAcc:
                Log.d(TAG, "onClick buttonCreateAcc()");
                presenterAccount.updateUser(new Users(editFIO.getText().toString(), editMail.getText().toString(), editPassword.getText().toString()));
                Intent intentExit = new Intent(getActivity(), MainMapActivity.class);
                startActivity(intentExit);
                break;
        }
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

        editFIO.setText(user.getFio());
        editMail.setText(user.getMail());
        editPassword.setText(user.getPassword());

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detachView();
    }

    @Override
    public void detachView() {
        presenterAccount.detachView();
    }
}
