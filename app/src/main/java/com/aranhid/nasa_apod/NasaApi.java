package com.aranhid.nasa_apod;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NasaApi {
    String BASE_URL = "https://api.nasa.gov/";
    String API_KEY = "vfPb66mO74uyzG3TQ6tVBsKvwMf4dacGsRMomMaF";

    @GET("/planetary/apod")
    Call<ApodResponse> getApod(@Query("api_key") String apiKey);

    @GET("/planetary/apod")
    Call<ApodResponse> getApodByDate(@Query("api_key") String apiKey, @Query("date") String date);
}
