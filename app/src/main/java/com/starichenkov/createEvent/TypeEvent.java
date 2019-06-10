package com.starichenkov.createEvent;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeEvent {

    //private List<String> typeEvents = new ArrayList<String>();//( "Спектакль", "Выставка", "Вечеринка", "Кинопоказ", "Концерт" );
    private Map<String,String> dicTypeEvent = new HashMap<String,String>();

    public TypeEvent(){
        /*dicTypeEvent.put("Спектакль", "#4f5cce");
        dicTypeEvent.put("Выставка", "#64beff");
        dicTypeEvent.put("Вечеринка", "#ff6464");
        dicTypeEvent.put("Кинопоказ", "#ffa217");
        dicTypeEvent.put("Концерт", "#47ff06");*/

        dicTypeEvent.put("Спектакль", "ic_fiber_manual_record_spectacle_24dp");
        dicTypeEvent.put("Выставка", "ic_fiber_manual_record_exhibition_24dp");
        dicTypeEvent.put("Вечеринка", "ic_fiber_manual_record_party_24dp");
        dicTypeEvent.put("Кинопоказ", "ic_fiber_manual_record_film_24dp");
        dicTypeEvent.put("Концерт", "ic_fiber_manual_record_concert_24dp");
    }

    public Drawable getDrawable(Context context, String key){
        //return dicTypeEvent.get(key);
        return ContextCompat.getDrawable(context, context.getResources().getIdentifier(dicTypeEvent.get(key),"drawable", context.getPackageName()));
    }

    public ArrayList<String> getArrayOfTypeEvent(){
        ArrayList<String> typeEvents = new ArrayList<String>(dicTypeEvent.keySet());

        //for(String str : dicTypeEvent.keySet()){
        //}
        return typeEvents;
    }
}
