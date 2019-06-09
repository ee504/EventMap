package com.starichenkov.eventmap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class SearchBarEventsFragment extends Fragment implements View.OnClickListener{

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
        ibtnDrawerOpener.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        ibtnDrawerOpener.setOnClickListener(this);

        editSearch = (SearchView) view.findViewById(R.id.editSearch);
        editSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mListener.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mListener.filter(newText);
                return true;
            }
        });
        //editSearch.setOnTouchListener(this);

        ibtnFilter = (ImageButton) view.findViewById(R.id.ibtnFilter);
        return view;
    }

    @Override
    public void onClick(View v) {
        // по id определяем кнопку, вызвавшую этот обработчик
        //Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
        switch (v.getId()) {

            case R.id.ibtnDrawerOpener:
                Log.d(TAG, "Click back");
                mListener.back();
                break;
        }
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
}
