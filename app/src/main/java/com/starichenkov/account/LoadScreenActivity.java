package com.starichenkov.account;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import com.starichenkov.eventmap.R;


public class LoadScreenActivity extends Activity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_screen);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }


}
