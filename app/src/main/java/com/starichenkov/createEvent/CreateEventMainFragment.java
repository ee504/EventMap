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
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.starichenkov.RoomDB.BookMarks;
import com.starichenkov.RoomDB.Events;
import com.starichenkov.account.AccountAuthorization;
import com.starichenkov.eventmap.BuildConfig;
import com.starichenkov.view.IView;
import com.starichenkov.eventmap.MapsActivity;
import com.starichenkov.eventmap.R;
import com.starichenkov.presenter.IPresenter;
import com.starichenkov.presenter.Presenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CreateEventMainFragment extends Fragment implements OnClickListener, OnTouchListener, IView {

    private String[] TypeEvents = { "Спектакль", "Выставка", "Вечеринка", "Кинопоказ", "Концерт" };
    private static final String TAG = "MyLog";

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
    private String mCurrentPhotoPath;

    private Calendar dateAndTime= Calendar.getInstance();

    private Uri photoURI;
    private Uri photoURIFullSize;
    private Bitmap bitmapPhoto;
    private String nameEvent;
    private String dateEvent;
    private String typeEvent;
    private String addressEvent;
    private LatLng latLngEvent;

    private IPresenter iPresenter;



    private CallBackInterfaceCreateEvent mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, null);

        iPresenter = new Presenter(this, getActivity().getLocalClassName());

        Log.d(TAG, "start CreateEventMainFragment");
        Log.d(TAG, "photoURI: " + photoURI);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        if(photoURI == null) {
            imageView.setImageResource(R.drawable.event_map_logo);
        }else{
            imageView.setImageBitmap(bitmapPhoto);
            /*try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
                setImage(bitmap);
            }catch (IOException ex) {
                Log.d(TAG, "Error: " + ex.getMessage());
            }*/
        }


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
                Log.d(TAG, "Создать мероприятие");
                iPresenter.createEvent(new Events(new AccountAuthorization(getActivity()).getIdUser(), photoURI.toString(), photoURIFullSize.toString(), editNameEvent.getText().toString(), editDescriptionEvent.getText().toString(),
                        dateEvent, spinnerTypeEvent.getSelectedItem().toString(), addressEvent, latLngEvent.latitude, latLngEvent.longitude));
                Log.d(TAG, "Список переменных");
                Log.d(TAG,
                        "id = " + new AccountAuthorization(getActivity()).getIdUser() +
                                ", photoURI = " + photoURI.toString() +
                                ", editNameEvent = " + editNameEvent.getText().toString() +
                                ", dateEvent = " + dateEvent +
                                ", TypeEvent = " + spinnerTypeEvent.getSelectedItem().toString() +
                                ", addressEvent = " + addressEvent +
                                ", latitude = " + latLngEvent.latitude +
                                ", longitude = " + latLngEvent.longitude);
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
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

    @Override
    public boolean  onTouch(View v, MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.editAddressEvent:
                    Log.d(TAG, "Touch editAddressEvent");
                    FragmentTransaction fTrans = getFragmentManager().beginTransaction();
                    fTrans.addToBackStack(null).commit();
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private File createImageFile() throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_.jpg";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFileName);
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
            //try{
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
                setImage(photoURI);
            /*}catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error: " + ex.getMessage());
            }*/

            //setPic();
            //performCrop();
        }
    }

    private void setImage(Uri uri) {

        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
            Log.d(TAG, "bitmap_origin.getWidth(): " + bitmap.getWidth());
            Log.d(TAG, "bitmap_origin.getHeight(): " + bitmap.getHeight());
            Log.d(TAG, "bitmap_origin.getByteCount(): " + bitmap.getByteCount());
            Log.d(TAG, "photoURI.getPath(): " + photoURI);

            /*}catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error: " + ex.getMessage());
            }*/


            Bitmap bitmap_1920_1080 = decodeSampledBitmapFromResource(photoURI, 1920, 1080);
            Log.d(TAG, "bitmap_1920_1080.getWidth(): " + bitmap_1920_1080.getWidth());
            Log.d(TAG, "bitmap_1920_1080.getHeight(): " + bitmap_1920_1080.getHeight());
            Log.d(TAG, "bitmap_1920_1080.getByteCount(): " + bitmap_1920_1080.getByteCount());


            Bitmap bitmap_300_300 = decodeSampledBitmapFromResource(photoURI, 300, 300);
            Log.d(TAG, "bitmap_300_300.getWidth(): " + bitmap_300_300.getWidth());
            Log.d(TAG, "bitmap_300_300.getHeight(): " + bitmap_300_300.getHeight());
            Log.d(TAG, "bitmap_300_300.getByteCount(): " + bitmap_300_300.getByteCount());

            InputStream inputStream = getActivity().getContentResolver().openInputStream(photoURI);
            ExifInterface ei = new ExifInterface(inputStream);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Log.d(TAG, "photoURI orientation: " + orientation);

            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap_300_300 = rotateImage(bitmap_300_300, 90);
                    bitmap_1920_1080 = rotateImage(bitmap_1920_1080, 90);
                    Log.d(TAG, "ORIENTATION_ROTATE_90");
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    Log.d(TAG, "ORIENTATION_ROTATE_180");
                    bitmap_300_300 = rotateImage(bitmap_300_300, 180);
                    bitmap_1920_1080 = rotateImage(bitmap_1920_1080, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    Log.d(TAG, "ORIENTATION_ROTATE_270");
                    bitmap_300_300 = rotateImage(bitmap_300_300, 270);
                    bitmap_1920_1080 = rotateImage(bitmap_1920_1080, 270);
                    break;

            }

            File file_1920_1080 = createImageFile();
            setBitMapToFile(bitmap_1920_1080, file_1920_1080);
            Uri uri_1920_1080 = Uri.fromFile(file_1920_1080);
            Log.d(TAG, "uri_1920_1080.getPath(): " + uri_1920_1080);

            File file_300_300 = createImageFile();
            setBitMapToFile(bitmap_300_300, file_300_300);
            Uri uri_300_300 = Uri.fromFile(file_300_300);
            Log.d(TAG, "uri_300_300.getPath(): " + uri_300_300);


            bitmapPhoto = bitmap_300_300;
            getActivity().getContentResolver().delete(photoURI, null, null);
            photoURI = uri_300_300;
            photoURIFullSize = uri_1920_1080;

            imageView.setImageBitmap(bitmap_300_300);

        }catch (IOException ex) {
            // Error occurred while creating the File
            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error: " + ex.getMessage());
        }

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public void SetEventAddress(Address address, LatLng latLng){
        addressEvent = address.getLocality() + ", " + address.getThoroughfare() + ", " + address.getFeatureName();
        latLngEvent = latLng;
        editAddressEvent.setText(addressEvent);

    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        Log.d(TAG, "calculateInSampleSize height: " + height);
        final int width = options.outWidth;
        Log.d(TAG, "calculateInSampleSize width: " + width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(TAG, "calculateInSampleSize inSampleSize: " + inSampleSize);
        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(Uri uri,
                                                         int reqWidth, int reqHeight) throws IOException{

        InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeStream(inputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        //return BitmapFactory.decodeResource(res, resId, options);
        InputStream inputStream2 = getActivity().getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(inputStream2, null, options);

    }

    public void setBitMapToFile(Bitmap bitMap, File file) throws IOException {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        file.createNewFile();
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(bytes.toByteArray());
        fo.close();

    }

    @Override
    public void sendEvents(List<Events> events){
    }

    @Override
    public void sendBookMarks(List<BookMarks> bookMarks){
    }

}
