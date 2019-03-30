package com.irfanullah.videodune.APIs;

import com.irfanullah.videodune.Models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    String AFTER_BASE_URL = "VideoDune/public/api/";
    @POST(AFTER_BASE_URL+"register")
    @FormUrlEncoded
    Call<User> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @POST(AFTER_BASE_URL+"login")
    @FormUrlEncoded
    Call<User> loginUser(@Field("email") String email, @Field("password") String password);
}
