package com.starichenkov.account;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.starichenkov.RoomDB.Users;
import com.starichenkov.eventmap.R;

public class ChangeAccountFragment extends Fragment implements View.OnClickListener {

    private TextView textViewRegistration;
    private EditText editFIO;
    private EditText editMail;
    private EditText editPassword;
    private Button buttonCreateAcc;
    private TextInputLayout etPasswordLayout;

    private static final String TAG = "MyLog";

    private CallBackInterfaceAccount mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_registration, null);

        textViewRegistration = (TextView) view.findViewById(R.id.textViewRegistration);
        textViewRegistration.setText("Редактирование профиля");
        editFIO = (EditText) view.findViewById(R.id.editFIO);
        editMail = (EditText) view.findViewById(R.id.editMail);
        editPassword = (EditText) view.findViewById(R.id.editPassword);
        buttonCreateAcc = (Button) view.findViewById(R.id.buttonCreateAcc);
        buttonCreateAcc.setText("Сохранить");
        buttonCreateAcc.setOnClickListener(this);

        etPasswordLayout = (TextInputLayout) view.findViewById(R.id.etPasswordLayout);

        mListener.getAccountData();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonCreateAcc:
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

    public void setUser(Users user) {

        editFIO.setText(user.getFio());
        editMail.setText(user.getMail());
        editPassword.setText(user.getPassword());
        etPasswordLayout.setTe

    }
}
