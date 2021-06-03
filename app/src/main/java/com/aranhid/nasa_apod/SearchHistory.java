package com.aranhid.nasa_apod;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SearchHistory {
    @Query("SELECT * FROM searchHistory ORDER BY _id DESC")
    List<SearchHistoryItem> selectAll();

    @Query("SELECT * FROM searchHistory WHERE _id=:id")
    SearchHistoryItem findById(int id);

    @Query("SELECT * FROM searchHistory WHERE date=:date")
    SearchHistoryItem findByDate(String date);

    @Insert
    void insert(SearchHistoryItem... items);
}
