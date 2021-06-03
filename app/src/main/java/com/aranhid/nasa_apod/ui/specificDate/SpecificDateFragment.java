package com.aranhid.nasa_apod.ui.specificDate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aranhid.nasa_apod.ApodResponse;
import com.aranhid.nasa_apod.NasaApi;
import com.aranhid.nasa_apod.R;
import com.aranhid.nasa_apod.SearchHistory;
import com.aranhid.nasa_apod.SearchHistoryDB;
import com.aranhid.nasa_apod.SearchHistoryItem;
import com.aranhid.nasa_apod.ui.home.HomeFragment;
import com.aranhid.nasa_apod.ui.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SpecificDateFragment extends Fragment implements View.OnClickListener {

    String TAG = "HomeFragment";

    private SpecificDateViewModel specificDateViewModel;

    private TextView title;
    private ImageView image;
    private TextView date;
    private TextView explanation;
    private Button button;
    private DatePicker datePicker;

    Retrofit retrofit;
    NasaApi nasaApi;

    SearchHistoryDB db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        specificDateViewModel = new ViewModelProvider(this).get(SpecificDateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_specific_date, container, false);

        db = SearchHistoryDB.create(this.getContext(), false);

        title = root.findViewById(R.id.title);
        image = root.findViewById(R.id.image);
        date = root.findViewById(R.id.date);
        explanation = root.findViewById(R.id.explanation);
        button = root.findViewById(R.id.button);
        datePicker = root.findViewById(R.id.date_picker);

        button.setOnClickListener(this::onClick);


        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NasaApi.BASE_URL)
                .build();

        nasaApi = retrofit.create(NasaApi.class);

        return root;
    }

    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        Call<ApodResponse> call = nasaApi.getApodByDate(NasaApi.API_KEY, date);
        call.enqueue(new ApodCallback());
    }

    public void UpdateData(ApodResponse data) {
        title.setText(data.title);
        date.setText(data.date);
        explanation.setText(data.explanation);
        Picasso.get().load(data.hdurl).into(image);
    }

    public void insertInHistory(ApodResponse data) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SearchHistory searchHistory = db.searchHistory();
                SearchHistoryItem check = searchHistory.findByDate(data.date);
                if (check == null) {
                    SearchHistoryItem item = new SearchHistoryItem(data.date, data.title, data.hdurl);
                    searchHistory.insert(item);
                }
                Log.d("TAG", "Inserted");
            }
        });
    }

    class ApodCallback implements Callback<ApodResponse> {

        @Override
        public void onResponse(Call<ApodResponse> call, Response<ApodResponse> response) {
            if (response.isSuccessful()) {
                UpdateData(response.body());
                insertInHistory(response.body());
                Log.d(TAG, "Request is successful");
            } else {
                Log.d(TAG, "onResponse: " + response.code());
                Log.d(TAG, "onResponse: " + response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<ApodResponse> call, Throwable t) {
            Log.d(TAG, "onFailure: " + t.toString());
        }
    }
}