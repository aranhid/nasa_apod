package com.aranhid.nasa_apod;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SearchHistoryItem.class}, version = 1)
public abstract class SearchHistoryDB extends RoomDatabase {
    public abstract SearchHistory searchHistory();

    private static final String DB_NAME = "searchHistory.db";
    private static volatile SearchHistoryDB INSTANCE = null;

    synchronized static SearchHistoryDB get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = create(context, false);
        }
        return INSTANCE;
    }
    public static SearchHistoryDB create(Context context, boolean memoryOnly) {
        RoomDatabase.Builder<SearchHistoryDB> builder;
        if (memoryOnly) {
            builder = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), SearchHistoryDB.class);
        }
        else {
            builder = Room.databaseBuilder(context.getApplicationContext(), SearchHistoryDB.class, DB_NAME);
        }
        return builder.build();
    }
}
