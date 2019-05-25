package com.starichenkov.BookMarksListView;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.squareup.picasso.Picasso;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.eventmap.R;

import java.util.ArrayList;
import java.util.List;

public class BookMarksListAdapter extends RecyclerView.Adapter<BookMarksListAdapter.MyViewHolder> {

    private List<Events> events;
    private Context mContext;
    private int mResourse;
    private static final String TAG = "MyLog";
    int lastPosition = -1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textNameEvent;
        TextView textTypeEvent;
        TextView textAddressEvent;
        ImageView imageEvent;
        LinearLayout llBookMark;


        public MyViewHolder(View view) {
            super(view);
            textNameEvent = (TextView) view.findViewById(R.id.textNameEvent);
            textTypeEvent = (TextView) view.findViewById(R.id.textTypeEvent);
            textAddressEvent = (TextView) view.findViewById(R.id.textAddressEvent);
            imageEvent = (ImageView) view.findViewById(R.id.imageEvent);
            llBookMark = (LinearLayout) view.findViewById(R.id.llBookMark);

        }

        public void loadImage(Uri uri){
            //Picasso.with(context).load(url).placeholder(R.drawable.placeholder).error(R.drawable.error_ph).into(this.image);
            Picasso.get().load(uri).error(R.drawable.event_map_logo).into(imageEvent);
        }
    }

    //Constructor
    public BookMarksListAdapter(Context mContext, int resource, List<Events> events){
        this.mContext = mContext;
        this.mResourse = resource;
        this.events = events;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookMarksListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mResourse, parent, false);
        return new MyViewHolder(view);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Events event = events.get(position);
        holder.textNameEvent.setText(event.nameEvent);
        holder.textTypeEvent.setText(event.typeEvent);
        holder.textAddressEvent.setText(event.addressEvent);
        //holder.imageEvent.setImageURI(Uri.parse(event.photoEvent));
        //Picasso.with(mContext).load(event.photoEvent).into(imageView);
        Log.d(TAG, "event.nameEvent: " + event.nameEvent);
        holder.loadImage(Uri.parse(event.photoEvent));
        setAnimation(holder.llBookMark, position);

    }

    private void setAnimation(View itemView, int position) {
        //Log.d(TAG, "position: " + position);
        //Log.d(TAG, "lastPosition: " + lastPosition);
        if (position > lastPosition){
            //Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.load_down_anim);
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            //Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.layout_animation_fall_down);
            itemView.startAnimation(animation);
            lastPosition = position;
        }/*else{
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.load_up_anim);
            itemView.startAnimation(animation);
            lastPosition = position;
        }*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return events.size();
    }


    /*private Context mContext;
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
    }*/
}
