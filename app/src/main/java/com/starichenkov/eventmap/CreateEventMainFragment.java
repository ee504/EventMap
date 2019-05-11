package com.starichenkov.eventmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.starichenkov.customClasses.AccountAuthorization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    
    final int REQUEST_TAKE_PHOTO = 1;
    private Uri photoURI;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private String mCurrentPhotoPath;

    private CallBackInterfaceCreateEvent mListener;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /*public interface OnClickAddressListener {
        void OnClickAddress(String link);
        void OpenPlaceAutocomplete();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, null);


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
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                    //mMap.setMyLocationEnabled(true);

                }else{
                    dispatchTakePictureIntent();
                    //uiSettings.setMyLocationButtonEnabled(true);
                }


                break;

        }
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
       /* if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error: " + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d(TAG, "photoFile: " + photoFile.getAbsolutePath());
                //photoURI = Uri.fromFile(photoFile);
                photoURI = FileProvider.getUriForFile(getActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);*/
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            //}
        //}
    }

    private File createImageFile() throws IOException{
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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

        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if(intent != null) {
                Log.d(TAG, "intent is not null");
                Log.d(TAG, "photoURI: " + photoURI);
                Log.d(TAG, "intent.getData(): " + intent.getData());
                Log.d(TAG, "intent.getExtras(): " + intent.getExtras());
                Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
                //imageView.setImageURI(photoURI);
            }else{
                Log.d(TAG, "intent is null");
                Log.d(TAG, "intent is null");
                Log.d(TAG, "photoURI: " + photoURI);
                Log.d(TAG, "mCurrentPhotoPath: " + mCurrentPhotoPath);
                File myFile = new File(mCurrentPhotoPath);
                Bitmap myBitmap;
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(myFile));
                    if (myBitmap != null) {
                        Log.d(TAG, "myBitmap is not null");
                        imageView.setImageBitmap(myBitmap);
                    }else{
                        Log.d(TAG, "myBitmap is null");
                    }

                }catch (IOException ex){
                    Log.d(TAG, "Exception:" + ex.getMessage());
                }

                //imageView.setImageURI(photoURI);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        dispatchTakePictureIntent();
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}
