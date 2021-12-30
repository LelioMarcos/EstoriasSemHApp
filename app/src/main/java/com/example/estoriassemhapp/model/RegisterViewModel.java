package com.example.estoriassemhapp.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import java.io.File;

public class RegisterViewModel extends ViewModel {
    File filePhoto = null;
    Uri selectPhotoLocation = null;

    public File getPhotoFile() {
        return filePhoto;
    }

    public void setPhotoFile(File filePhoto) {
        this.filePhoto = filePhoto;
    }

    public Uri getSelectPhotoLocation() {
        return selectPhotoLocation;
    }

    public void setSelectPhotoLocation(Uri selectPhotoLocation) {
        this.selectPhotoLocation = selectPhotoLocation;
    }
}