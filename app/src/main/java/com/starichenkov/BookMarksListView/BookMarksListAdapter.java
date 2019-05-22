package com.starichenkov.BookMarksListView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.starichenkov.eventmap.R;

import java.util.ArrayList;

public class BookMarksListAdapter extends ArrayAdapter<BookMarksListViewData> {

    private Context mContext;
    private int mResourse;

    public BookMarksListAdapter(Context context, int resource, ArrayList<BookMarksListViewData> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mResourse = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        String nameEvent = getItem(position).getNameEvent();
        String typeEvent = getItem(position).getTypeEvent();
        String addressEvent = getItem(position).getAddressEvent();

        BookMarksListViewData bookMarksListViewData= new BookMarksListViewData(nameEvent, typeEvent, addressEvent);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResourse, parent, false);

        TextView textNameEvent = (TextView) convertView.findViewById(R.id.textNameEvent);
        TextView textTypeEvent = (TextView) convertView.findViewById(R.id.textTypeEvent);
        TextView textAddressEvent = (TextView) convertView.findViewById(R.id.textAddressEvent);

        textNameEvent.setText(nameEvent);
        textTypeEvent.setText(typeEvent);
        textAddressEvent.setText(addressEvent);

        return convertView;
    }
}
