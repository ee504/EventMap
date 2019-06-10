package com.starichenkov.createEvent;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.RoomDB.Users;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.eventmap.BuildConfig;
import com.starichenkov.eventmap.MainMapActivity;
import com.starichenkov.image.ChangeImage;
import com.starichenkov.view.IView;
import com.starichenkov.eventmap.MapFragment;
import com.starichenkov.eventmap.R;
import com.starichenkov.presenter.IPresenter;
import com.starichenkov.presenter.Presenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CreateEventMainFragment extends Fragment implements OnClickListener, OnTouchListener {

    //private String[] TypeEvents = { "Спектакль", "Выставка", "Вечеринка", "Кинопоказ", "Концерт" };
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
    //private String mCurrentPhotoPath;

    private Calendar dateAndTime= Calendar.getInstance();

    private Uri photoUriMain;
    private Uri photoURI;
    private Uri photoURIFullSize;
    private Uri newPhotoURI;
    private Uri newPhotoURIFullSize;

    //private Bitmap bitmapPhoto;
    private String nameEvent;
    private String dateEvent;
    //private String typeEvent;
    private String addressEvent;
    private LatLng latLngEvent;

    private boolean createEvent;

    private ArrayAdapter<String> adapter;



    private CallBackInterfaceCreateEvent mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, null);

        createEvent = false;

        typeEvent = new TypeEvent();

        Log.d(TAG, "start CreateEventMainFragment");
        Log.d(TAG, "photoURI: " + photoURI);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        if(photoURI == null) {
            imageView.setImageResource(R.drawable.event_map_logo);
        }else{
            //imageView.setImageBitmap(bitmapPhoto);
            imageView.setImageURI(photoURI);
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

        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, typeEvent.getArrayOfTypeEvent());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeEvent.setAdapter(adapter);

        mListener.getEvent();

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCreateEvent:
                Log.d(TAG, "Click buttonCreateEvent");
                Log.d(TAG, "Создать мероприятие");
                if(newPhotoURI != null){
                    if(photoURI != null){
                        new File(photoURI.getPath()).delete();
                        photoURI = newPhotoURI;

                        new File(photoURIFullSize.getPath()).delete();
                        photoURIFullSize = newPhotoURIFullSize;

                    }else{
                        photoURI = newPhotoURI;
                        photoURIFullSize = newPhotoURIFullSize;
                    }
                }
                mListener.createEvent(new Events(new AccountAuthorization().getIdUser(), photoURI.toString(), photoURIFullSize.toString(), editNameEvent.getText().toString(), editDescriptionEvent.getText().toString(),
                        dateEvent, spinnerTypeEvent.getSelectedItem().toString(), editAddressEvent.getText().toString(), latLngEvent.latitude, latLngEvent.longitude));
                Log.d(TAG, "Список переменных");
                Log.d(TAG,
                        "id = " + new AccountAuthorization().getIdUser() +
                                ", photoURI = " + photoURI.toString() +
                                ", editNameEvent = " + editNameEvent.getText().toString() +
                                ", dateEvent = " + dateEvent +
                                ", TypeEvent = " + spinnerTypeEvent.getSelectedItem().toString() +
                                ", addressEvent = " + addressEvent +
                                ", latitude = " + latLngEvent.latitude +
                                ", longitude = " + latLngEvent.longitude);
                createEvent = true;
                Intent intent = new Intent(getActivity(), MainMapActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonTakePhoto:
                Log.d(TAG, "Click buttonTakePhoto");
                dispatchTakePictureIntent();
                break;

            case R.id.buttonDeletePhoto:
                Log.d(TAG, "Click buttonDeletePhoto");
                imageView.setImageResource(R.drawable.event_map_logo);
                //getActivity().getContentResolver().delete(photoURI, null, null);
                //getActivity().getContentResolver().delete(photoURIFullSize, null, null);
                if(newPhotoURI != null) {
                    new File(newPhotoURI.getPath()).delete();
                    new File(newPhotoURIFullSize.getPath()).delete();
                }
                break;

        }
    }

    @Override
    public boolean  onTouch(View v, MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.editAddressEvent:
                    Log.d(TAG, "Touch editAddressEvent");
                    //FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    //fTrans.addToBackStack(null).commit();
                    mListener.OpenPlaceAutocomplete();
                    break;

                case R.id.editDateEvent:
                    setDate(v);
                    //setTime(v);
                    //setInitialDateTime();
            }
        }
        return true;
    }

    public void setDate(View v) {
        new DatePickerDialog(getActivity(), myDateCallBack,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        new TimePickerDialog(getActivity(), myTimeCallBack,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener myDateCallBack = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setTime(view);
        }
    };

    TimePickerDialog.OnTimeSetListener myTimeCallBack=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка даты и времени
    private void setInitialDateTime() {

        /*editDateEvent.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));*/
        SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm");
        dateEvent = myDateFormat.format(dateAndTime.getTime());
        /*try {
            Date myDate = myDateFormat.parse(formattedDate);
            String formattedDate1 = myDateFormat.format(myDate);
            //editDateEvent.setText(myDate.toString());
            editDateEvent.setText(formattedDate1);
        }
        catch(ParseException pe) {
            Log.d(TAG, "Error: " + pe.getMessage());
        }*/
        //Date myDate = Date(dateAndTime.getTime());
        editDateEvent.setText(dateEvent);
        //editDateEvent.setText(dateAndTime.getTime().toString());
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createTempImageFile();
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

    private File createTempImageFile() throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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

        Log.d(TAG, "CreateEventMainFragment onActivityResult()");

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.d(TAG, "intent is not null");
            Log.d(TAG, "photoUriMain: " + photoUriMain);
            ChangeImage image = new ChangeImage(getContext(), photoUriMain);

            newPhotoURIFullSize = image.getImage1920x1080();
            newPhotoURI = image.getImage300x300();

            //bitmapPhoto = image.getBitmapPhoto();
            //imageView.setImageBitmap(bitmapPhoto);
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
                new File(newPhotoURIFullSize.getPath()).delete();
                //getActivity().getContentResolver().delete(photoURI, null, null);
                //getActivity().getContentResolver().delete(photoURIFullSize, null, null);
        }
    }

    public void sentEvent(Events event) {

        Log.d(TAG, "CreateEventMainFragment sentEvent");
        latLngEvent = new LatLng(event.latitude, event.longitude);
        photoURI = Uri.parse(event.photoEvent);
        photoURIFullSize = Uri.parse(event.photoEventFullSize);
        dateEvent = event.dateEvent;

        textViewCreateEvent.setText("Редактирование мероприятия");
        imageView.setImageURI(photoURI);
        editNameEvent.setText(event.nameEvent);
        editDescriptionEvent.setText(event.descriptionEvent);
        editDateEvent.setText(event.dateEvent);
        spinnerTypeEvent.setSelection(adapter.getPosition(event.typeEvent));
        editAddressEvent.setText(event.addressEvent);
        buttonCreateEvent.setText("Изменить мероприятие");

    }
}
