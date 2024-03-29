package com.starichenkov.account;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starichenkov.data.Events;
import com.starichenkov.createEvent.TypeEvent;
import com.starichenkov.eventmap.R;

import java.util.ArrayList;
import java.util.List;

public class EventsListInAccountAdapter extends RecyclerView.Adapter<EventsListInAccountAdapter.MyViewHolder> {

    private List<Events> events;
    private List<Events> eventsCopy;
    private Context mContext;
    private int mResourse;
    int lastPosition = -1;
    TypeEvent typeEvent;

    private OnEventListener mOnEventListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        TextView textNameEvent;
        TextView textTypeEvent;
        //TextView textAddressEvent;
        ImageView imageEvent;
        LinearLayout llBookMark;
        Button buttonEdit;
        Button buttonDelete;
        ImageView imageDot;

        OnEventListener onEventListener;

        public MyViewHolder(View view, OnEventListener onEventListener) {
            super(view);
            textNameEvent = (TextView) view.findViewById(R.id.textNameEvent);
            textTypeEvent = (TextView) view.findViewById(R.id.textTypeEvent);
            //textAddressEvent = (TextView) view.findViewById(R.id.textAddressEvent);
            imageEvent = (ImageView) view.findViewById(R.id.imageEvent);
            imageDot = (ImageView) view.findViewById(R.id.imageDot);

            llBookMark = (LinearLayout) view.findViewById(R.id.llBookMark);

            buttonEdit = (Button) view.findViewById(R.id.buttonEdit);
            buttonDelete = (Button) view.findViewById(R.id.buttonDelete);

            this.onEventListener = onEventListener;
            buttonEdit.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        public void loadImage(String url){
            //Picasso.with(context).load(url).placeholder(R.drawable.placeholder).error(R.drawable.error_ph).into(this.image);
            Picasso.get().load(url).placeholder(R.drawable.event_map_logo).error(R.drawable.event_map_logo).into(imageEvent);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //edit event
                case R.id.buttonEdit:
                    onEventListener.onEditClick(getAdapterPosition());
                    break;
                //delete event
                case R.id.buttonDelete:
                    onEventListener.onDeleteClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnEventListener{
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    //Constructor
    public EventsListInAccountAdapter(Context mContext, int resource, EventsListInAccountAdapter.OnEventListener listener, List<Events> events){
        this.mContext = mContext;
        this.mResourse = resource;
        this.events = events;
        this.eventsCopy = new ArrayList<Events>(events);
        this.mOnEventListener = listener;
        this.typeEvent = new TypeEvent();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventsListInAccountAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        holder.imageDot.setImageDrawable(typeEvent.getDrawable(mContext, event.getTypeEvent()));
        holder.loadImage(event.getPhotoEvent());
        setAnimation(holder.llBookMark, position);

    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            itemView.startAnimation(animation);
            lastPosition = position;
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return events.size();
    }

}
