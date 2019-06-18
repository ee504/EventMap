package com.starichenkov.model;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.starichenkov.model.interfaces.IModelPhoto;
import com.starichenkov.presenter.CallBacks.CallBackCurrentUser;

public class ModelPhoto extends ModelCurrentUser implements IModelPhoto {

    public ModelPhoto(CallBackCurrentUser callBackCurrentUser) {
        super(callBackCurrentUser);
    }

    @Override
    public void deletePhoto(String photo) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference photoRef = storage.getReferenceFromUrl(photo);
        photoRef.delete();
    }
}
