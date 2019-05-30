package com.starichenkov.eventmap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.starichenkov.BookMarksListView.BookMarksListAdapter;
import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.presenter.Presenter;
import com.starichenkov.view.CallBackFromDB;
import com.starichenkov.view.IView;

import java.util.List;


public class BookMarksList extends Activity implements IView, CallBackFromDB, BookMarksListAdapter.OnEventListener {

    private static final String TAG = "MyLog";

    // имена атрибутов для Map
    final String ATTRIBUTE_IMZGE_EVENT = "imageEvent";
    final String ATTRIBUTE_NAME_EVENT = "textNameEvent";
    final String ATTRIBUTE_TYPE_EVENT = "textTypeEvent";
    final String ATTRIBUTE_ADDRESS_EVENT = "textAddressEvent";

    private ListView lvBookMarks;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Presenter presenter;

    private List<Events> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_marks);

        //lvBookMarks = (ListView) findViewById(R.id.lvBookMarks);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        presenter = new Presenter(this, this.getLocalClassName());
        presenter.getEventsFromBookmarks();
    }

    public void sendEvents(List<Events> events){
        this.events = events;
        setEvents(events);
    }

    /*private void setEvents(List<Events> events) {
        // упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(events.size());
        Map<String, Object> m;
        for (Events event : events) {
            Log.d(TAG, "event id: " + event.id);
            Log.d(TAG, "event name: " + event.nameEvent);
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_IMZGE_EVENT, event.photoEvent);
            m.put(ATTRIBUTE_NAME_EVENT, event.nameEvent);
            m.put(ATTRIBUTE_TYPE_EVENT, event.typeEvent);
            m.put(ATTRIBUTE_ADDRESS_EVENT, event.addressEvent);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_IMZGE_EVENT, ATTRIBUTE_NAME_EVENT,
                ATTRIBUTE_TYPE_EVENT, ATTRIBUTE_ADDRESS_EVENT };
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.imageEvent, R.id.textNameEvent, R.id.textTypeEvent,  R.id.textAddressEvent};

        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_event,
                from, to);

        lvBookMarks.setAdapter(sAdapter);
    }*/

    private void setEvents(List<Events> events) {

        Log.d(TAG, "BookMarksList setEvents()");

        /*ArrayList<BookMarksListViewData> eventsList = new ArrayList<>();
        for (Events event : events) {

            //Log.d(TAG, "event id: " + event.id);
            //Log.d(TAG, "event name: " + event.nameEvent);
            eventsList.add(new BookMarksListViewData(event.nameEvent, event.typeEvent, event.addressEvent));
        }*/

        BookMarksListAdapter adapter = new BookMarksListAdapter(this, R.layout.item_event, events);
        //lvBookMarks.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
    }

    public void sendBookMarks(List<BookMarks> bookMarks){

    }

    @Override
    public void onEventClick(int position) {
        Log.d(TAG, "events.get(position).id: " + events.get(position).id);
        //events.get(position).nameEvent;
        //Intent intentBookMarks = new Intent(this, MapsActivity.class);
        //startActivity(intentBookMarks);
    }
}
