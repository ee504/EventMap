package com.starichenkov.createEvent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.util.Calendar;

public class MDatePicker {

    private Context context;
    private Calendar dateAndTime;
    DatePickerCallBack callBack;

    public MDatePicker(Context context, DatePickerCallBack callBack){
        this.context = context;
        dateAndTime= Calendar.getInstance();
        this.callBack = callBack;
    }

    public Calendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDate() {
        new DatePickerDialog(context, myDateCallBack,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setTime() {
        new TimePickerDialog(context, myTimeCallBack,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка обработчика выбора даты
    private DatePickerDialog.OnDateSetListener myDateCallBack = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setTime();
        }
    };

    private TimePickerDialog.OnTimeSetListener myTimeCallBack=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            callBack.setInitialDateTime(dateAndTime);
        }
    };

}
