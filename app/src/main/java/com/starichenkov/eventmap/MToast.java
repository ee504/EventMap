package com.starichenkov.eventmap;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MToast {

    private Context context;

    public MToast(Context context){
        this.context = context;
    }

    public void showShortBottomMessage(String message){
        Toast toast = Toast.makeText(context, message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.show();
    }

    public void showShortCenterMessage(String message){
        Toast toast = Toast.makeText(context, message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void showLongBottomMessage(String message){
        Toast toast = Toast.makeText(context, message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.show();
    }

    public void showLongCenterMessage(String message){
        Toast toast = Toast.makeText(context, message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
