package com.starichenkov.eventmap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class TopSearchBarFragment extends Fragment implements View.OnClickListener {

    private ImageButton ibtnDrawerOpener;
    private ImageButton ibtnFilter;
    private SearchView editSearch;

    private static final String TAG = "MyLog";

    private CallBackInterfaceMap mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_search_bar, null);

        ibtnDrawerOpener = (ImageButton) view.findViewById(R.id.ibtnDrawerOpener);
        ibtnDrawerOpener.setOnClickListener(this);

        editSearch = (SearchView) view.findViewById(R.id.editSearch);
        editSearch.setOnSearchClickListener(this);

        ibtnFilter = (ImageButton) view.findViewById(R.id.ibtnFilter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CallBackInterfaceMap) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackInterfaceMap");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ibtnDrawerOpener:
                mListener.openDrawer();
                Log.d(TAG, "Click ibtnDrawerOpener");
                break;

            case R.id.editSearch:
                Log.d(TAG, "Click editSearch");
                editSearch.setQuery("",false);
                editSearch.setIconified(true);
                mListener.OpenEventsList();
                break;
        }
    }

    /*@Override
    public boolean  onTouch(View v, MotionEvent event){
        //if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.editSearch:
                    Log.d(TAG, "Touch editSearch");
                    //editSearch.clearFocus();
                    editSearch.cancelPendingInputEvents();
                    editSearch.setQuery("", false);
                    editSearch.clearFocus();
                    mListener.OpenEventsList();
                    break;

            }
        //}
        return true;
    }*/
}
