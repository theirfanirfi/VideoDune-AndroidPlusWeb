package com.irfanullah.videodune;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.irfanullah.videodune.Classes.CameraPreview;

import java.io.IOException;

public class RecordVideo extends AppCompatActivity implements SurfaceHolder.Callback {

    SurfaceView layout;
    private CameraPreview cameraPreview;
    Context context;
    Camera camera;

    private static final String TAG = "RecordVideo";
    MediaRecorder mediaRecorder;
    boolean recording = false;
    SurfaceHolder surfaceHolder;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);
        layout = (SurfaceView) findViewById(R.id.frameLayout);
        button = findViewById(R.id.button);

        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        camera.setParameters(parameters);
        camera.unlock();

        surfaceHolder = layout.getHolder();

        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        context = this;

        mediaRecorder = new MediaRecorder();
        initMediaRecorder();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recording){
                    mediaRecorder.stop();
                    //camera.release();
                    //camera.lock();
                    mediaRecorder.release();
                    recording = false;
                }else {
                    mediaRecorder.start();
                    recording = true;
                }

            }
        });
//

     //   checkCameraHardware(context);
    }

    private void initMediaRecorder(){
        try {
            camera.setPreviewDisplay(surfaceHolder);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.setCamera(camera);   // like this!
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        mediaRecorder.setProfile(camcorderProfile_HQ);
        mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");

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
}
