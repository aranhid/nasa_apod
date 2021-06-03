package com.aranhid.nasa_apod.ui.specificDate;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aranhid.nasa_apod.ApodResponse;
import com.aranhid.nasa_apod.NasaApi;

import retrofit2.Call;

public class SpecificDateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpecificDateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public void onClick(View v) {
        Log.d("TEST", "TEST");
    }

    public LiveData<String> getText() {
        return mText;
    }
}