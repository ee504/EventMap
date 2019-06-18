package com.starichenkov.createEvent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
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
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.contracts.ContractCreateEvent;
import com.starichenkov.data.Events;
import com.starichenkov.eventmap.BuildConfig;
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.image.ChangeImage;
import com.starichenkov.image.CreateImageFile;
import com.starichenkov.eventmap.R;
import com.starichenkov.presenter.myPresenters.PresenterCreateEvent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class CreateEventMainFragment extends Fragment implements OnClickListener, OnTouchListener, DatePickerCallBack, ContractCreateEvent.View {

    private TypeEvent typeEvent;
    private static final String TAG = "MyLog";

    private TextView textViewCreateEvent;
    private EditText editNameEvent;
    private EditText editDescriptionEvent;
    private EditText editDateEvent;
    private Spinner spinnerTypeEvent;
    private EditText editAddressEvent;
    private Button buttonCreateEvent;
    private ImageView imageView;
    private Button buttonTakePhoto;
    private Button buttonDeletePhoto;
    
    final int REQUEST_TAKE_PHOTO = 1;

    private Uri photoUriMain;
    private String photoURI;
    private Uri newPhotoURI;

    private String nameEvent;
    private String dateEvent;
    private String addressEvent;
    private LatLng latLngEvent;

    private boolean createEvent;

    private ArrayAdapter<String> adapter;

    private CreateImageFile createImageFile;

    private CallBackInterfaceCreateEvent mListener;

    private PresenterCreateEvent presenterEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, null);

        typeEvent = new TypeEvent();
        presenterEvent = new PresenterCreateEvent(this);
        createImageFile = new CreateImageFile(getActivity());

        String idEvent = mListener.getIdEvent();

        initView(view);
        if(idEvent != null){
            presenterEvent.getEventById(idEvent);
        }

        return view;

    }

    private void initView(View view) {

        imageView = (ImageView) view.findViewById(R.id.imageView);
        if(photoURI == null) {
            imageView.setImageResource(R.drawable.event_map_logo);
        }else{
            Picasso.get().load(photoURI).placeholder(R.drawable.event_map_logo).error(R.drawable.event_map_logo).into(imageView);
        }

        textViewCreateEvent = (TextView) view.findViewById(R.id.textViewCreateEvent);
        editNameEvent = (EditText) view.findViewById(R.id.editNameEvent);
        editDescriptionEvent = (EditText) view.findViewById(R.id.editDescriptionEvent);
        editDateEvent = (EditText) view.findViewById(R.id.editDateEvent);
        spinnerTypeEvent = (Spinner) view.findViewById(R.id.spinnerTypeEvent);
        editAddressEvent = (EditText) view.findViewById(R.id.editAddressEvent);
        editAddressEvent.setOnTouchListener(this);
        editDateEvent = (EditText) view.findViewById(R.id.editDateEvent);
        editDateEvent.setOnTouchListener(this);

        buttonCreateEvent = (Button) view.findViewById(R.id.buttonCreateEvent);
        buttonCreateEvent.setOnClickListener(this);

        buttonTakePhoto = (Button) view.findViewById(R.id.buttonTakePhoto);
        buttonTakePhoto.setOnClickListener(this);

        buttonDeletePhoto = (Button) view.findViewById(R.id.buttonDeletePhoto);
        buttonDeletePhoto.setOnClickListener(this);

        ArrayList<String> spinnerList = typeEvent.getArrayOfTypeEvent();
        spinnerList.add(0, "Тип мероприятия:");
        adapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_item,spinnerList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTypeEvent.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCreateEvent:
                Log.d(TAG, "Click buttonCreateEvent");
                if(newPhotoURI != null){
                    if(presenterEvent.getCurrentEvent().getPhotoEvent()!=null){
                        presenterEvent.deletePhoto(presenterEvent.getCurrentEvent().getPhotoEvent());
                        presenterEvent.getCurrentEvent().setPhotoEvent(null);
                        photoURI = newPhotoURI.toString();

                    }else if(photoURI != null){
                        new File(Uri.parse(photoURI).getPath()).delete();
                        photoURI = newPhotoURI.toString();
                    }else{
                        photoURI = newPhotoURI.toString();
                    }
                }
                presenterEvent.createEvent(new Events(new AccountAuthorization(getActivity()).getIdUser(),photoURI, editNameEvent.getText().toString(), editDescriptionEvent.getText().toString(),
                        dateEvent, spinnerTypeEvent.getSelectedItem().toString(), editAddressEvent.getText().toString(), latLngEvent.latitude, latLngEvent.longitude));
                createEvent = true;

                Intent intentMainMapActivity = new Intent(getActivity(), MainMapActivity.class);
                startActivity(intentMainMapActivity);
                break;

            case R.id.buttonTakePhoto:
                dispatchTakePictureIntent();
                break;

            case R.id.buttonDeletePhoto:
                Log.d(TAG, "Click buttonDeletePhoto");
                imageView.setImageResource(R.drawable.event_map_logo);
                //presenterEvent.onClickDeletePhoto();
                if(presenterEvent.getCurrentEvent().getPhotoEvent()!=null){
                    presenterEvent.deletePhoto(presenterEvent.getCurrentEvent().getPhotoEvent());
                    presenterEvent.getCurrentEvent().setPhotoEvent(null);
                }else if(newPhotoURI != null) {
                    new File(newPhotoURI.getPath()).delete();
                }
                break;

        }
    }

    @Override
    public boolean  onTouch(View v, MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.editAddressEvent:
                    mListener.OpenPlaceAutocomplete();
                    break;

                case R.id.editDateEvent:
                    //presenterEvent.onEditDateEvent(getActivity());
                    MDatePicker datePicker = new MDatePicker(getActivity(), this);
                    datePicker.setDate();
                    //setDate();
            }
        }
        return true;
    }

    // установка даты и времени
    @Override
    public void setInitialDateTime(Calendar dateAndTime) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm");
        dateEvent = myDateFormat.format(dateAndTime.getTime());
        editDateEvent.setText(dateEvent);

    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile.getTempImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "CreateEventMainFragment Error: " + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d(TAG, "photoFile: " + photoFile.getAbsolutePath());
                //photoURI = Uri.fromFile(photoFile);
                photoUriMain = FileProvider.getUriForFile(getActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUriMain);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CallBackInterfaceCreateEvent) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackInterfaceCreateEvent");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        //presenterEvent.onActivityResult(getActivity(), requestCode, resultCode);
        //imageView.setImageURI(newPhotoURI);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.d(TAG, "intent is not null");
            Log.d(TAG, "photoUriMain: " + photoUriMain);

            if(newPhotoURI != null) {
                new File(newPhotoURI.getPath()).delete();
            }

            ChangeImage image = new ChangeImage(getContext(), photoUriMain);
            newPhotoURI = image.getImage300x300();
            imageView.setImageURI(newPhotoURI);
            getActivity().getContentResolver().delete(photoUriMain, null, null);
        }
    }


    public void SetEventAddress(Address address, LatLng latLng){
        addressEvent = address.getLocality() + ", " + address.getThoroughfare() + ", " + address.getFeatureName();
        latLngEvent = latLng;
        editAddressEvent.setText(addressEvent);

    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        if(createEvent == false && newPhotoURI != null){
                new File(newPhotoURI.getPath()).delete();
        }
        detachView();
    }

    @Override
    public void setCurrentEvent(Events event) {
        Log.d(TAG, "CreateEventMainFragment setCurrentEvent");
        latLngEvent = new LatLng(event.getLatitude(), event.getLongitude());
        photoURI = event.getPhotoEvent();
        //photoURIFullSize = Uri.parse(event.getPhotoEventFullSize());
        dateEvent = event.getDateEvent();

        textViewCreateEvent.setText("Редактирование мероприятия");

        //imageView.setImageURI(photoURI);
        Picasso.get().load(photoURI).placeholder(R.drawable.event_map_logo).error(R.drawable.event_map_logo).into(imageView);
        editNameEvent.setText(event.getNameEvent());
        editDescriptionEvent.setText(event.getDescriptionEvent());
        editDateEvent.setText(event.getDateEvent());
        spinnerTypeEvent.setSelection(adapter.getPosition(event.getTypeEvent()));
        editAddressEvent.setText(event.getAddressEvent());
        buttonCreateEvent.setText("Изменить мероприятие");
    }

    @Override
    public void detachView() {
        presenterEvent.detachView();
    }
}
