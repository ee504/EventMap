package com.starichenkov.eventmap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.starichenkov.presenter.Presenter;

public class CreateEventActivity extends Activity {

    private String[] TypeEvents = { "Спектакль", "Выставка", "Вечеринка", "Кинопоказ", "Концерт" };
    private Spinner spinnerTypeEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_event);

        initView();
        //iPresenter = new Presenter(this);


    }

    private void initView() {

        Spinner spinnerTypeEvent = (Spinner) findViewById(R.id.spinnerTypeEvent);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, TypeEvents);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeEvent.setAdapter(adapter);
        // заголовок

    }
}
