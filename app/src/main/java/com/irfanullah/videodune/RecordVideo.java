package com.irfanullah.videodune;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.irfanullah.videodune.Classes.CameraPreview;

import java.io.IOException;
import java.sql.Timestamp;

import static android.app.PendingIntent.getActivity;

public class RecordVideo extends AppCompatActivity implements SurfaceHolder.Callback {

    SurfaceView layout;
    private CameraPreview cameraPreview;
    Context context;
    Camera camera;
    TextView cd;
    Handler handler;
    int COUNT_DOWN = 120;
    int MINUTE = 60;
    private static final String TAG = "RecordVideo";
    MediaRecorder mediaRecorder;
    boolean recording = false;
    SurfaceHolder surfaceHolder;
    Button button, restartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);
        layout = (SurfaceView) findViewById(R.id.frameLayout);
        button = findViewById(R.id.startStopBtn);
        restartBtn = findViewById(R.id.restartBtn);
        cd = findViewById(R.id.countDownTextView);
        camera = Camera.open();
//        Camera.Parameters parameters = camera.getParameters();
//        camera.setParameters(parameters);
        camera.unlock();




        surfaceHolder = layout.getHolder();

        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        context = this;

        Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                mediaRecorder = new MediaRecorder();
                initMediaRecorder();
            }
        });
        tt.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recording){

                    try {
                        mediaRecorder.stop();
                        mediaRecorder.release();
                    }catch (Exception e){
                        Log.i(TAG,e.toString());
                    }

                    camera.release();
                    button.setText("Start Recording");
                    recording = false;
                }else {
                    prepareMediaRecorder();
                    button.setText("Stop Recording");
                            camera.release();
                            mediaRecorder.start();


                 //   countDownTimer();
                    recording = true;
                }

            }
        });
//

     //   checkCameraHardware(context);
    }

    private void initMediaRecorder(){
//        try {
//            camera.setPreviewDisplay(surfaceHolder);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        mediaRecorder.setCamera(camera);   // like this!
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

        CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        mediaRecorder.setProfile(camcorderProfile_HQ);
        mediaRecorder.setVideoEncodingBitRate(10000000);
        mediaRecorder.setVideoFrameRate(30);

        //get the time stamp
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        //remove the seconds part
        timestamp = timestamp.substring(0, timestamp.length()-6).replaceAll(":","");
        //append file name to the time stamp
        String filestamp =  timestamp;

        mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo"+filestamp+".mp4");

        mediaRecorder.setMaxDuration(120000); // Set max duration 60 sec.
        mediaRecorder.setMaxFileSize(5000000); // Set max file size 5M
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            Log.i(TAG, "checkCameraHardware: Camera is there.");
            // cameraPreview = new CameraPreview(context,mediaRecorder);
           // layout.addV
            return true;
        } else {
            // no camera on this device
            Log.i(TAG, "checkCameraHardware: Camera is not there.");

            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        prepareMediaRecorder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void prepareMediaRecorder(){
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void countDownTimer(){
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(COUNT_DOWN > MINUTE && COUNT_DOWN > 60 && MINUTE > 0){
                    COUNT_DOWN--;
                    MINUTE--;
                    if(MINUTE < 10 || MINUTE == 0) {
                        cd.setText("01:0" + Integer.toString(MINUTE));
                    }else {
                        cd.setText("01:" + Integer.toString(MINUTE));
                    }

                    handler.postDelayed(this,1000);
                }
                else if(COUNT_DOWN <= 60 && COUNT_DOWN >= 0 && MINUTE >= 0) {
                    COUNT_DOWN--;

                    if(MINUTE <= 0) {
                        MINUTE = 60;
                    }

                    MINUTE--;

                    if(MINUTE < 10 || MINUTE == 0) {
                        cd.setText("00:0" + Integer.toString(MINUTE));
                    }else {
                        cd.setText("00:" + Integer.toString(MINUTE));
                    }

                    if(COUNT_DOWN > 0) {
                        handler.postDelayed(this, 1000);
                    }else {
                        //call off recording
                        mediaRecorder.stop();
                        //camera.release();
                        //camera.lock();
                        button.setText("Start Recording");
                        mediaRecorder.release();
                        recording = false;
                    }
                }else {
                    //call off recording
                    mediaRecorder.stop();
                    //camera.release();
                    //camera.lock();
                    button.setText("Start Recording");
                    mediaRecorder.release();
                    recording = false;
                }

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           // mediaRecorder.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
         //   mediaRecorder.resume();
        }
    }
}
