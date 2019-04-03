package com.irfanullah.videodune.APIs;

import com.irfanullah.videodune.Models.Settings;
import com.irfanullah.videodune.Models.User;
import com.irfanullah.videodune.Models.Video;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    String AFTER_BASE_URL = "VideoDune/public/api/";
    @POST(AFTER_BASE_URL+"register")
    @FormUrlEncoded
    Call<User> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @POST(AFTER_BASE_URL+"login")
    @FormUrlEncoded
    Call<User> loginUser(@Field("email") String email, @Field("password") String password);

    @POST(AFTER_BASE_URL+"savesettings")
    @FormUrlEncoded
    Call<Settings> saveSettings(@Field("token") String token, @Field("username") String username, @Field("address") String address,
                                @Field("hashtag") String hashtag);
    @GET(AFTER_BASE_URL+"getsettings")
    Call<Settings> getSettings(@Query("token") String token);


    //upload status attachments
    @Multipart
    @POST(AFTER_BASE_URL+"uploadvideo")
    Call<Video> uploadvideo(@Part("token") RequestBody token, @Part MultipartBody.Part video, @Part("hash_tag") RequestBody hash_tag, @Part("facebook_id") RequestBody facebook_id,
                            @Part("email") RequestBody email, @Part("video_title") RequestBody video_title);

}
