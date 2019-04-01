package com.irfanullah.videodune;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button startBtn;
    Context context;
    int REQUEST_VIDEO_CAPTURE = 333;
    int VIDEO_RECORDING_DURATION = 120;
    int VIDEO_QUALITY = 1;
    private static final String TAG = "WelcomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initObjects();
    }

    private void initObjects() {
        context = this;
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCameraActivity();
            }
        });
    }

    private void gotoCameraActivity() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            //takeVideoIntent.putExtra("android.intent.extra.durationLimit", 30000);
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,VIDEO_RECORDING_DURATION);
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,VIDEO_QUALITY);
            // takeVideoIntent.putExtra("EXTRA_VIDEO_QUALITY", 50);
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK){
            Uri uri = data.getData();
            Log.i(TAG, "onActivityResult: "+uri.toString());
            Intent mainAct = new Intent(context,MainActivity.class);
            mainAct.putExtra("video",uri.toString());
            startActivity(mainAct);
        }
    }
}
