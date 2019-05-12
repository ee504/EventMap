package com.starichenkov.eventmap;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
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
    final int PIC_CROP = 2;
    private Uri photoURI;
    private String mCurrentPhotoPath;

    private CallBackInterfaceCreateEvent mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, null);


        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.event_map_logo);

        editNameEvent = (EditText) view.findViewById(R.id.editNameEvent);
        editDateEvent = (EditText) view.findViewById(R.id.editDateEvent);
        spinnerTypeEvent = (Spinner) view.findViewById(R.id.spinnerTypeEvent);
        editAddressEvent = (EditText) view.findViewById(R.id.editAddressEvent);
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

        buttonCreateEvent = (Button) view.findViewById(R.id.buttonCreateEvent);
        buttonCreateEvent.setOnClickListener(this);

        buttonTakePhoto = (Button) view.findViewById(R.id.buttonTakePhoto);
        buttonTakePhoto.setOnClickListener(this);

        buttonDeletePhoto = (Button) view.findViewById(R.id.buttonDeletePhoto);
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
                dispatchTakePictureIntent();
                break;

            case R.id.buttonDeletePhoto:
                Log.d(TAG, "Click buttonDeletePhoto");
                imageView.setImageResource(R.drawable.event_map_logo);
                getActivity().getContentResolver().delete(photoURI, null, null);
                break;

        }
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
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
            Log.d(TAG, "intent is not null");
            Log.d(TAG, "photoURI: " + photoURI);
            //imageView.setImageURI(photoURI);
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
                setImage(bitmap);
            }catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error: " + ex.getMessage());
            }

            //setPic();
            //performCrop();
        }//else if(requestCode == PIC_CROP && resultCode == RESULT_OK){
            //Bundle extras = intent.getExtras();
            // Получим кадрированное изображение
            //Bitmap thePic = extras.getParcelable("data");
            // передаём его в ImageView
            //imageView.setImageBitmap(thePic);
        //}
    }

    private void setImage(Bitmap bitmap) {

        try{
            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    bitmap = bitmap;
            }

        }catch (IOException ex) {
            // Error occurred while creating the File
            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error: " + ex.getMessage());
        }

        imageView.setImageBitmap(bitmap);

    }

    private void setPic() {

        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        //orientation
        try{
        ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                bitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                bitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                bitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                bitmap = bitmap;
        }

        }catch (IOException ex) {
            // Error occurred while creating the File
            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error: " + ex.getMessage());
        }

        imageView.setImageBitmap(bitmap);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void performCrop(){
        try {
            // Намерение для кадрирования. Не все устройства поддерживают его
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(photoURI, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 147);
            cropIntent.putExtra("outputY", 147);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch(ActivityNotFoundException anfe){
            String errorMessage = "Извините, но ваше устройство не поддерживает кадрирование";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
