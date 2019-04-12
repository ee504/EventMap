package com.starichenkov.presenter;

import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.starichenkov.eventmap.R;

public class Presenter {

    private static final String TAG = "MyLog";

    private View mView;

    public Presenter(View view){
        this.mView = view;
    }
}
