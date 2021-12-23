package com.example.estoriassemhapp.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {
    Uri selectPhotoLocation = null;

    public Uri getSelectPhotoLocation() {
        return selectPhotoLocation;
    }

    public void setSelectPhotoLocation(Uri selectPhotoLocation) {
        this.selectPhotoLocation = selectPhotoLocation;
    }

}