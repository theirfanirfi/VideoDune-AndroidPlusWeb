package com.irfanullah.videodune;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.irfanullah.videodune.Libraries.RetroLib;
import com.irfanullah.videodune.Models.Video;
import com.irfanullah.videodune.Storage.PrefStorage;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalActivity extends AppCompatActivity {

    EditText emailField, fbField, hashtagField, videoTitleField;
    Context context;
    Button finishBtn;
    String email = "", fb = "", hashtag = "", videoTitle;
    Uri videoUri;
    TextView statusTextView;
    private static final String TAG = "FinalActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        videoUri = Uri.parse(getIntent().getExtras().getString("videoUri"));
        initObjects();
        finishBtnClickListener();
    }



    private void initObjects() {
        context = this;
        emailField = findViewById(R.id.emailField);
        fbField = findViewById(R.id.fbField);
        hashtagField = findViewById(R.id.personalHashTagEditText);
        videoTitleField = findViewById(R.id.videoTitleField);
        finishBtn = findViewById(R.id.finishBtn);
        statusTextView = findViewById(R.id.statusTextView);
    }

    private void finishBtnClickListener() {

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailField.getText().toString();
                fb = fbField.getText().toString();
                hashtag = hashtagField.getText().toString();
                videoTitle = videoTitleField.getText().toString();
                if(email.isEmpty() || fb.isEmpty() || hashtag.isEmpty() || videoTitle.isEmpty()){
                    RetroLib.toastHere(context,"Fields cannot be empty.");
                }else {
                    if(PrefStorage.checkUser(context)) {
                        makeFinishRequest();
                    }else {
                        RetroLib.toastHere(context,"You need to login.");
                    }

                }
            }
        });
    }

    private void makeFinishRequest() {

        statusTextView.setText("Please wait, the video is being uploaded.");
        String path = getRealPathFromURIPath(videoUri,context);
        File file = new File(path);

        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), PrefStorage.getUser(context).getTOKEN());
        RequestBody hash_tag = RequestBody.create(MediaType.parse("multipart/form-data"),hashtag);
        RequestBody facebook_id = RequestBody.create(MediaType.parse("multipart/form-data"),fb);

        RequestBody emaill = RequestBody.create(MediaType.parse("multipart/form-data"),email);
        RequestBody vt = RequestBody.create(MediaType.parse("multipart/form-data"),videoTitle);
        RequestBody vid = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        Log.i(TAG, "makeFinishRequest: "+vid.toString());
        MultipartBody.Part video = MultipartBody.Part.createFormData("video",file.getName(),vid);



        RetroLib.geApiService().uploadvideo(tokenBody,video,hash_tag,facebook_id,emaill,vt).enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                if(response.isSuccessful()) {
                    Video vodeo = response.body();

                    if (vodeo.getError()) {
                        RetroLib.toastHere(context, vodeo.getMessage());
                        Log.i(TAG, "onResponse: " + vodeo.getMessage());
                        statusTextView.setText(vodeo.getMessage());

                    } else if(vodeo.isSaved()) {
                        statusTextView.setText(vodeo.getMessage());
                        RetroLib.toastHere(context, vodeo.getMessage());
                        Log.i(TAG, "onResponse: " + vodeo.getMessage());
                        gotoStartActivity();
                    }else {
                        statusTextView.setText(vodeo.getMessage());
                        RetroLib.toastHere(context, vodeo.getMessage());
                    }
                }else {
                    Log.i(TAG, "onResponse: " + response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                RetroLib.toastHere(context,t.getMessage());
                Log.i(TAG, "onResponse: "+t.getMessage());


            }
        });

    }

    private String getRealPathFromURIPath(Uri contentURI, Context activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void gotoStartActivity(){
        Intent startAct = new Intent(context,WelcomeActivity.class);
        startActivity(startAct);
    }
}
