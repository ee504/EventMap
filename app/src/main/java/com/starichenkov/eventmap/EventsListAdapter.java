package com.starichenkov.eventmap;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starichenkov.data.Events;
import com.starichenkov.createEvent.TypeEvent;

import java.util.ArrayList;
import java.util.List;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.MyViewHolder> {

    private List<Events> events;
    private List<Events> eventsCopy;
    private Context mContext;
    private int mResourse;
    private static final String TAG = "MyLog";
    int lastPosition = -1;
    TypeEvent typeEvent;

    //private Map<String,String> dictionary = new HashMap<String,String>();


    private OnEventListener mOnEventListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        TextView textNameEvent;
        TextView textTypeEvent;
        TextView textAddressEvent;
        ImageView imageEvent;
        LinearLayout llBookMark;
        ImageView imageDot;

        OnEventListener onEventListener;

        public MyViewHolder(View view, OnEventListener onEventListener) {
            super(view);
            textNameEvent = (TextView) view.findViewById(R.id.textNameEvent);
            textTypeEvent = (TextView) view.findViewById(R.id.textTypeEvent);
            textAddressEvent = (TextView) view.findViewById(R.id.textAddressEvent);
            imageEvent = (ImageView) view.findViewById(R.id.imageEvent);
            llBookMark = (LinearLayout) view.findViewById(R.id.llBookMark);
            imageDot = (ImageView) view.findViewById(R.id.imageDot);

            this.onEventListener = onEventListener;
            itemView.setOnClickListener(this);
        }

        public void loadImage(String url){
            //Picasso.with(context).load(url).placeholder(R.drawable.placeholder).error(R.drawable.error_ph).into(this.image);
            Picasso.get().load(url).placeholder(R.drawable.event_map_logo).error(R.drawable.event_map_logo).into(imageEvent);
        }

        @Override
        public void onClick(View v) {
            onEventListener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnEventListener{
        void onEventClick(int position);
    }

    //Constructor
    public EventsListAdapter(Context mContext, Fragment fragment, int resource, List<Events> events){
        this.mContext = mContext;
        this.mResourse = resource;
        this.events = events;
        this.eventsCopy = new ArrayList<Events>(events);
        this.mOnEventListener = (OnEventListener)fragment;
        this.typeEvent = new TypeEvent();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mResourse, parent, false);
        return new MyViewHolder(view, mOnEventListener);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Events event = events.get(position);
        holder.textNameEvent.setText(event.getNameEvent());
        holder.textTypeEvent.setText(event.getTypeEvent());
        holder.textAddressEvent.setText(event.getAddressEvent());
        holder.imageDot.setImageDrawable(typeEvent.getDrawable(mContext, event.getTypeEvent()));
        //holder.imageEvent.setImageURI(Uri.parse(event.photoEvent));
        //Picasso.with(mContext).load(event.photoEvent).into(imageView);
        Log.d(TAG, "event.nameEvent: " + event.getNameEvent());
        holder.loadImage(event.getPhotoEvent());
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

    public void filter(String text) {
        events.clear();
        if(text.isEmpty()){
            events.addAll(eventsCopy);
        } else{
            text = text.toLowerCase();
            for(Events event: eventsCopy){
                if(event.getNameEvent().toLowerCase().contains(text)){
                    events.add(event);
                }
            }
        }
        notifyDataSetChanged();
    }

}
