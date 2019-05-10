package com.starichenkov.eventmap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.view.View.OnTouchListener;

import java.io.File;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CreateEventMainFragment extends Fragment implements View.OnClickListener {

    private String[] TypeEvents = { "Спектакль", "Выставка", "Вечеринка", "Кинопоказ", "Концерт" };
    private static final String TAG = "MyLog";

    private EditText editNameEvent;
    private EditText editDateEvent;
    private Spinner spinnerTypeEvent;
    private EditText editAddressEvent;
    private Button buttonCreateEvent;
    private ImageView imageView;
    private Button buttonTakePhoto;
    private Button buttonDeletePhoto;

    private File directory;
    final int REQUEST_CODE_PHOTO = 1;

    private CallBackInterfaceCreateEvent mListener;

    /*public interface OnClickAddressListener {
        void OnClickAddress(String link);
        void OpenPlaceAutocomplete();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, null);
        createDirectory();

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.event_map_logo);

        EditText editNameEvent = (EditText) view.findViewById(R.id.editNameEvent);
        EditText editDateEvent = (EditText) view.findViewById(R.id.editDateEvent);
        Spinner spinnerTypeEvent = (Spinner) view.findViewById(R.id.spinnerTypeEvent);
        EditText editAddressEvent = (EditText) view.findViewById(R.id.editAddressEvent);
        editAddressEvent.setOnClickListener(this);
        editAddressEvent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        Log.d(TAG, "Touch editAddressEvent");
                        mListener.OnClickAddress("Click editAddressEvent 123 test");
                        mListener.OpenPlaceAutocomplete();
                        break;
            }
                return true;
        }});
        Button buttonCreateEvent = (Button) view.findViewById(R.id.buttonCreateEvent);
        buttonCreateEvent.setOnClickListener(this);

        Button buttonTakePhoto = (Button) view.findViewById(R.id.buttonTakePhoto);
        buttonTakePhoto.setOnClickListener(this);

        Button buttonDeletePhoto = (Button) view.findViewById(R.id.buttonDeletePhoto);
        buttonDeletePhoto.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, TypeEvents);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeEvent.setAdapter(adapter);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCreateEvent:
                Log.d(TAG, "Click buttonCreateEvent");
                break;

            case R.id.buttonTakePhoto:
                Log.d(TAG, "Click buttonTakePhoto");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
                startActivityForResult(intent, REQUEST_CODE_PHOTO);
                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CallBackInterfaceCreateEvent) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Photo uri: " + intent.getData());
                    imageView.setImageURI(intent.getData());
                    /*Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
                                    + bitmap.getHeight());
                            ivPhoto.setImageBitmap(bitmap);
                        }
                    }*/
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }
    }

    private Uri generateFileUri() {
        File file = null;
        file = new File(directory.getPath() + "/" + "photo_"
                + System.currentTimeMillis() + ".jpg");
        Log.d(TAG, "fileName = " + file);
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyFolder");
        if (!directory.exists())
            directory.mkdirs();
    }

}
