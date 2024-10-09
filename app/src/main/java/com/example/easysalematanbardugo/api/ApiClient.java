package com.example.easysalematanbardugo.api;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client class for making network requests using Retrofit.
 */
public class ApiClient {

    // Retrofit advantages:
    // 1. Easy to use
    // 2. Supports synchronous and asynchronous requests
    // 3. Supports multiple converters
    private static final String BASE_URL = "https://fakestoreapi.com/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            //GsonConverterFactory is used to convert JSON to Java objects
        }
        return retrofit;
    }

    @NonNull
    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
