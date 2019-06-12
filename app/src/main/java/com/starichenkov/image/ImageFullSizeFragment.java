package com.starichenkov.image;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.starichenkov.eventmap.R;

public class ImageFullSizeFragment extends Fragment {

    private ImageView imageViewFullScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.image_full_screen, null);

        imageViewFullScreen = (ImageView) view.findViewById(R.id.imageViewFullScreen);

        return view;

    }

    public void setImage(Uri uri){
        imageViewFullScreen.setImageURI(uri);
    }

}
