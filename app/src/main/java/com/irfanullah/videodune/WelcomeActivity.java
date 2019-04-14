package com.irfanullah.videodune;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.irfanullah.videodune.Libraries.RetroLib;

public class WelcomeActivity extends AppCompatActivity {

    Button startBtn;
    Context context;
    int REQUEST_VIDEO_CAPTURE = 333;
    int VIDEO_RECORDING_DURATION = 120;
    int VIDEO_QUALITY = 1;
    ImageView setting;
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
        setting = findViewById(R.id.imageViewSetting);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestCameraPermission();
                }else {
                      gotoCameraActivity();
                }
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingAct = new Intent(context,SettingsActivity.class);
                startActivity(settingAct);
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

    private void requestCameraPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.CAMERA)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_VIDEO_CAPTURE+1);

                // MY_PERMISSIONS_REQUEST_READ_CAMERA is an
                // app-defined int constant. The callback method gets the
                // result of the request.
           // }
        } else {
            // Permission has already been granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_VIDEO_CAPTURE+1);
            gotoCameraActivity();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_VIDEO_CAPTURE+1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

          //  gotoCameraActivity();
            requestStoragePermission();
        }else if(requestCode == REQUEST_VIDEO_CAPTURE+2 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              //gotoCameraActivity();
            requestMicroPhonePermission();
        }else if(requestCode == REQUEST_VIDEO_CAPTURE+3 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            gotoCameraActivity();
        }
        else {
            RetroLib.toastHere(context,"Error occurred in granting permission. Try again");
        }

    }

    private void requestStoragePermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.CAMERA)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_VIDEO_CAPTURE+2);

            // MY_PERMISSIONS_REQUEST_READ_CAMERA is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            // }
        } else {
            // Permission has already been granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_VIDEO_CAPTURE+2);
            requestMicroPhonePermission();

        }
    }

    private void requestMicroPhonePermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.CAMERA)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_VIDEO_CAPTURE+3);

            // MY_PERMISSIONS_REQUEST_READ_CAMERA is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            // }
        } else {
            // Permission has already been granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_VIDEO_CAPTURE+3);
            gotoCameraActivity();

        }
    }
}
