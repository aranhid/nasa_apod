package com.aranhid.nasa_apod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HistoryItem extends AppCompatActivity {

    SearchHistoryDB db;

    TextView title;
    ImageView image;
    TextView date;
    TextView explanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_item);

        db = SearchHistoryDB.create(this, false);

        title = findViewById(R.id.history_title);
        image = findViewById(R.id.history_image);
        date = findViewById(R.id.history_date);
        explanation = findViewById(R.id.history_explanation);

        Intent intent = getIntent();
        String date = intent.getStringExtra(HistoryListAdapter.EXTRA_DATE);

        getHistory(date);
    }

    private void updateActivity(SearchHistoryItem item) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                title.setText(item.title);
                Picasso.get().load(item.image_url).into(image);
                date.setText(item.date);
                explanation.setText(item.explanation);
            }
        });
    }

    public void getHistory(String date) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SearchHistory searchHistory = db.searchHistory();
                SearchHistoryItem item = searchHistory.findByDate(date);
                updateActivity(item);
                Log.d("TAG", item.toString());
            }
        });
    }
}