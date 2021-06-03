package com.aranhid.nasa_apod;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Locale;

@Entity(tableName = "searchHistory")
public class SearchHistoryItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int _id;

    String date;
    String title;
    String image_url;


    public SearchHistoryItem(int _id, String date, String title, String image_url){
        this._id = _id;
        this.date = date;
        this.title = title;
        this.image_url = image_url;
    }

    @Ignore
    public SearchHistoryItem(String date, String title, String image_url){
        this._id = _id;
        this.date = date;
        this.title = title;
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "id: %d, date: %s, title: %s", _id, date, title);
    }
}
