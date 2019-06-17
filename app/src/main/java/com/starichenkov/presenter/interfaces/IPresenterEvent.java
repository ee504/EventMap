package com.starichenkov.presenter.interfaces;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.starichenkov.data.Events;

public interface IPresenterEvent extends IPresenterMain {
    void createEvent(Events event);
    void getEventById(String IdEvent);
    void updateEvent(Events event);
    void deletePhoto(String photo);

    //void dispatchTakePictureIntent(Activity activity);
    //void onActivityResult(Activity activity, int requestCode, int resultCode);

    //void onClickDeletePhoto();

    //void onEditDateEvent(Context context);

    Events getCurrentEvent();
}
