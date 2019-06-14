package com.starichenkov.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.starichenkov.data.Users;
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.eventmap.R;

public class ChangeAccountFragment extends Fragment implements View.OnClickListener {

    private TextView textViewRegistration;
    private EditText editFIO;
    private EditText editMail;
    private EditText editPassword;
    private Button buttonCreateAcc;
    private Users user;

    private static final String TAG = "MyLog";

    private CallBackInterfaceAccount mListener;

    private String nameFragment = "ChangeAccountFragment";

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

        mListener.setCurrentFragment(nameFragment);

        mListener.getAccountData();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonCreateAcc:
                Log.d(TAG, "onClick buttonCreateAcc()");
                user.setFio(editFIO.getText().toString());
                user.setMail(editMail.getText().toString());
                user.setPassword(editPassword.getText().toString());
                mListener.updateUser(user);
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

    public void setUser(Users user) {

        this.user = user;
        editFIO.setText(this.user.getFio());
        editMail.setText(this.user.getMail());
        editPassword.setText(this.user.getPassword());

    }
}
