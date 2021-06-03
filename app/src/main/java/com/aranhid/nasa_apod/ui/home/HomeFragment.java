package com.aranhid.nasa_apod.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aranhid.nasa_apod.ApodResponse;
import com.aranhid.nasa_apod.NasaApi;
import com.aranhid.nasa_apod.R;
import com.aranhid.nasa_apod.SearchHistory;
import com.aranhid.nasa_apod.SearchHistoryDB;
import com.aranhid.nasa_apod.SearchHistoryItem;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HomeFragment extends Fragment {

    String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;

    private TextView title;
    private ImageView image;
    private TextView date;
    private TextView explanation;

    Retrofit retrofit;
    NasaApi nasaApi;

    SearchHistoryDB db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = SearchHistoryDB.create(this.getContext(), false);

        title = root.findViewById(R.id.title);
        image = root.findViewById(R.id.image);
        date = root.findViewById(R.id.date);
        explanation = root.findViewById(R.id.explanation);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NasaApi.BASE_URL)
                .build();

        nasaApi = retrofit.create(NasaApi.class);

        Call<ApodResponse> call = nasaApi.getApod(NasaApi.API_KEY);
        call.enqueue(new ApodCallback());

        return root;
    }

    public void updateData(ApodResponse data) {
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
                    SearchHistoryItem item = new SearchHistoryItem(data.date, data.title, data.hdurl, data.explanation);
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
                updateData(response.body());
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