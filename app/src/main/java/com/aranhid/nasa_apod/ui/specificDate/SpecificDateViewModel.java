package com.aranhid.nasa_apod.ui.specificDate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpecificDateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpecificDateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}