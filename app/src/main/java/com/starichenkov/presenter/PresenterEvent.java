package com.starichenkov.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.starichenkov.createEvent.CallBackCreateEvent;
import com.starichenkov.createEvent.MDatePicker;
import com.starichenkov.data.Events;
import com.starichenkov.eventmap.BuildConfig;
import com.starichenkov.image.ChangeImage;
import com.starichenkov.image.CreateImageFile;
import com.starichenkov.model.CreateEventModel;
import com.starichenkov.presenter.interfaces.IPresenterEvent;
import com.starichenkov.view.interfaces.IViewCreateEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PresenterEvent implements IPresenterEvent, CallBackCreateEvent {

    private static final String TAG = "MyLog";

    private IViewCreateEvent iView;
    private CreateEventModel createEventModel;
    //private CreateImageFile createImageFile;
    private Events currentEvent;
    //private boolean isEventCreated;
    //final int REQUEST_TAKE_PHOTO = 1;

    //private Uri photoUriMain;
    //private String photoURI;
    //private Uri newPhotoURI;

    //private Context context;

    public PresenterEvent(IViewCreateEvent iView){
        this.iView = iView;
        //this.isEventCreated = false;
        //this.context = context;
        this.createEventModel = new CreateEventModel(this);
        //this.createImageFile = new CreateImageFile(context);

        //this.photoUriMain = iView.getPhotoUriMain();
        //this.photoURI = iView.getPhotoURI();
        //this.newPhotoURI = iView.getNewPhotoURI();
    }

    @Override
    public Events getCurrentEvent(){
        if(currentEvent != null) {
            return currentEvent;
        }else{
            return new Events();
        }
    }

    @Override
    public void onClickCreateEvent(Events event) {
        if(currentEvent != null){
            event.setId(currentEvent.getId());
            createEventModel.updateEvent(event);
        }
        createEventModel.createEvent(event);
    }

    @Override
    public void getEventById(String IdEvent) {
        createEventModel.getEventById(IdEvent);
    }

    @Override
    public void updateEvent(Events event) {

    }

    /*@Override
    public void onClickDeletePhoto(){
        if(currentEvent.getPhotoEvent()!=null){
            deletePhoto(currentEvent.getPhotoEvent());
            currentEvent.setPhotoEvent(null);
        }else if(newPhotoURI != null) {
            new File(newPhotoURI.getPath()).delete();
            newPhotoURI = null;
        }
    }*/

    @Override
    public void deletePhoto(String photo) {

    }

    @Override
    public void detachView() {
        /*if(isEventCreated == false && newPhotoURI != null){
            new File(newPhotoURI.getPath()).delete();
        }*/
        iView = null;
    }

    @Override
    public void startMainActivity(){
        Log.e(TAG, "PresenterEvent startMainActivity()");
        iView.startMainActivity();
    }

    @Override
    public void setEvents(List<Events> events) {
        this.currentEvent = events.get(0);
        iView.setEvents(events);
    }

    /*@Override
    public void dispatchTakePictureIntent(Activity activity){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile.getTempImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "CreateEventMainFragment Error: " + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d(TAG, "photoFile: " + photoFile.getAbsolutePath());
                photoUriMain = FileProvider.getUriForFile(activity,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUriMain);
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode){
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if(newPhotoURI != null) {
                new File(newPhotoURI.getPath()).delete();
            }
            ChangeImage image = new ChangeImage(context, photoUriMain);
            newPhotoURI = image.getImage300x300();
            //imageView.setImageURI(newPhotoURI);
            activity.getContentResolver().delete(photoUriMain, null, null);
            photoUriMain = null;
        }
    }*/

    /*@Override
    public void onEditDateEvent(Context context){
        MDatePicker datePicker = new MDatePicker(context);
        datePicker.setDate();
        iView.setInitialDateTime(datePicker.getDateAndTime());
    }*/
}
