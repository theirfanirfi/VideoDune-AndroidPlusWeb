package com.irfanullah.videodune.Libraries;

import android.content.Context;
import android.widget.Toast;

import com.irfanullah.videodune.APIs.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroLib {
    public static final String BASE_URL = "http://192.168.10.3/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit;
        } else {
            return retrofit;
        }
    }

    public static ApiService geApiService() {
        return getRetrofit().create(ApiService.class);
    }

    public static void toastHere(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }


}

